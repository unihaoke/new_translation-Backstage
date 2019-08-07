package com.trans.translation.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

public class TranslationVo implements Serializable {

    private String title;//标题

    private String t_describe;//描述

    private String t_language;//翻译类型

    private String territory;//翻译领域

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date deadline;

    private Integer overdue;//0未过期，1过期

    private String userid;

    private String subpackageid;

    private String product_id;

    private String translator;

    private Integer status;

    private String translation;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    public TranslationVo(String title, String t_describe, String t_language, String territory, Date deadline, Integer overdue, String userid, String subpackageid, String product_id, String translator, Integer status, String translation, Date createtime) {
        this.title = title;
        this.t_describe = t_describe;
        this.t_language = t_language;
        this.territory = territory;
        this.deadline = deadline;
        this.overdue = overdue;
        this.userid = userid;
        this.subpackageid = subpackageid;
        this.product_id = product_id;
        this.translator = translator;
        this.status = status;
        this.translation = translation;
        this.createtime = createtime;
    }

    public TranslationVo() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSubpackageid() {
        return subpackageid;
    }

    public void setSubpackageid(String subpackageid) {
        this.subpackageid = subpackageid;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
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

    public Integer getOverdue() {
        return overdue;
    }

    public void setOverdue(Integer overdue) {
        this.overdue = overdue;
    }
}
