package com.zlh.blogdemo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlh.blogdemo.dao.dos.Archives;
import com.zlh.blogdemo.dao.mapper.ArticleBodyMapper;
import com.zlh.blogdemo.dao.mapper.ArticleMapper;
import com.zlh.blogdemo.dao.mapper.CategoryMapper;
import com.zlh.blogdemo.dao.pojo.Article;
import com.zlh.blogdemo.dao.pojo.ArticleBody;
import com.zlh.blogdemo.service.ArticleService;
import com.zlh.blogdemo.service.CategoryService;
import com.zlh.blogdemo.service.SysUserService;
import com.zlh.blogdemo.service.TagService;
import com.zlh.blogdemo.vo.ArticleBodyVo;
import com.zlh.blogdemo.vo.ArticleVo;
import com.zlh.blogdemo.vo.CategoryVo;
import com.zlh.blogdemo.vo.Result;
import com.zlh.blogdemo.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ArticleServiceImpl
 * @Description TODO
 * @Author Ember_hui Email:Ember_hui@163.com
 * @Date 2021/11/11 15:47
 * @Version 1.0
 */

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Autowired
    private CategoryService categoryService;

    @Override
    public ArticleVo findArticleById(Long id) {
        Article article = articleMapper.selectById(id);
        return copy(article,true,true,true,true);
    }

    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

    @Override
    public Result newArticle(int limit) {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Article::getCreateDate);
        lambdaQueryWrapper.select(Article::getId,Article::getTitle);
        lambdaQueryWrapper.last("limit "+limit);
        List<Article> articles = articleMapper.selectList(lambdaQueryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Article::getViewCounts);
        lambdaQueryWrapper.select(Article::getId,Article::getTitle);
        lambdaQueryWrapper.last("limit " + limit);
//        select id,title from article group by view_counts desc limit 5
        List<Article> articleList = articleMapper.selectList(lambdaQueryWrapper);
//        返回的是vo对象数组数据
        return Result.success(copyList(articleList,false,false));
    }

    @Override
    public Result listArticle(PageParams pageParams) {
//        分页查询article数据库表
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
//        查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        是否置顶进行排序
//        lambdaQueryWrapper.orderByDesc(Article::getWeight);
//        按照create date 倒序排列
        lambdaQueryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);

        Page<Article> page1 = articleMapper.selectPage(page, lambdaQueryWrapper);
        List<Article> records = page1.getRecords();
        List<ArticleVo> articleVoList = copyList(records,true,true);
        return Result.success(articleVoList);
    }



    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,false,false));
        }
        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor,boolean isBody) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,isBody,false));
        }
        return articleVoList;
    }
    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor,boolean isBody,boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,isBody,isCategory));
        }
        return articleVoList;
    }


    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
//      如果传的是空值，则是当前时间，里面有默认判断
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
//        不是所有的接口都需要 标签、作者信息
        if (isTag){
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        if (isBody){
            Long bodyId = article.getBodyId();
            articleVo.setBody(findBodyById(bodyId));
        }
        if (isCategory){
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }


    private ArticleBodyVo findBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }


}
