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
    private int page = 1;
    private  int pageSize = 10;
}
