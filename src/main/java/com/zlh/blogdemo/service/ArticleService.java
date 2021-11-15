package com.zlh.blogdemo.service;


import com.zlh.blogdemo.vo.ArticleVo;
import com.zlh.blogdemo.vo.Result;
import com.zlh.blogdemo.vo.params.ArticleParam;
import com.zlh.blogdemo.vo.params.PageParams;

public interface ArticleService {

    /**
     * 分页查询 文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    Result hotArticle(int limit);

    Result newArticle(int limit);
//       文章归档
    Result listArchives();

    ArticleVo findArticleById(Long id);

    Result publish(ArticleParam articleParam);
}
