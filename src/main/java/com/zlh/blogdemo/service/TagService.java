package com.zlh.blogdemo.service;


import com.zlh.blogdemo.vo.Result;
import com.zlh.blogdemo.vo.TagVo;

import java.util.List;

public interface TagService {

    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);
}
