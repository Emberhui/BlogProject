package com.zlh.blogdemo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlh.blogdemo.dao.dos.Archives;
import com.zlh.blogdemo.dao.pojo.Article;
import com.zlh.blogdemo.vo.params.PageParams;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {

    List<Archives> listArchives();

    IPage<Article> listArticle(Page<Article> page,
                               Long categoryId,
                               Long tagId,
                               String year,
                               String month);
}
