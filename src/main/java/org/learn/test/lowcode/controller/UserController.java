package org.learn.test.lowcode.controller;

import org.learn.test.lowcode.entity.User;
import org.learn.framework.annotation.*;
import org.learn.framework.web.ResponseBody;

/**
 * @author Application
 */
@Controller
public class UserController {

    @PostMapping("/user")
    public ResponseBody insert(User user){
        return ResponseBody.success();
    }

    @DeleteMapping("/user")
    public ResponseBody delete(int id){
        return ResponseBody.success();
    }

    @PutMapping("/user")
    public ResponseBody update(User user){
        return ResponseBody.success();
    }

    @GetMapping("/user")
    public ResponseBody findAll(){
        return ResponseBody.success();
    }

    @GetMapping("/user/{id}")
    public ResponseBody find(int id){
        return ResponseBody.success();
    }
}
