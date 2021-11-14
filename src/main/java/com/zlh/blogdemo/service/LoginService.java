package com.zlh.blogdemo.service;

import com.zlh.blogdemo.dao.pojo.SysUser;
import com.zlh.blogdemo.vo.Result;
import com.zlh.blogdemo.vo.params.LoginParam;


public interface LoginService {

    Result login(LoginParam loginParam);

    SysUser checkToken(String token);

    Result logout(String token);

    Result register(LoginParam loginParam);
}
