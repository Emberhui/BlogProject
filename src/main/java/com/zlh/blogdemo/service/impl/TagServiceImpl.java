package com.zlh.blogdemo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zlh.blogdemo.dao.mapper.TagMapper;
import com.zlh.blogdemo.dao.pojo.Tag;
import com.zlh.blogdemo.service.TagService;
import com.zlh.blogdemo.vo.Result;
import com.zlh.blogdemo.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName TagServiceImpl
 * @Description TODO
 * @Author Ember_hui Email:Ember_hui@163.com
 * @Date 2021/11/11 18:56
 * @Version 1.0
 */

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;


    @Override
    public Result findAll() {
        return Result.success(copyList(tagMapper.selectList(new LambdaQueryWrapper<>())));
    }

    @Override
    public Result hots(int limit) {
//        标签所拥有的文章id最多，为最热标签
//        查询 根据tag_id 分组计数 从大到小排列，取前limit个
        List<Long> tagIds = tagMapper.findHotsTagIds(limit);
        if (CollectionUtils.isEmpty(tagIds)){
//            如果为空给个空值
            return Result.success(Collections.emptyList());
        }
//        需要的是 tag_id 和 tag_name  Tag对象
//        select * from tag where id in (1,2,3)
        List<Tag> tagList = tagMapper.findTagsByTagIds(tagIds);
        return Result.success(tagList);
    }

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
//        MybatisPlus 无法进行多表查询
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    private List<TagVo> copyList(List<Tag> tags) {
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tags){
                tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    private TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }
}
