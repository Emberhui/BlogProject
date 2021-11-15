package com.zlh.blogdemo.service;

import com.zlh.blogdemo.vo.Result;
import com.zlh.blogdemo.vo.params.CommentParam;

public interface CommentsService {

    Result commentsByArticleId(Long id);

    Result comment(CommentParam commentParam);
}
