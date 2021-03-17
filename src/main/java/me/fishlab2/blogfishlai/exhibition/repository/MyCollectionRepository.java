package me.fishlab2.blogfishlai.exhibition.repository;

import me.fishlab2.blogfishlai.exhibition.entity.MyCollection;
import me.fishlab2.blogfishlai.exhibition.service.impl.MyCollectionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MyCollectionRepository extends JpaRepository<MyCollection, Long> {

    final String FIND_VALUES = "SELECT ";

    public MyCollection findById(long id);
    public void deleteById(long id);

    @Query(value="SELECT mc.coll_name FROM my_collection mc", nativeQuery=true)
    List<String> findNames();

    // Todo 等待測試，
    //  sql with replace name，
    @Query(value="SELECT COUNT(*) FROM my_collection mc WHERE REGEXP_REPLACE(mc.coll_name, '[[:space:][:punct:]]') = ?1", nativeQuery=true)
    long countByUniqueName(String collName);
}
