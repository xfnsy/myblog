package com.banxian.myblog.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class CommonConfig {

    private static final Logger log = LoggerFactory.getLogger(CommonConfig.class);

    /**
     * 定时任务默认是单线程的，多个任务会相互阻塞，此时需要配置定时任务线程池
     */
    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        log.info("开始初始化定时任务");
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        //当前线程数
        scheduler.setPoolSize(5);
        //定时任务前缀
        scheduler.setThreadNamePrefix("test");
        //该方法用来设置线程池中 任务的等待时间，如果超过这个时间还没有销毁就 强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        scheduler.setAwaitTerminationSeconds(20);
        // 该方法用来设置 线程池关闭 的时候 等待 所有任务都完成后，再继续 销毁 其他的 Bean，这样这些 异步任务 的 销毁 就会先于 数据库连接池对象 的销毁
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        log.info("初始化定时任务结束");
        return scheduler;
    }


}
