package com.zlh.blogdemo.controller;

import com.zlh.blogdemo.service.LoginService;
import com.zlh.blogdemo.service.SysUserService;
import com.zlh.blogdemo.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LogOutController
 * @Description TODO
 * @Author Ember_hui Email:Ember_hui@163.com
 * @Date 2021/11/13 18:38
 * @Version 1.0
 */

@RestController
@RequestMapping("logout")
public class LogOutController {

    @Autowired
    private LoginService loginService;

    @GetMapping
    public Result logout(@RequestHeader("Authorization") String token){
        return loginService.logout(token);
    }
}
