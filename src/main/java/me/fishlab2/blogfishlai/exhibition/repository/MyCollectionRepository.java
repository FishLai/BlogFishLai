package me.fishlab2.blogfishlai.exhibition.repository;

import me.fishlab2.blogfishlai.exhibition.entity.MyCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyCollectionRepository extends JpaRepository<MyCollection, Long> {
    final String FIND_VALUES = "SELECT ";

    public MyCollection findById(long id);
    public void deleteById(long id);

    @Query(value="SELECT mc.coll_name FROM my_collection mc", nativeQuery=true)
    List<String> findNames();
}
