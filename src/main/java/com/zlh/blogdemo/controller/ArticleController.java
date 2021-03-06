package com.zlh.blogdemo.controller;

import com.zlh.blogdemo.common.aop.LogAnnotation;
import com.zlh.blogdemo.common.cache.Cache;
import com.zlh.blogdemo.service.ArticleService;
import com.zlh.blogdemo.vo.ArticleVo;
import com.zlh.blogdemo.vo.Result;
import com.zlh.blogdemo.vo.params.ArticleParam;
import com.zlh.blogdemo.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName ArticleController
 * @Description TODO
 * @Author Ember_hui Email:Ember_hui@163.com
 * @Date 2021/11/11 15:29
 * @Version 1.0
 */

@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /** 
    *@author Ember_hui Email:Ember_hui@163.com
    *@Description 首页，文章列表
    *@Date 15:38 2021/11/11
    *@Params PageParams
    *@Return Result
    */

    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }


    @PostMapping
    @LogAnnotation(module = "文章",operator = "获取文章列表")
//    @Cache(expire = 5 * 60 * 1000,name = "listArticle")
    public Result listArticle(@RequestBody PageParams pageParams){

        return articleService.listArticle(pageParams);
    }

    @PostMapping("hot")
//    @Cache(expire = 5 * 60 * 1000,name = "hotArticle")
    public Result hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    @PostMapping("new")
//    @Cache(expire = 5 * 60 * 1000,name = "newArticle")
    public Result newArticle(){
        int limit = 4;
        return articleService.newArticle(limit);
    }

    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }

    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long id){
               ArticleVo articleVo = articleService.findArticleById(id);
               return Result.success(articleVo);
    }
}
