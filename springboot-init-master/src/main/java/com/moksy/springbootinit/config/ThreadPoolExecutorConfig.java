package com.moksy.springbootinit.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolExecutorConfig {
    @Bean
    public ThreadPoolExecutor threadPoolExecutor(){
        //自定义创建一个线程工厂
        ThreadFactory threadFactory=new ThreadFactory() {
            //初始化线程数为1
            int count=1;
            @Override
            public Thread newThread(@NotNull Runnable r) {
                //创建一个线程
                Thread thread = new Thread(r);
                //给线程设置一个名称
                thread.setName("线程" + count);
                count++;
                //返回新创建的线程
                return thread;
            }
        };
        //创建一个新的线程池，核心数为2，最大线程数为4，空闲等待时间100秒，任务队列是阻塞队列，长度为4，使用自定义的线程工厂
        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(2, 4, 100,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(4),threadFactory);
        //返回创建的线程池
        return threadPoolExecutor;
    }
}
