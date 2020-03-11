package com.trans.translation.controller;

import com.trans.translation.common.Result;
import com.trans.translation.common.StatusCode;
import com.trans.translation.pojo.Translation;
import com.trans.translation.service.TranslationService;
import com.trans.translation.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/translation")
public class TranslationController {

    @Autowired
    private TranslationService translationService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @GetMapping(value = "/all")
    public Result findAll(){
        return translationService.findAll();
    }

    @GetMapping(value = "/search/{page}/{size}")
    public Result pageQuery(@PathVariable int page, @PathVariable int size){
        return translationService.pageQuery( page, size);
    }

    @GetMapping
    public Result findByUserId(HttpServletRequest request){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        return translationService.findByUserId(userId);
    }

    @PostMapping
    public Result add(HttpServletRequest request,@RequestBody Translation translation){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        translation.setUserid(userId);
        return translationService.add(translation);
    }

    @GetMapping(value = "/{subpackageId}")
    public Result findTranslate(HttpServletRequest request,@PathVariable String subpackageId){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        return translationService.findTranslate(userId,subpackageId);
    }

    @PostMapping(value = "/translate")
    public Result translateText(@RequestBody Map<String, String> map){
        return new Result(true,StatusCode.OK,"翻译请求成功",translationService.GooleTranslate(map.get("tl"),map.get("text")));
    }

    @GetMapping(value = "/translation/{subpackageId}")
    public Result  findBySubpackageId(@PathVariable String subpackageId){
        return translationService.findBySubpackageId(subpackageId);
    }

    @PostMapping(value = "/similarity")
    public Result textSimilarity(@RequestBody Map<String, String> map){
        return translationService.textSimilarity(map);
    }

    @PostMapping(value = "/adopt")
    public Result adopt(@RequestBody Map<String, String> map){
        return translationService.adopt(map);
    }
}
