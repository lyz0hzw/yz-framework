package org.learn.framework.task;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 任务调度
 */
public class JobSchedule {
    /**
     * JDK自带的定时服务，并声明10个线程并发执行
     */
    private static ScheduledExecutorService SERVICE;


    public static void init(int size) {
        SERVICE = Executors.newScheduledThreadPool(size);
    }

    /**
     * 自定义定时服务
     * @param task 定时任务
     * @param initialDelay 起始时间
     * @param period 执行频率
     * @param timeUnit 执行频率枚举
     */
    public static void doTask(Runnable task, long initialDelay,long period,TimeUnit timeUnit){
        SERVICE.scheduleAtFixedRate(task,initialDelay,period,timeUnit);
    }

    /**
     * 自定义定时服务
     * @param task 定时任务
     * @param startTime 起始时间
     * @param period 执行频率
     * @param timeUnit 执行频率枚举
     */
    public static void doTask(Runnable task,String startTime,long period,TimeUnit timeUnit,DateUnit dateUnit){
        long betweenStart = DateUtil.between(DateUtil.date(), DateUtil.parse(startTime), dateUnit);
        SERVICE.scheduleAtFixedRate(task,betweenStart,period,timeUnit);
    }


    /**
     * 按秒为单位执行定时任务
     * @param task 线程任务
     * @param initialDelay 开始时间
     * @param period 执行频率
     */
    public static void doBySeconds(Runnable task,long initialDelay,long period){
        SERVICE.scheduleAtFixedRate(task,initialDelay,period,TimeUnit.SECONDS);
    }

    /**
     * 按秒为单位执行定时任务
     * @param task 线程任务
     * @param startTime 开始时间
     * @param period 执行频率
     */
    public static void doBySeconds(Runnable task,String startTime,long period){
        long betweenStart = DateUtil.between(DateUtil.date(), DateUtil.parse(startTime), DateUnit.SECOND);
        SERVICE.scheduleAtFixedRate(task,betweenStart,period,TimeUnit.SECONDS);
    }

    /**
     * 按分钟为单位执行定时任务
     * @param task 线程任务
     * @param initialDelay 开始时间
     * @param period 执行频率
     */
    public static void doByMinutes(Runnable task,long initialDelay,long period){
        SERVICE.scheduleAtFixedRate(task,initialDelay,period,TimeUnit.MINUTES);
    }

    /**
     * 按分钟为单位执行定时任务
     * @param task 线程任务
     * @param startTime 开始时间
     * @param period 执行频率
     */
    public static void doByMinutes(Runnable task,String startTime,long period){
        long betweenStart = DateUtil.between(DateUtil.date(), DateUtil.parse(startTime), DateUnit.DAY);
        SERVICE.scheduleAtFixedRate(task,betweenStart,period,TimeUnit.MINUTES);
    }

    /**
     * 按小时为单位执行定时任务
     * @param task 线程任务
     * @param initialDelay 开始时间
     * @param period 执行频率
     */
    public static void doByHours(Runnable task,long initialDelay,long period){
        SERVICE.scheduleAtFixedRate(task,initialDelay,period,TimeUnit.HOURS);
    }

    /**
     * 按小时为单位执行定时任务
     * @param task 线程任务
     * @param startTime 开始时间
     * @param period 执行频率
     */
    public static void doByHours(Runnable task,String startTime,long period){
        long betweenStart = DateUtil.between(DateUtil.date(), DateUtil.parse(startTime), DateUnit.HOUR);
        SERVICE.scheduleAtFixedRate(task,betweenStart,period,TimeUnit.HOURS);
    }

    /**
     * 按天为单位执行定时任务
     * @param task 线程任务
     * @param initialDelay 开始时间
     * @param period 执行频率
     */
    public static void doByDays(Runnable task,long initialDelay,long period){
        SERVICE.scheduleAtFixedRate(task,initialDelay,period,TimeUnit.DAYS);
    }

    /**
     * 按天为单位执行定时任务
     * @param task 线程任务
     * @param startTime 开始时间
     * @param period 执行频率
     */
    public static void doByDays(Runnable task,String startTime,long period){
        long betweenStart = DateUtil.between(DateUtil.date(), DateUtil.parse(startTime), DateUnit.DAY);
        SERVICE.scheduleAtFixedRate(task,betweenStart,period,TimeUnit.DAYS);
    }
}
