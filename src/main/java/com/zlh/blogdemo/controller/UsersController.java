package com.zlh.blogdemo.controller;

import com.zlh.blogdemo.service.SysUserService;
import com.zlh.blogdemo.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName UsersController
 * @Description TODO
 * @Author Ember_hui Email:Ember_hui@163.com
 * @Date 2021/11/13 16:49
 * @Version 1.0
 */
@RestController
@RequestMapping("users")
public class UsersController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){

        return sysUserService.findUserByToken(token);
    }
}
