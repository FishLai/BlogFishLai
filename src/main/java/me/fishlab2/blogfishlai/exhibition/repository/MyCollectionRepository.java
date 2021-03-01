package me.fishlab2.blogfishlai.exhibition.repository;

import me.fishlab2.blogfishlai.exhibition.entity.MyCollection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyCollectionRepository extends JpaRepository<MyCollection, Long> {
    public MyCollection findById(long id);
    public void deleteById(long id);
}
