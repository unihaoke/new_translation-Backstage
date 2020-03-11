package com.trans.translation.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "trans_translation")
@EntityListeners(AuditingEntityListener.class)
public class Translation implements Serializable {


    @Id
    private String id;//ID

    private String userid;

    private String subpackageid;

    private String product_id;

    private String translator;

    private Integer status;

    private String translation;

    private String content;

    private Integer t_length;

    @CreatedDate
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    @LastModifiedDate
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getT_length() {
        return t_length;
    }

    public void setT_length(Integer t_length) {
        this.t_length = t_length;
    }
}
