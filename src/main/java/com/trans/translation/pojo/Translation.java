package com.trans.translation.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "trans_translation")
@IdClass(TranslationKey.class)
public class Translation implements Serializable {

    @Id
    @Column(name = "userid", nullable = false)
    private String userid;

    @Id
    @Column(name = "subpackageid", nullable = false)
    private String subpackageid;

    private String translator;

    private String title;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date deadline;

    private Integer section;

    private Integer status;

    private String translation;


    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatetime;

    private Integer isget;

    public Integer getIsget() {
        return isget;
    }

    public void setIsget(Integer isget) {
        this.isget = isget;
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

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
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

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}
