package com.zlh.blogdemo.service;


import com.zlh.blogdemo.dao.pojo.SysUser;
import com.zlh.blogdemo.vo.ArticleBodyVo;
import com.zlh.blogdemo.vo.Result;

public interface SysUserService {

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    Result findUserByToken(String token);

    SysUser findUserByAccount(String account);

    void save(SysUser sysUserNew);

}
