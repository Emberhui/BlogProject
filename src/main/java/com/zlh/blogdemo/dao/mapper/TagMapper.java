package com.zlh.blogdemo.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zlh.blogdemo.dao.pojo.Tag;
import com.zlh.blogdemo.vo.Result;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {

//    根据文章id查询标签列表
    List<Tag> findTagsByArticleId(Long articleId);

    List<Long> findHotsTagIds(int limit);

    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
