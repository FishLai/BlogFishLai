package me.fishlab2.blogfishlai.exhibition.repository;

import me.fishlab2.blogfishlai.exhibition.entity.MyCollection;
import me.fishlab2.blogfishlai.exhibition.entity.Tech;
import me.fishlab2.blogfishlai.exhibition.entity.compositepk.TechPk;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface TechRepository extends JpaRepository<Tech, TechPk> {
    List<Tech> findByName(String name);
    List<Tech> findByColl(MyCollection coll);

    @Transactional
    void deleteByCollAndName(MyCollection coll, String name);
}
