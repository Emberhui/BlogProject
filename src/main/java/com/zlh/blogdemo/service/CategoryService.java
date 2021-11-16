package com.zlh.blogdemo.service;

import com.zlh.blogdemo.vo.CategoryVo;
import com.zlh.blogdemo.vo.Result;


/**
 * @ClassName CategoryService
 * @Description TODO
 * @Author Ember_hui Email:Ember_hui@163.com
 * @Date 2021/11/14 15:51
 * @Version 1.0
 */
public interface CategoryService {

    CategoryVo findCategoryById(Long categoryId);

    Result findAll();

    Result findAllDetail();

    Result findAllDetailById(Long id);
}
