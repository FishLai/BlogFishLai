package me.fishlab2.blogfishlai.articles.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Data
public class Article extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "標題不能為空")
    private String title;

    @Column(nullable = false)
    private String body;
}
