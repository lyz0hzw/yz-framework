package org.learn.test.lowcode.controller;

import org.learn.test.lowcode.entity.Subject;
import org.learn.framework.annotation.*;
import org.learn.framework.web.ResponseBody;

/**
 * @author Application
 */
@Controller
public class SubjectController {

    @PostMapping("/subject")
    public ResponseBody insert(Subject subject){
        return ResponseBody.success();
    }

    @DeleteMapping("/subject")
    public ResponseBody delete(int id){
        return ResponseBody.success();
    }

    @PutMapping("/subject")
    public ResponseBody update(Subject subject){
        return ResponseBody.success();
    }

    @GetMapping("/subject")
    public ResponseBody findAll(){
        return ResponseBody.success();
    }

    @GetMapping("/subject/{id}")
    public ResponseBody find(int id){
        return ResponseBody.success();
    }
}
