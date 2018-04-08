package com.spring.boot.study.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class ExecutorService {


    @Async("taskExecutor")
    public Future<String> taskOne() throws InterruptedException {
        System.out.println("开始做任务一");
        long startTime = System.currentTimeMillis();
        Thread.sleep(4000);
        long endTime = System.currentTimeMillis();
        System.out.println("完成任务一，耗时：" + (endTime - startTime) + "毫秒");
        return new AsyncResult<>("任务一完成");
    }

    @Async("taskExecutor")
    public Future<String> taskTwo() throws InterruptedException {
        System.out.println("开始做任务二");
        long start = System.currentTimeMillis();
        Thread.sleep(5000);
        long end = System.currentTimeMillis();
        System.out.println("完成任务二，耗时：" + (end - start) + "毫秒");
        return new AsyncResult<>("任务二完成");
    }

    @Async("taskExecutor")
    public Future<String> taskThree() throws InterruptedException {
        System.out.println("开始做任务三");
        long start = System.currentTimeMillis();
        Thread.sleep(3000);
        long end = System.currentTimeMillis();
        System.out.println("完成任务三，耗时：" + (end - start) + "毫秒");
        return new AsyncResult<>("任务三完成");
    }

    @Async("taskExecutor")
    public void doTask() {
        long start = System.currentTimeMillis();
        try {
            Future<String> task1 = this.taskOne();
            Future<String> task2 = this.taskTwo();
            Future<String> task3 = this.taskThree();
            while (true) {
                if(task1.isDone() && task2.isDone() && task3.isDone()) {
                    break;
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.printf("任务全部完成，总耗时：" + (end - start) + "毫秒");
    }
}
