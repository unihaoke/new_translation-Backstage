package com.trans.translation.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 */
@Entity
@Table(name = "trans_user")
public class User implements Serializable {

    @Id
    private String id;//ID

    private String username;

    private String phone;

    private Integer u_status;

    private String email;

    private Integer accept;

    private Integer totality;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getU_status() {
        return u_status;
    }

    public void setU_status(Integer u_status) {
        this.u_status = u_status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAccept() {
        return accept;
    }

    public void setAccept(Integer accept) {
        this.accept = accept;
    }

    public Integer getTotality() {
        return totality;
    }

    public void setTotality(Integer totality) {
        this.totality = totality;
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
