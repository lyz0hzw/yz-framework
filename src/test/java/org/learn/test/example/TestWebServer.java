package org.learn.test.example;

import org.learn.framework.Starter;
import org.learn.framework.context.ApplicationContext;
import org.learn.test.example.project.inner.A;
import org.learn.test.example.project.inner.Sayable;

/**
 * @author hzwang
 * @version 1.0
 * Create by 2024/1/9 10:23
 * @title TestWebServer
 * @description <TODO description class purpose>
 */

public class TestWebServer {
    public static void main(String[] args) {
        ApplicationContext applicationContext = Starter.run(A.class);
        Sayable person = (Sayable) applicationContext.getBean("person");
        person.sayHi();
    }
}
