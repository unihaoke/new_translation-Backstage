package com.trans.translation.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author cwh
 * @date 2020/3/11 15:57
 * 用户小组关系表
 */
@Entity
@Table(name = "trans_user_group")
public class UserGroup implements Serializable {

    @Id
    private String id;

    private Integer status;

    private String userid;

    private String groupid;

    private String position;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date jointime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getJointime() {
        return jointime;
    }

    public void setJointime(Date jointime) {
        this.jointime = jointime;
    }

    public UserGroup(String id, Integer status, String userid, String groupid, String position) {
        this.id = id;
        this.status = status;
        this.userid = userid;
        this.groupid = groupid;
        this.position = position;
    }

    public UserGroup(String id, Integer status, String userid, String groupid, String position, Date jointime) {
        this.id = id;
        this.status = status;
        this.userid = userid;
        this.groupid = groupid;
        this.position = position;
        this.jointime = jointime;
    }

    public UserGroup() {
    }
}
