package com.trans.translation.controller;


import com.trans.translation.common.Result;
import com.trans.translation.common.StatusCode;
import com.trans.translation.service.SubpackageService;
import com.trans.translation.service.TaskService;
import com.trans.translation.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

@Controller
@RequestMapping("/subpackage")
public class SubpackageController {

    @Autowired
    private SubpackageService subpackageService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @GetMapping
    @ResponseBody
    public Result findAll(){
        return subpackageService.findAll();
    }

    @GetMapping(value = "/search/{page}/{size}")
    @ResponseBody
    public Result pageQuery(@PathVariable int page, @PathVariable int size,HttpServletRequest request){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        return subpackageService.pageQuery(page,size,userId);
    }

    @GetMapping(value = "/search/{territory}")
    @ResponseBody
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
    @ResponseBody
    public Result pageQueryByTerritory(@PathVariable int page, @PathVariable int size, @PathVariable String territory,HttpServletRequest request){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        return subpackageService.pageQueryByTerritory(page,size,territory,userId);
    }

    /**
     * 查找上下文
     * @param section
     * @return
     */
    @GetMapping(value = "/{taskId}/{section}")
    @ResponseBody
    public Result findByContext(@PathVariable String taskId,@PathVariable Integer section){
        return subpackageService.findByContext(taskId,section);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public Result findById(@PathVariable String id){
        return subpackageService.findById(id);
    }

    @GetMapping(value = "/task/{taskId}")
    @ResponseBody
    public Result findByTaskId(@PathVariable String taskId){
        return subpackageService.findByTaskId(taskId);
    }

    @GetMapping(value = "/merge/{taskId}")
    public void merge(@PathVariable(value = "taskId") String taskId, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        //查找合并文件直接返回
        String path = taskService.findMergeFile(taskId);
        if (path==null){
            path = subpackageService.merge(userId,taskId).trim();
        }
        File file = new File(path);
        if (file.exists()){
            byte[] buffer = new byte[1024];
            String temp[] = path.split("\\\\");
            String fileName = temp[temp.length-1].trim();
            response.setContentType("application/msword");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os  = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                System.out.println("Download the song successfully!");
            }  catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    @GetMapping(value = "/merge/check/{taskId}")
    @ResponseBody
    public Result checkMerge(@PathVariable(value = "taskId") String taskId){
        int count  = subpackageService.checkMerge(taskId);
        if(count>0){
            return new Result(false,StatusCode.ERROR,"还有分包任务未完成");
        }
        return new Result(true,StatusCode.OK,"分包任务已全部完成");
    }


}