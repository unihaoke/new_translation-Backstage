package com.trans.translation.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

/**
 * 封装了Task和Product
 */
public class TaskVo implements Serializable {
    private String title;//标题

    private String t_describe;//描述

    private String t_language;//翻译类型

    private String territory;//翻译领域

    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private Date deadline;

    private Integer overdue;//0未过期，1过期

    private String userid;

    private String taskid;

    private String fileName;

    private Integer t_status;

    private String translatefile;//翻译的文件

    private Integer total;//共分为几段

    private Integer minSub;//分包最小字数

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createtime;


    public TaskVo(String title, String t_describe, String t_language, String territory, Date deadline, Integer overdue, String userid, String taskid, String fileName, Integer t_status, String translatefile, Integer total, Date createtime) {
        this.title = title;
        this.t_describe = t_describe;
        this.t_language = t_language;
        this.territory = territory;
        this.deadline = deadline;
        this.overdue = overdue;
        this.userid = userid;
        this.taskid = taskid;
        this.fileName = fileName;
        this.t_status = t_status;
        this.translatefile = translatefile;
        this.total = total;
        this.createtime = createtime;
    }



    public TaskVo() {
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public Integer getOverdue() {
        return overdue;
    }

    public void setOverdue(Integer overdue) {
        this.overdue = overdue;
    }

    public Integer getT_status() {
        return t_status;
    }

    public void setT_status(Integer t_status) {
        this.t_status = t_status;
    }

    public String getTranslatefile() {
        return translatefile;
    }

    public void setTranslatefile(String translatefile) {
        this.translatefile = translatefile;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public Integer getMinSub() {
        return minSub;
    }

    public void setMinSub(Integer minSub) {
        this.minSub = minSub;
    }
}
