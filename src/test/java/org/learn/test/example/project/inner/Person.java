package org.learn.test.example.project.inner;

import org.learn.framework.annotation.Component;

/**
 * @author hzwang
 * @version 1.0
 * Create by 2024/1/5 17:18
 * @title Person
 * @description <TODO description class purpose>
 */
@Component
public class Person implements Sayable {

    public void sayHi(){
        System.out.println("你好啊");
    }
}
