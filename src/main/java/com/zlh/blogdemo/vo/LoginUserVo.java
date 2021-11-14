package com.zlh.blogdemo.vo;

import lombok.Data;

/**
 * @ClassName LoginUserVo
 * @Description TODO
 * @Author Ember_hui Email:Ember_hui@163.com
 * @Date 2021/11/13 17:04
 * @Version 1.0
 */

@Data
public class LoginUserVo {
    private Long id;
    private String account;
    private String nickname;
    private String avatar;
}
