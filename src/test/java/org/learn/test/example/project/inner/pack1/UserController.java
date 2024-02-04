package org.learn.test.example.project.inner.pack1;

import org.learn.framework.annotation.Controller;
import org.learn.framework.annotation.RequestMapping;

/**
 * @author hzwang
 * @version 1.0
 * Create by 2024/1/9 15:19
 * @title UserController
 * @description <TODO description class purpose>
 */
@Controller
public class UserController {

    @RequestMapping("/user")
    public String user(String username) {
        System.out.println(username);
        return username;
    }
}
