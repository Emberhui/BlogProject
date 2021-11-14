package com.zlh.blogdemo.dao.dos;

import lombok.Data;

/**
 * @ClassName Archives
 * @Description DOS文件夹下保存的是不需要持久化的数据
 * @Author Ember_hui Email:Ember_hui@163.com
 * @Date 2021/11/12 14:14
 * @Version 1.0
 */

@Data
public class Archives {
    private Integer year;
    private Integer month;
    private Long count;
}
