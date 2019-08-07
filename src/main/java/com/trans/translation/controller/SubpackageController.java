package com.trans.translation.controller;

import com.trans.translation.common.Result;
import com.trans.translation.service.SubpackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subpackage")
public class SubpackageController {

    @Autowired
    private SubpackageService subpackageService;

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

}