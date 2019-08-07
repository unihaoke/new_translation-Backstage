package com.trans.translation.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "trans_product")
public class Product implements Serializable {

    @Id
    private String pid;//ID

    private String title;//标题

    private String t_describe;//描述

    private String t_language;//翻译类型

    private String territory;//翻译领域

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deadline;

    private Integer overdue;//0未过期，1过期

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public Integer getOverdue() {
        return overdue;
    }

    public void setOverdue(Integer overdue) {
        this.overdue = overdue;
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
}
