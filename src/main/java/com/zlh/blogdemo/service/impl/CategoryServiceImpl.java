package com.zlh.blogdemo.service.impl;

import com.zlh.blogdemo.dao.mapper.CategoryMapper;
import com.zlh.blogdemo.dao.pojo.Category;
import com.zlh.blogdemo.service.CategoryService;
import com.zlh.blogdemo.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName CategoryServiceImpl
 * @Description TODO
 * @Author Ember_hui Email:Ember_hui@163.com
 * @Date 2021/11/14 15:52
 * @Version 1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryVo findCategoryById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }
}
