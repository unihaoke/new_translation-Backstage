package com.trans.translation.pojo;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author cwh
 * @date 2020/3/11 15:57
 * 用户小组关系表
 */
public class UserGroup implements Serializable {

    @Id
    private String id;

    private Integer status;

    private String userid;

    private String groupid;

    private String position;

}
