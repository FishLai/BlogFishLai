package me.fishlab2.blogfishlai.articles.service;

import me.fishlab2.blogfishlai.articles.entity.Article;

import java.util.List;

public interface ArticleService {
    public List<Article> getArticleList();
    public Article findArticleById(long id);
}
