package org.learn.test.example.project.inner;

import org.learn.framework.annotation.Component;
import org.learn.framework.annotation.Priority;
import org.learn.framework.aop.After;
import org.learn.framework.aop.Aspect;
import org.learn.framework.aop.Before;
import org.learn.framework.context.Level;

/**
 * @author hzwang
 * @version 1.0
 * Create by 2024/1/5 17:12
 * @title P
 * @description <TODO description class purpose>
 */
@Aspect(target = Person.class)
@Component
public class aspect {

    @Before
    public void before(){
        System.out.println("before: 方法被执行前");
    }

    @After
    public void after(){
        System.out.println("after: 方法被执行后");
    }
}
