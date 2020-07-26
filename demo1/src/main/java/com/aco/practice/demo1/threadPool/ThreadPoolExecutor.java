package com.aco.practice.demo1.threadPool;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author: HaoJianXu
 * @Date: 2020/7/26 15:46
 */
public class ThreadPoolExecutor {

    /**
     * 创建线程池
     * @return
     */
    public static java.util.concurrent.ThreadPoolExecutor customThreadPool(){
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
        return new java.util.concurrent.ThreadPoolExecutor(
                10,//初始化线程池大小
                30,//最大线程数量
                60,//存活时间
                TimeUnit.SECONDS,//存活时间单位
                queue//存储线程池的队列
        );
    }
}
