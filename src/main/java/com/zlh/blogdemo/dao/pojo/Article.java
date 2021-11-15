package com.zlh.blogdemo.dao.pojo;

import lombok.Data;

/**
 * @ClassName Article
 * @Description TODO
 * @Author Ember_hui Email:Ember_hui@163.com
 * @Date 2021/11/11 15:16
 * @Version 1.0
 */

@Data
public class Article {
    public static final int Article_TOP = 1;
    public static final int Article_Common = 0;

    private Long id;
    private String title;
    private String summary;
    private Integer commentCounts;
    private Integer viewCounts;

    /**
     * 作者id
     */
    private Long authorId;
    /**
     * 内容id
     */
    private Long bodyId;
    /**
     *类别id
     */
    private Long categoryId;

    /**
     * 置顶
     */
    private Integer weight;


    /**
     * 创建时间
     */
    private Long createDate;
}
