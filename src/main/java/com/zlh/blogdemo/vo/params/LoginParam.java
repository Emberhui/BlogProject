package com.zlh.blogdemo.vo.params;

import lombok.Data;

/**
 * @ClassName LoginParam
 * @Description TODO
 * @Author Ember_hui Email:Ember_hui@163.com
 * @Date 2021/11/13 15:13
 * @Version 1.0
 */
@Data
public class LoginParam {
    private String account;
    private String password;
    private String nickname;
}
