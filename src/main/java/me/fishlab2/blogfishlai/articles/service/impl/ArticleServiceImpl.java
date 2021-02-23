package me.fishlab2.blogfishlai.articles.service.impl;

import me.fishlab2.blogfishlai.articles.entity.Article;
import me.fishlab2.blogfishlai.articles.repository.ArticleRepository;
import me.fishlab2.blogfishlai.articles.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public List<Article> getArticleList() {
        return articleRepository.findAll();
    }

    @Override
    public Article findArticleById(long id) {
        return articleRepository.findById(id);
    }
}