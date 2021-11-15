package com.zlh.blogdemo.service;


import com.zlh.blogdemo.dao.pojo.SysUser;
import com.zlh.blogdemo.vo.ArticleBodyVo;
import com.zlh.blogdemo.vo.CommentVo;
import com.zlh.blogdemo.vo.Result;
import com.zlh.blogdemo.vo.UserVo;

import java.util.List;

public interface SysUserService {

    UserVo findUserVoById(Long id);

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    Result findUserByToken(String token);

    SysUser findUserByAccount(String account);

    void save(SysUser sysUserNew);

}
