package com.spring.boot.study.Job;


import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SimpleJob{

    private static int count = 0;

    @Scheduled(cron = "0 0/3 * * * ?")
    public void simpleJob() {
        System.out.println("count: " + count);
        count++;
    }
}
