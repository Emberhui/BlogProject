package com.zlh.blogdemo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName Result
 * @Description TODO
 * @Author Ember_hui Email:Ember_hui@163.com
 * @Date 2021/11/11 15:30
 * @Version 1.0
 */

@Data
@AllArgsConstructor
public class Result {
    private boolean success;
    private int code;
    private String msg;
    private Object data;

    public static Result success(Object data){
        return new Result(true,200,"success",data);
    }

    public static Result fail(int code,String msg){
        return new Result(false,code,msg,null);
    }
}
