package com.zlh.blogdemo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlh.blogdemo.dao.dos.Archives;
import com.zlh.blogdemo.dao.mapper.ArticleBodyMapper;
import com.zlh.blogdemo.dao.mapper.ArticleMapper;
import com.zlh.blogdemo.dao.mapper.ArticleTagMapper;
import com.zlh.blogdemo.dao.mapper.CategoryMapper;
import com.zlh.blogdemo.dao.pojo.*;
import com.zlh.blogdemo.service.*;
import com.zlh.blogdemo.utils.UserThreadLocal;
import com.zlh.blogdemo.vo.*;
import com.zlh.blogdemo.vo.params.ArticleParam;
import com.zlh.blogdemo.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.java2d.pipe.AAShapePipe;

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
@Transactional
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

    @Autowired
    private ThreadService threadService;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public Result publish(ArticleParam articleParam) {
//       获取登录的用户信息
        SysUser sysUser = UserThreadLocal.get();
//        设置发布文章信息，插入数据库中
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setCategoryId(articleParam.getCategory().getId());
        article.setCreateDate(System.currentTimeMillis());
        article.setCommentCounts(0);
        article.setSummary(articleParam.getSummary());
        article.setTitle(articleParam.getTitle());
        article.setViewCounts(0);
        article.setWeight(Article.Article_Common);
        article.setBodyId(-1L);
//        插入之后会生成文章id
        articleMapper.insert(article);
//      标签加入关联列表中
        List<TagVo> tagList = articleParam.getTags();
        if (tagList != null){
            for (TagVo tag: tagList){
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(tag.getId());
                articleTagMapper.insert(articleTag);
            }
        }
//      body信息内容存储
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);
//      文章设置body id
        article.setBodyId(articleBody.getId());
//      更新
        articleMapper.updateById(article);
//      返回数据类型 {"id":12232323}
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(article.getId());
        return Result.success(articleVo);
    }

    @Override
    public ArticleVo findArticleById(Long id) {
        Article article = articleMapper.selectById(id);
//        查看完文章后，做了更新操作，更新时加写锁，阻塞其他的读操作，性能会比较低
//        更新 增加了此次接口的耗时  如果一旦更新出问题，不能影响查看文章的操作
//        解决：将更新操作扔到线程池中去执行
        threadService.updateArticleViewCount(articleMapper,article);
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

//    @Override
//    public Result listArticle(PageParams pageParams) {
////        分页查询article数据库表
//        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
////        查询条件
//        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        if (pageParams.getCategoryId() != null){
////            where ... and category_id = #{categoryId}
//            lambdaQueryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
//        }
//        List<Long> articleIdList = new ArrayList<>();
//        if (pageParams.getTagId() != null){
////            加入标签条件查询
////            article表中并没有tag字段，一篇文章可能有多个标签
////            article_tag表  article_id 1:n tag_id
//            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId, pageParams.getTagId());
//            List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
//            for (ArticleTag articleTag : articleTags) {
//                articleIdList.add(articleTag.getArticleId());
//            }
//            if (articleIdList.size() > 0){
////              and id in (..,..,..)
//                lambdaQueryWrapper.in(Article::getId,articleIdList);
//            }
//        }
////        是否置顶进行排序
////        lambdaQueryWrapper.orderByDesc(Article::getWeight);
////        按照create date 倒序排列
//        lambdaQueryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
//
//        Page<Article> page1 = articleMapper.selectPage(page, lambdaQueryWrapper);
//        List<Article> records = page1.getRecords();
//        List<ArticleVo> articleVoList = copyList(records,true,true);
//        return Result.success(articleVoList);
//    }


//    自己实现
    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticle(page, pageParams.getCategoryId(), pageParams.getTagId(),
                pageParams.getYear(), pageParams.getMonth());
        List<Article> records = articleIPage.getRecords();
        return Result.success(copyList(records,true,true));
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
