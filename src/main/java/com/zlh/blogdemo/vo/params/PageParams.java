package com.zlh.blogdemo.vo.params;

import lombok.Data;

/**
 * @ClassName PageParams
 * @Description TODO
 * @Author Ember_hui Email:Ember_hui@163.com
 * @Date 2021/11/11 15:28
 * @Version 1.0
 */

@Data
public class PageParams {

    private Integer page = 1;

    private  Integer pageSize = 10;

    private Long categoryId;

    private Long tagId;

    private String year;

    private String month;

//    获取月份时需要处理，月份为一位时需加上0，与sql语句相适配
    public String getMonth(){
        if (this.month != null && this.month.length() == 1){
            return "0"+this.month;
        }
        return this.month;
    }
}
