package com.spring.boot.study.controller;


import com.spring.boot.study.service.ExecutorService;
import com.spring.boot.study.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.Future;

@Controller
public class ThreadPoolController {
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private SysService sysService;

    @ResponseBody
    @RequestMapping(value = "/executor")
    public String testExecutor(){
        long start = System.currentTimeMillis();
        try {
            Future<String> task1 = executorService.taskOne();
            Future<String> task2 = executorService.taskTwo();
            Future<String> task3 = executorService.taskThree();
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
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/executor2")
    public String testExecutor2() {
        executorService.doTask();
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/executor3")
    public String testExecutor3() {
        sysService.doTask();
        return "success";
    }

}
