package com.trans.translation.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class TranslationKey implements Serializable {
    @Id
    @Column(name = "userid", nullable = false)
    private String userid;

    @Id
    @Column(name = "subpackageid", nullable = false)
    private String subpackageid;

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
}
