package org.learn.framework.task;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.log.Log;
import org.learn.framework.annotation.*;
import org.learn.framework.config.TaskConfig;
import org.learn.framework.context.*;
import org.learn.framework.util.ClassUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author hongda.li@hand-china.com 2022/3/16 13:58
 */
@Priority(Level.Middle)
public class JobAware implements ApplicationContextAware {

    private TimeUnit timeUnit;

    private DateUnit dateUnit;

    private static final List<JobInstance> JOB_LIST = new ArrayList<>();

    public static List<JobInstance> getJobList() {
        return JobAware.JOB_LIST;
    }

    public static JobInstance getJobInstance(Class<?> clazz, String methodName) {
        for (JobInstance jobInstance : JOB_LIST) {
            if (jobInstance.getJobName().equals(clazz.getName() + "#" + methodName)) {
                return jobInstance;
            }
        }
        return null;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (! ClassUtils.mainClassWithAnnotation(EnableSchedule.class)) {
            return;
        }
        TaskConfig config = Environment.getConfig(TaskConfig.class);
        JobSchedule.init(config.getThreadPoolSize());

        ClassUtils.getClassesWithAnnotation(Component.class).forEach(clazz
                -> Arrays.asList(ReflectUtil.getMethods(clazz, method
                -> method.getAnnotation(Cron.class) != null)).forEach(method  // 获取带有@Component注解的类，获取@Cron的方法
                -> {
            JobInstance jobInstance = new JobInstance();
            jobInstance.setInstance(applicationContext.getBean(clazz));
            jobInstance.setJobName(clazz.getName() + "#" + method.getName());
            YzClass yzClass = new YzClass(); // 存储特定类的特定方法
            yzClass.setClazz(clazz);
            yzClass.setMethod(method);
            jobInstance.setYzClass(yzClass);
            jobInstance.setCronExpress(method.getAnnotation(Cron.class).value());
            JOB_LIST.add(jobInstance);
        }));

        ClassUtils.getClassesWithAnnotation(Component.class).forEach(clazz
                -> Arrays.asList(ReflectUtil.getMethods(clazz, method
                -> method.getAnnotation(Schedule.class) != null)).forEach(method
                -> {
            Schedule schedule = AnnotationUtil.getAnnotation(method, Schedule.class);
            JobInstance jobInstance = new JobInstance();
            jobInstance.setJobName(clazz.getName() + "#" + method.getName());
            jobInstance.setInstance(applicationContext.getBean(clazz));
            YzClass yzClass = new YzClass();
            yzClass.setClazz(clazz);
            yzClass.setMethod(method);
            jobInstance.setYzClass(yzClass);
            jobInstance.setDelay(schedule.delay());
            jobInstance.setPeriod(schedule.period());
            jobInstance.setStartTime(schedule.startTime());
            jobInstance.setUnit(schedule.unit());
            JOB_LIST.add(jobInstance);
        }));

        executeJobs();
        executeTasks();

        CronUtil.setMatchSecond(true);
        CronUtil.start();
    }

    private void executeJobs() {
        JobAware.JOB_LIST.forEach(jobInstance
                -> CronUtil.schedule(jobInstance.getCronExpress(), (Task) ()
                -> {
            try {
                ReflectUtil.invoke(jobInstance.getInstance(), jobInstance.getYzClass().getMethod());
                jobInstance.countAddOne();
            } catch (UtilException e) {
                Log.get().error("定时任务执行出现错误[{}]", jobInstance.toString());
                e.printStackTrace();
            }
        }));
    }

    private void executeTasks(){
        JobAware.JOB_LIST.forEach(jobInstance -> {
            if (jobInstance.getUnit() != null){
                parseUnit(jobInstance.getUnit());
                long initialDelay = jobInstance.getDelay();
                long period = jobInstance.getPeriod();
                String startTime = jobInstance.getStartTime();
                // 如果采用initialDelay作为起始时间
                if ("".equals(startTime)) {
                    JobSchedule.doTask(() -> {
                        try {
                            ReflectUtil.invoke(jobInstance.getInstance(), jobInstance.getYzClass().getMethod());
                            jobInstance.countAddOne();
                        } catch (Exception e) {
                            Log.get().error("定时任务执行出现错误[{}]", jobInstance.toString());
                            e.printStackTrace();
                        }
                    }, initialDelay, period, timeUnit);
                } else {
                    // 如果采用startTime作为起始时间
                    JobSchedule.doTask(() -> {
                        try {
                            ReflectUtil.invoke(jobInstance.getInstance(), jobInstance.getYzClass().getMethod());
                            jobInstance.countAddOne();
                        } catch (Exception e) {
                            Log.get().error( "定时任务执行出现错误[{}]", jobInstance.toString());
                            e.printStackTrace();
                        }
                    }, startTime, period, timeUnit, dateUnit);
                }
            }
        });
    }

    /**
     * 解析执行频率枚举类型
     *
     * @param jobUnit JFaster执行频率枚举
     */
    private void parseUnit(JobUnit jobUnit) {
        switch (jobUnit) {
            case DAY:
                timeUnit = TimeUnit.DAYS;
                dateUnit = DateUnit.DAY;
                break;
            case MINUTE:
                timeUnit = TimeUnit.MINUTES;
                dateUnit = DateUnit.MINUTE;
                break;
            case HOUR:
                timeUnit = TimeUnit.HOURS;
                dateUnit = DateUnit.HOUR;
                break;
            case SECOND:
                timeUnit = TimeUnit.SECONDS;
                dateUnit = DateUnit.SECOND;
                break;
            default:
        }
    }
}
