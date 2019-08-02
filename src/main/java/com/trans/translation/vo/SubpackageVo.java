package com.trans.translation.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class SubpackageVo implements Serializable {

    private String id;

    private String title;//标题

    private String t_describe;//描述

    private String t_language;//翻译类型

    private String territory;//翻译领域

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deadline; //截止时间

    private String userid;

    private String taskid;

    private String product_id;

    private String content; //内容

    private Integer section; //第几段

    private String translation;//翻译

    private Integer t_status;//状态

    private Integer count;//

    private Integer text_length;//文字长度

    public SubpackageVo(String id, String title, String t_describe, String t_language, String territory, Date deadline, String userid, String taskid, String product_id, String content, Integer section, String translation, Integer t_status, Integer count, Integer text_length) {
        this.id = id;
        this.title = title;
        this.t_describe = t_describe;
        this.t_language = t_language;
        this.territory = territory;
        this.deadline = deadline;
        this.userid = userid;
        this.taskid = taskid;
        this.product_id = product_id;
        this.content = content;
        this.section = section;
        this.translation = translation;
        this.t_status = t_status;
        this.count = count;
        this.text_length = text_length;
    }

    public SubpackageVo() {
    }

    public SubpackageVo(String t_describe, Integer section) {
        this.t_describe = t_describe;
        this.section = section;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getT_describe() {
        return t_describe;
    }

    public void setT_describe(String t_describe) {
        this.t_describe = t_describe;
    }

    public String getT_language() {
        return t_language;
    }

    public void setT_language(String t_language) {
        this.t_language = t_language;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public Integer getT_status() {
        return t_status;
    }

    public void setT_status(Integer t_status) {
        this.t_status = t_status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getText_length() {
        return text_length;
    }

    public void setText_length(Integer text_length) {
        this.text_length = text_length;
    }
}
