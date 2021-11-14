package com.zlh.blogdemo.controller;

import com.zlh.blogdemo.service.ArticleService;
import com.zlh.blogdemo.vo.ArticleVo;
import com.zlh.blogdemo.vo.Result;
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
    @PostMapping
    public Result listArticle(@RequestBody PageParams pageParams){

        return articleService.listArticle(pageParams);
    }

    @PostMapping("hot")
    public Result hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    @PostMapping("new")
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
