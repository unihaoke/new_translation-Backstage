package com.trans.translation.service.impl;

import com.trans.translation.common.Result;
import com.trans.translation.common.StatusCode;
import com.trans.translation.dao.SubpackageDao;
import com.trans.translation.dao.TaskDao;
import com.trans.translation.pojo.Subpackage;
import com.trans.translation.pojo.Task;
import com.trans.translation.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

/**
 * 服务层
 *
 * @author Administrator
 *
 */
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private SubpackageDao subpackageDao;

    private Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    /**
     * 通过userId查找任务
     * @param userId
     * @return
     */
    @Override
    public Result findByUserId(String userId) {
        if(StringUtils.isEmpty(userId)){
            return new Result(false,StatusCode.ERROR,"查询失败");
        }
        Sort sort = new Sort(Sort.Direction.DESC,"createtime");
        return new Result(true,StatusCode.OK,"查询成功",taskDao.findByUserid(userId,sort));
    }

    /**
     * 添加任务和文件记录
     * @param task
     * @param file
     * @return
     */
    @Override
    public Result addTaskAndFile(Task task, MultipartFile file) {
        if(task == null){
            return new Result(false,StatusCode.ERROR,"添加失败");
        }
        if (file.isEmpty()) {
            return new Result(false,StatusCode.ERROR,"发布失败");
        }

        //文件上传
        uploadFile(file);

        Subpackage subpackage = new Subpackage();
        try {
            //分包
            int count = fileSubpackage((File) file,subpackage);
            task.setTotal(count);
        } catch (IOException e) {
            e.printStackTrace();
        }
        taskDao.save(task);
        subpackageDao.save(subpackage);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /**
     * 对文件进行分包
     * @param file
     */
    public int fileSubpackage(File file,Subpackage subpackage) throws IOException {


        ArrayList<String> res = new ArrayList<>();//段落切分结果
        StringBuilder sb = new StringBuilder();//拼接读取的内容
        String temp = null;//存取sb去除空格的内容
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int ch = 0;
        while ((ch = reader.read())!=-1){
//            temp = sb.toString().trim().replaceAll("\\s+","");//去除前后空格，之后去除中间空格
            temp = sb.toString();
            if((char)ch =='\r'){
                //判断是否空行
                if(!"".equals(temp)){
                    //到了段落结尾，将其加入链表，并清空sb
                    res.add(temp);
                }
                sb.delete(0,sb.length());//清空sb
                temp = null;
            }else{
                //说明还没到段落结尾，将结果暂存
                sb.append((char)ch);
            }
        }

        if(reader.read()==-1){//注意文本最后一段末尾没有'\r'。所以ch=-1时，最后一段temp还没有存入res.
            System.out.println("你读到末尾了");
        }
        //如果最后一段非空，加入
        if(!"".equals(temp)){
            res.add(temp);
        }

        Iterator<String> iterator = res.iterator();
        //每个段落的排序
        int z=1;

        while (iterator.hasNext()){
            String next = iterator.next();
            System.out.println("段落开始:");
            System.out.println(next);
            subpackage.setContent(next);
            subpackage.setSection(z);
            z++;
        }
        return res.size();
    }

    /**
     * 将文件保存到本地
     * @param file
     */
    public void uploadFile(MultipartFile file){
        String fileName = file.getOriginalFilename();
        String filePath = ".";

        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件，上传的文件名:{},上传的路径:{},新文件名:{}",fileName,filePath,uploadFileName);
        File fileDir = new File(filePath);
        System.out.println("1:"+fileDir);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(filePath,uploadFileName);
        System.out.println(System.getProperty("user.dir"));
        try {
            file.transferTo(targetFile);
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常",e);
        }
    }
}
