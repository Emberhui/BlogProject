package com.zlh.blogdemo.controller;

import com.zlh.blogdemo.service.LoginService;
import com.zlh.blogdemo.vo.Result;
import com.zlh.blogdemo.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author Ember_hui Email:Ember_hui@163.com
 * @Date 2021/11/13 15:09
 * @Version 1.0
 */

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParam loginParam){
        return loginService.login(loginParam);
    }
}
