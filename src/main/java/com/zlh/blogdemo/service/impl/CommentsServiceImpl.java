package com.zlh.blogdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zlh.blogdemo.dao.mapper.CommentMapper;
import com.zlh.blogdemo.dao.pojo.Comment;
import com.zlh.blogdemo.dao.pojo.SysUser;
import com.zlh.blogdemo.service.CommentsService;
import com.zlh.blogdemo.service.SysUserService;
import com.zlh.blogdemo.utils.UserThreadLocal;
import com.zlh.blogdemo.vo.CommentVo;
import com.zlh.blogdemo.vo.Result;
import com.zlh.blogdemo.vo.UserVo;
import com.zlh.blogdemo.vo.params.CommentParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result comment(CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        commentMapper.insert(comment);
        return Result.success(null);
    }

    @Override
    public Result commentsByArticleId(Long id) {
//        根据文章id 查询评论列表（comment表）
//        根据作者id 查询作者信息
//        如果level = 1 查询有没有子评论
//        如果有，根据评论id进行查询（parentid）
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,id);
        queryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVoList = copyList(comments);
        return Result.success(commentVoList);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoArrayList = new ArrayList<>();
        for (Comment comment : comments){
            commentVoArrayList.add(copy(comment));
        }
        return commentVoArrayList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
//        作者信息
        Long authorId = comment.getAuthorId();
        UserVo userVo = sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);
//        子评论
        Integer level = comment.getLevel();
        if (1 == level){
            Long id = comment.getId();
            List<CommentVo> commentVoList = findUserVoByParentId(id);
            commentVo.setChildrens(commentVoList);
        }
//        给谁评论
        if (level > 1){
            Long toUid = comment.getToUid();
            UserVo userVo1 = sysUserService.findUserVoById(toUid);
            commentVo.setToUser(userVo1);
        }

        return commentVo;
    }

    private List<CommentVo> findUserVoByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);
        queryWrapper.eq(Comment::getLevel,2);
        return copyList(commentMapper.selectList(queryWrapper));
    }
}
