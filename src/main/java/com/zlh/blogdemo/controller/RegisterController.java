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
 * @ClassName RegisterController
 * @Description TODO
 * @Author Ember_hui Email:Ember_hui@163.com
 * @Date 2021/11/14 12:40
 * @Version 1.0
 */

@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result register(@RequestBody LoginParam loginParam){
//        sso 单点登录，后期如果把登录注册功能提出去（单独的服务，可以独立提供接口服务）
        return loginService.register(loginParam);
    }
}
