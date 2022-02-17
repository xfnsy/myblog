package com.banxian.myblog.common.cache;

import com.banxian.myblog.common.util.DateUtil;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
public class LocalCache {

    private final static ConcurrentHashMap<String, WarpperValue> store;

    static {
        store = new ConcurrentHashMap<>(256);
        // 定时清理过期数据
        Thread thread = new Thread(new ScheduledClear(), "scheduledClear");
        thread.setDaemon(true);
        thread.start();
    }

    public static Object get(String key) {
        WarpperValue warpperValue = store.get(key);
        if (warpperValue == null || checkExpire(warpperValue)) {
            store.remove(key);
            return null;
        }
        return warpperValue.getValue();
    }

    public static <T> T get(String key, Class<T> type) {
        Object value = get(key);
        return value == null ? null : (T) value;
    }

    public static WarpperValue lookUp(String key) {
        return store.get(key);
    }

    public static void put(String key, Object value) {
        store.put(key, new WarpperValue(value));
    }

    public static void put(String key, Object value, LocalDateTime localDateTime) {
        store.put(key, new WarpperValue(value, localDateTime));
    }

    public static void put(String key, Object value, long seconds) {
        put(key, value, LocalDateTime.now().plusSeconds(seconds));
    }

    public static void put(String key, Object value, Date expireDate) {
        put(key, value, DateUtil.date2LocalDateTime(expireDate));
    }


    public static Object putIfAbsent(String key, Object value) {
        return store.putIfAbsent(key, new WarpperValue(value));
    }


    public static void remove(String key) {
        store.remove(key);
    }

    public static void clear() {
        store.clear();
    }


    public static boolean containsKey(String key) {
        return store.containsKey(key);
    }


    private static boolean checkExpire(WarpperValue warpperValue) {
        // 没有过期时间
        if (warpperValue.getExpireDate() == null) {
            return false;
        }
        // 还没过期
        if (warpperValue.getExpireDate().isAfter(LocalDateTime.now())) {
            return false;
        }
        return true;
    }


    private static class WarpperValue {
        private Object value;
        private LocalDateTime expireDate;
        private LocalDateTime lastAccessDate;

        private WarpperValue(Object value) {
            this.value = value;
        }

        public WarpperValue(Object value, LocalDateTime expireDate) {
            this.value = value;
            this.expireDate = expireDate;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public LocalDateTime getExpireDate() {
            return expireDate;
        }

        public void setExpireDate(LocalDateTime expireDate) {
            this.expireDate = expireDate;
        }

        public LocalDateTime getLastAccessDate() {
            return lastAccessDate;
        }

        public void setLastAccessDate(LocalDateTime lastAccessDate) {
            this.lastAccessDate = lastAccessDate;
        }

    }

    /**
     * 定期清理类
     */
    private static class ScheduledClear implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
//                System.out.println("ScheduledClear >>>清理开始...");
                StringBuffer sbf = new StringBuffer();
                store.forEach((k, v) -> {
                    if (checkExpire(v)) {
                        store.remove(k);
                        sbf.append("key=").append(k);
                    }
                });
                if (sbf.length() > 0) {
                    System.out.println("ScheduledClear >>>清理了" + sbf.toString());
                }
//                System.out.println("ScheduledClear >>>清理结束...");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        LocalCache.put("demo", new AtomicLong(100), 21);
        LocalCache.put("demo1", new AtomicLong(100), 2);
        LocalCache.put("demo2", new AtomicLong(100), 12);
        LocalCache.put("demo3", new AtomicLong(100), 11);
        for (int i = 0; i < 20; i++) {
            TimeUnit.SECONDS.sleep(1);
            new Thread(() -> System.out.println(Thread.currentThread().getName() + "--获得了" + LocalCache.get("demo", AtomicLong.class).incrementAndGet())).start();
        }
    }

}
