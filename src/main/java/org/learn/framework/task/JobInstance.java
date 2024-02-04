package org.learn.framework.task;


import org.learn.framework.context.Environment;
import org.learn.framework.context.YzClass;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hongda.li@hand-china.com 2022/3/16 14:06
 */
public class JobInstance {

    private String jobName;

    private String createTime = Environment.getSystemTime();

    private YzClass yzClass;

    private Object instance;

    private String cronExpress;

    private String startTime;

    private long period;

    private long delay;

    private JobUnit unit;

    private final AtomicInteger count = new AtomicInteger(0);

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public YzClass getYzClass() {
        return yzClass;
    }

    public void setYzClass(YzClass yzClass) {
        this.yzClass = yzClass;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public String getCronExpress() {
        return cronExpress;
    }

    public void setCronExpress(String cronExpress) {
        this.cronExpress = cronExpress;
    }

    public int getCount() {
        return count.get();
    }

    public void setCount(int count) {
        this.count.set(count);
    }

    public void countAddOne(){
        this.count.incrementAndGet();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public JobUnit getUnit() {
        return unit;
    }

    public void setUnit(JobUnit unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobInstance that = (JobInstance) o;
        return period == that.period && delay == that.delay && Objects.equals(jobName, that.jobName) && Objects.equals(createTime, that.createTime) && Objects.equals(yzClass, that.yzClass) && Objects.equals(instance, that.instance) && Objects.equals(cronExpress, that.cronExpress) && Objects.equals(startTime, that.startTime) && unit == that.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobName, createTime, yzClass, instance, cronExpress, startTime, period, delay, unit);
    }

    @Override
    public String toString() {
        return "JobInstance{" +
                "jobName='" + jobName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", YzClass=" + yzClass +
                ", instance=" + instance +
                ", cronExpress='" + cronExpress + '\'' +
                ", startTime='" + startTime + '\'' +
                ", period=" + period +
                ", delay=" + delay +
                ", unit=" + unit +
                ", count=" + count +
                '}';
    }
}
