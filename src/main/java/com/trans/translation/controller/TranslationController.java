package com.trans.translation.controller;

import com.trans.translation.common.Result;
import com.trans.translation.pojo.Translation;
import com.trans.translation.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/translation")
public class TranslationController {

    @Autowired
    private TranslationService translationService;

    @GetMapping
    public Result findAll(){
        return translationService.findAll();
    }

    @GetMapping(value = "/search/{page}/{size}")
    public Result pageQuery(@PathVariable int page, @PathVariable int size){
        return translationService.pageQuery( page, size);
    }

    @GetMapping(value = "/{userId}")
    public Result findByUserId(@PathVariable String userId){
        return translationService.findByUserId(userId);
    }

    @PostMapping
    public Result add(@RequestBody Translation translation){
        return translationService.add(translation);
    }

    @GetMapping(value = "/{userId}/{subpackageId}")
    public Result findTranslate(@PathVariable String userId, @PathVariable String subpackageId){
        return translationService.findTranslate(userId,subpackageId);
    }

    @GetMapping(value = "/translate/{tl}/{text}")
    public Result translateText(@PathVariable(value = "tl") String tl,@PathVariable(value = "text") String text){
        return translationService.GooleTranslate(tl,text);
    }

    @GetMapping(value = "/translation/{subpackageId}")
    public Result  findBySubpackageId(@PathVariable String subpackageId){
        return translationService.findBySubpackageId(subpackageId);
    }
}
