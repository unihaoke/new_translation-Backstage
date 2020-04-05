package com.trans.translation.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author cwh
 * @date 2020/3/11 15:52
 * 小组实体类
 */
@Entity
@Table(name = "trans_group")
public class Group implements Serializable {

    @Id
    private String id;

    private String group_name;

    private String userid;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createtime;

    private Integer count;//小组人数

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Group(String id, String group_name, String userid, Date createtime, Integer count) {
        this.id = id;
        this.group_name = group_name;
        this.userid = userid;
        this.createtime = createtime;
        this.count = count;
    }

    public Group() {
    }
}
