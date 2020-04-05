package com.trans.translation.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author cwh
 * @date 2020/3/11 15:34
 * 审批实体类
 */
@Entity
@Table(name = "trans_approval")
public class Approval implements Serializable {

    @Id
    private String id;

    private String subpackageid;

    private String taskid;

    private String userid;

    private Integer status;

    private String groupid;

    private String transid;

    private String translation;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubpackageid() {
        return subpackageid;
    }

    public void setSubpackageid(String subpackageid) {
        this.subpackageid = subpackageid;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getTransid() {
        return transid;
    }


    public void setTransid(String transid) {
        this.transid = transid;
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
}
