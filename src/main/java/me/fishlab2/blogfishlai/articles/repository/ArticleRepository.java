package me.fishlab2.blogfishlai.articles.repository;

import me.fishlab2.blogfishlai.articles.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
    Article findById(long id);
}
