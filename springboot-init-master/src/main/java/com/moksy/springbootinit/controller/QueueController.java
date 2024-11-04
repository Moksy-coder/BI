package com.moksy.springbootinit.controller;

import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
/*
* 队列测试
* */
@Slf4j
@RequestMapping("/queue")
@RestController
public class QueueController {
    @Resource//自动注入线程池的实例
    private ThreadPoolExecutor threadPoolExecutor;
    @GetMapping("/add")
    public void add(String name){
        CompletableFuture.runAsync(()->{
            log.info("任务执行中:"+name+",执行人:"+Thread.currentThread().getName());
            try {
                //让线程休眠一下
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //异步任务在threadPoolExecutor中执行
        },threadPoolExecutor);
    }
    @GetMapping("/get")
    //方法返回线程池的状态信息
    public String get(){
      //创建一个Hashmap用来存储线程池的状态信息
        Map<String, Object> map=new HashMap<>();
        //获取队列的长度
        int size = threadPoolExecutor.getQueue().size();
        // 将队列长度放入map中
         map.put("队列长度：",size);
        // 获取线程池已接收的任务总数
        long taskCount = threadPoolExecutor.getTaskCount();
        // 将任务总数放入map中
        map.put("总的任务数：", taskCount);
        // 获取线程池已完成的任务数
        long completedTaskCount = threadPoolExecutor.getCompletedTaskCount();
        // 将已完成的任务数放入map中
        map.put("已完成的任务数：", completedTaskCount);
        // 获取线程池中正在执行任务的线程数
        int activeCount = threadPoolExecutor.getActiveCount();
        // 将正在工作的线程数放入map中
        map.put("正在执行任务的线程数：", activeCount);
        // 将map转换为JSON字符串并返回
        return JSONUtil.toJsonStr(map);
    }
}
