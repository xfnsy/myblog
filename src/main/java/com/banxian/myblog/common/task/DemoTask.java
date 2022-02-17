package com.banxian.myblog.common.task;

import com.banxian.myblog.common.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

//@Component
public class DemoTask {
        private static final Logger logger= LoggerFactory.getLogger(DemoTask.class);
    @Scheduled(cron = "0/3 * * * * ?")
    public void demo1(){
        try {
            System.out.println("定时任务1->>>"+ DateUtil.format(LocalDateTime.now()) +"执行了，哈哈哈");
            int i=1/0;
        }catch (Exception e){
            logger.error("定时任务异常",e);
        }

    }
    @Scheduled(cron = "0/3 * * * * ?")
    public void demo2(){
        System.out.println("定时任务2->>>"+ DateUtil.format(LocalDateTime.now()) +"执行了，哈哈哈");
    }
}
