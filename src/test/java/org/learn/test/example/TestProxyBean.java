package org.learn.test.example;

import cn.hutool.aop.ProxyUtil;
import cn.hutool.aop.aspects.Aspect;
import org.learn.framework.Starter;
import org.learn.framework.aop.YzProxy;
import org.learn.framework.context.ApplicationContext;
import org.learn.test.example.project.inner.A;
import org.learn.test.example.project.inner.Person;
import org.learn.test.example.project.inner.Sayable;

import java.lang.reflect.Method;

/**
 * @author hzwang
 * @version 1.0
 * Create by 2024/1/5 17:11
 * @title TestProxyBean
 * @description <TODO description class purpose>
 */

public class TestProxyBean {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext applicationContext = Starter.run(A.class);
        Sayable person = (Sayable) applicationContext.getBean("person");
        person.sayHi();

//        Thread.sleep(500000);
//        Person p = new Person();
//        Sayable sayable = YzProxy.newProxyInstanceByInstance(Sayable.class, p, new Aspect() {
//            @Override
//            public boolean before(Object target, Method method, Object[] args) {
//                System.out.println("代理前");
//                return true;
//            }
//
//            @Override
//            public boolean after(Object target, Method method, Object[] args, Object returnVal) {
//                System.out.println("代理后");
//                return true;
//            }
//
//            @Override
//            public boolean afterException(Object target, Method method, Object[] args, Throwable e) {
//                return false;
//            }
//        });
//
//        sayable.sayHi();
    }
}
