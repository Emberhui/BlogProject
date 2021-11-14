package com.zlh.blogdemo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zlh.blogdemo.dao.dos.Archives;
import com.zlh.blogdemo.dao.pojo.Article;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {

    List<Archives> listArchives();
}
