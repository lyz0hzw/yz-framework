package org.learn.test.lowcode.controller;

import org.learn.test.lowcode.entity.Corpus_conversation_multiple;
import org.learn.framework.annotation.*;
import org.learn.framework.web.ResponseBody;

/**
 * @author Application
 */
@Controller
public class Corpus_conversation_multipleController {

    @PostMapping("/corpus_conversation_multiple")
    public ResponseBody insert(Corpus_conversation_multiple corpus_conversation_multiple){
        return ResponseBody.success();
    }

    @DeleteMapping("/corpus_conversation_multiple")
    public ResponseBody delete(int id){
        return ResponseBody.success();
    }

    @PutMapping("/corpus_conversation_multiple")
    public ResponseBody update(Corpus_conversation_multiple corpus_conversation_multiple){
        return ResponseBody.success();
    }

    @GetMapping("/corpus_conversation_multiple")
    public ResponseBody findAll(){
        return ResponseBody.success();
    }

    @GetMapping("/corpus_conversation_multiple/{id}")
    public ResponseBody find(int id){
        return ResponseBody.success();
    }
}
