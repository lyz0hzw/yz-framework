package org.learn.test.lowcode.controller;

import org.learn.test.lowcode.entity.Corpus_conversation_single;
import org.learn.framework.annotation.*;
import org.learn.framework.web.ResponseBody;

/**
 * @author Application
 */
@Controller
public class Corpus_conversation_singleController {

    @PostMapping("/corpus_conversation_single")
    public ResponseBody insert(Corpus_conversation_single corpus_conversation_single){
        return ResponseBody.success();
    }

    @DeleteMapping("/corpus_conversation_single")
    public ResponseBody delete(int id){
        return ResponseBody.success();
    }

    @PutMapping("/corpus_conversation_single")
    public ResponseBody update(Corpus_conversation_single corpus_conversation_single){
        return ResponseBody.success();
    }

    @GetMapping("/corpus_conversation_single")
    public ResponseBody findAll(){
        return ResponseBody.success();
    }

    @GetMapping("/corpus_conversation_single/{id}")
    public ResponseBody find(int id){
        return ResponseBody.success();
    }
}
