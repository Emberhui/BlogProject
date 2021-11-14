package com.zlh.blogdemo.dao.pojo;

import lombok.Data;

@Data
public class SysUser {

//    @TableId(type = IdType.ASSIGN_ID)  //mybatis-plus中默认id类型
//    以后用户多了，要进行分表操作，需要使用分布式id
//    @TableId(type = IdType.AUTO)   //数据库自增id
    private Long id;

    private String account;

    private Integer admin;

    private String avatar;

    private Long createDate;

    private Integer deleted;

    private String email;

    private Long lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String password;

    private String salt;

    private String status;
}