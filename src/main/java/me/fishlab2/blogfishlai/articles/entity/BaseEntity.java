package me.fishlab2.blogfishlai.articles.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @CreatedDate
    private Long createTime;

    @LastModifiedDate
    private Long updateTime;

    @Column(name = "create_by")
    @CreatedBy
    private Long createBy;

    @Column(name = "lastmodified_by")
    @LastModifiedBy
    private String lastModifiedBy;
}
