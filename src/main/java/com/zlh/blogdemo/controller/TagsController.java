package com.zlh.blogdemo.controller;

import com.zlh.blogdemo.service.TagService;
import com.zlh.blogdemo.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TagsController
 * @Description TODO
 * @Author Ember_hui Email:Ember_hui@163.com
 * @Date 2021/11/12 12:54
 * @Version 1.0
 */

@RestController
@RequestMapping("tags")
public class TagsController {

    @Autowired
    private TagService tagService;

    @GetMapping("detail/{id}")
    public Result findAllDetailById(@PathVariable("id") Long id){
        return tagService.findAllDetailById(id);
    }

    @GetMapping("detail")
    public Result findAllDetail(){
        return tagService.findAllDetail();
    }

    @GetMapping
    public Result findAll(){
        return tagService.findAll();
    }

    @GetMapping("hot")
    public Result hot(){
        int limit=6;
        return tagService.hots(limit);
    }
}
