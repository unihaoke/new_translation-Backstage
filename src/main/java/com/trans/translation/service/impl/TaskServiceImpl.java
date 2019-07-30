package com.trans.translation.service.impl;

import com.trans.translation.common.Result;
import com.trans.translation.common.StatusCode;
import com.trans.translation.dao.SubpackageDao;
import com.trans.translation.dao.TaskDao;
import com.trans.translation.pojo.Subpackage;
import com.trans.translation.pojo.Task;
import com.trans.translation.service.TaskService;
import com.trans.translation.utils.IdWorker;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

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
    private IdWorker idWorker;

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
        readWord(file);
        task.setId(idWorker.nextId()+"");
        //文件上传以及分包
        String fileName = uploadFile(file);
        task.setFilename(fileName);
        task.setT_status(0);
//        task.setTotal(count);
        task.setCreatetime(new Date());
        task.setUpdatetime(new Date());
        taskDao.save(task);
        return new Result(true,StatusCode.OK,"添加成功");
    }


    /**
     * 对文本进行分包(暂时不用，用下面的那个文档分包)
     * @param file
     */
    public int fileSubpackage(File file,Subpackage subpackage) throws IOException {

        ArrayList<String> res = new ArrayList<>();//段落切分结果
        StringBuilder sb = new StringBuilder();//拼接读取的内容
        String temp = null;//存取sb去除空格的内容
        BufferedReader reader = null;
        try {
            reader = new BufferedReader( new InputStreamReader(new FileInputStream(file), "GBK"));
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
    public String uploadFile(MultipartFile file){
        String fileName = file.getOriginalFilename();
        String filePath = "E:\\apk";
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
//            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常",e);
        }
        return uploadFileName;
    }


    /**
     * 将文档进行分包
     */
    public void readWord(MultipartFile file){
        String textFileName=file.getOriginalFilename();
        Map wordMap = new LinkedHashMap();//创建一个map对象存放word中的内容
        try {
            if(textFileName.endsWith(".doc")){ //判断文件格式
                InputStream fis = file.getInputStream();
                WordExtractor wordExtractor = new WordExtractor(fis);//使用HWPF组件中WordExtractor类从Word文档中提取文本或段落
                int i=1;
                for(String words : wordExtractor.getParagraphText()){//获取段落内容
                    System.out.println(words);
                    wordMap.put("DOC文档，第（"+i+"）段内容",words);
                    i++;
                }
                fis.close();
            }
            if(textFileName.endsWith(".docx")){
                File uFile = new File("E:\\apk\\tempFile.docx");//创建一个临时文件
                if(!uFile.exists()){
                    uFile.createNewFile();
                }
                FileCopyUtils.copy(file.getBytes(), uFile);//复制文件内容
                OPCPackage opcPackage = POIXMLDocument.openPackage("E:\\apk\\tempFile.docx");//包含所有POI OOXML文档类的通用功能，打开一个文件包。
                XWPFDocument document = new XWPFDocument(opcPackage);//使用XWPF组件XWPFDocument类获取文档内容
                List<XWPFParagraph> paras = document.getParagraphs();
                int i=1;
                for(XWPFParagraph paragraph : paras){
                    String words = paragraph.getText();
                    System.out.println(words);
                    wordMap.put("DOCX文档，第（"+i+"）段内容",words);
                    System.out.println(" ");
                    i++;
                }
                opcPackage.close();
                uFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(wordMap);
    }
}
