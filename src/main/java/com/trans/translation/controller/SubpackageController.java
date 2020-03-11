package com.trans.translation.controller;

import com.trans.translation.common.Result;
import com.trans.translation.service.SubpackageService;
import com.trans.translation.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/subpackage")
public class SubpackageController {

    @Autowired
    private SubpackageService subpackageService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @GetMapping
    public Result findAll(){
        return subpackageService.findAll();
    }

    @GetMapping(value = "/search/{page}/{size}")
    public Result pageQuery(@PathVariable int page, @PathVariable int size){
        return subpackageService.pageQuery(page,size);
    }

    @GetMapping(value = "/search/{territory}")
    public Result findByTerritory(@PathVariable String territory){
        return subpackageService.findByTerritory(territory);
    }

    /**
     * 通过领域查找分包信息
     * @param page
     * @param size
     * @param territory
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}/{territory}")
    public Result pageQueryByTerritory(@PathVariable int page, @PathVariable int size, @PathVariable String territory){
        return subpackageService.pageQueryByTerritory(page,size,territory);
    }

    /**
     * 查找上下文
     * @param section
     * @return
     */
    @GetMapping(value = "/{taskId}/{section}")
    public Result findByContext(@PathVariable String taskId,@PathVariable Integer section){
        return subpackageService.findByContext(taskId,section);
    }

    @GetMapping(value = "/{id}")
    public Result findById(@PathVariable String id){
        return subpackageService.findById(id);
    }

    @GetMapping(value = "/task/{taskId}")
    public Result findByTaskId(@PathVariable String taskId){
        return subpackageService.findByTaskId(taskId);
    }

    @PostMapping(value = "/merge")
    public Result merge(HttpServletRequest request, @RequestBody Map<String, String> map){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        return subpackageService.merge(userId,map.get("taskId"));
    }

}