package com.zlh.blogdemo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @ClassName TagVo
 * @Description TODO
 * @Author Ember_hui Email:Ember_hui@163.com
 * @Date 2021/11/11 16:05
 * @Version 1.0
 */

@Data
public class TagVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String tagName;
}
