package com.zlh.blogdemo.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.zlh.blogdemo.dao.mapper.ArticleMapper;
import com.zlh.blogdemo.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {
//      此操作在线程池中执行，不影响原来主线程
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        int viewCounts = article.getViewCounts();
        Article article1 = new Article();
        article1.setViewCounts(viewCounts + 1);
        LambdaUpdateWrapper<Article> articleLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        articleLambdaUpdateWrapper.eq(Article::getId,article.getId());
//        设置一个 为了在多线程的环境下 线程安全
        articleLambdaUpdateWrapper.eq(Article::getViewCounts,viewCounts);
//        update article set view_count = 100 where view_count = 99 and id = **
//        UPDATE ms_article SET view_counts=? WHERE (id = ? AND view_counts = ?)
        articleMapper.update(article1,articleLambdaUpdateWrapper);
        try {
            Thread.sleep(5000);
            System.out.println("更新完成了……………………");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
