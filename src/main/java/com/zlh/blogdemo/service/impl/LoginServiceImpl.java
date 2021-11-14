package com.zlh.blogdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.zlh.blogdemo.dao.pojo.SysUser;
import com.zlh.blogdemo.service.LoginService;
import com.zlh.blogdemo.service.SysUserService;
import com.zlh.blogdemo.utils.JWTUtils;
import com.zlh.blogdemo.vo.ErrorCode;
import com.zlh.blogdemo.vo.Result;
import com.zlh.blogdemo.vo.params.LoginParam;
import org.apache.commons.codec.cli.Digest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName LoginServiceImpl
 * @Description TODO
 * @Author Ember_hui Email:Ember_hui@163.com
 * @Date 2021/11/13 15:12
 * @Version 1.0
 */

@Service
@Transactional   //添加事务功能
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

//  加密语言
    private static final String slat = "mszlu!@#";

    @Override
    public Result register(LoginParam loginParam) {
//        判断参数是否合法
//        判断账户是否存在
//        注册用户
//        生成token 传入redis 并返回
//        注意，加上事务，中间出现问题回滚
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password) || StringUtils.isBlank(nickname)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser = sysUserService.findUserByAccount(account);
        if (sysUser != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        SysUser sysUserNew = new SysUser();
        sysUserNew.setAccount(account);
        String passCode = password + slat;
        sysUserNew.setPassword(DigestUtils.md5DigestAsHex(passCode.getBytes()));
        sysUserNew.setNickname(nickname);
        sysUserNew.setCreateDate(System.currentTimeMillis());
        sysUserNew.setLastLogin(System.currentTimeMillis());
        sysUserNew.setAvatar("static/img/logo.png");
        sysUserNew.setAdmin(1);
        sysUserNew.setDeleted(0);
        sysUserNew.setSalt("");
        sysUserNew.setStatus("");
        sysUserNew.setEmail("");

        sysUserService.save(sysUserNew);
        String token = JWTUtils.createToken(sysUserNew.getId());

        redisTemplate.opsForValue().set("TOKEN_"+token,JSON.toJSONString(sysUserNew),1,TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }

    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null){
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)){
//            过期了
            return null;
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    @Override
    public Result login(LoginParam loginParam) {
//        检查参数是否合法，
//        根据用户名和密码去user中查询，是否存在
//        如果不存在，登录失败
//        如果存在，使用jwt，生成token，返回给前端
//        token放入redis中， redis token：user信息 设置过期时间
//        登录认证时，先认证token字符串是否合法，去redis认证是否存在
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
//        md5加密密码
        String passCode = password+slat;
        password= DigestUtils.md5DigestAsHex(passCode.getBytes());
        SysUser sysUser = sysUserService.findUser(account,password);
        if (sysUser == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }
}
