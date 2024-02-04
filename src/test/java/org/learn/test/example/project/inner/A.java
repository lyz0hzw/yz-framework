package org.learn.test.example.project.inner;

import org.learn.framework.annotation.ComponentScan;
import org.learn.framework.annotation.BootApplication;
import org.learn.framework.annotation.EnableSchedule;

/**
 * @author hzwang
 * @version 1.0
 * Create by 2023/9/7 16:48
 * @title A
 * @description <TODO description class purpose>
 */

@BootApplication(scanBasePackages = "org.learn.test.example.bean.project.extra")
@ComponentScan("org.learn.test.example.bean.project.scan")
@EnableSchedule
public class A {
}
