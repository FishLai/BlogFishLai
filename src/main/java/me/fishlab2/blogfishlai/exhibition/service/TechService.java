package me.fishlab2.blogfishlai.exhibition.service;

import me.fishlab2.blogfishlai.exhibition.entity.MyCollection;
import me.fishlab2.blogfishlai.exhibition.entity.Tech;

import java.util.List;

public interface TechService {
    public List<Tech> findTechByColl(MyCollection coll);
    public List<Tech> findTechByName(String name);
    public void deleteByCollAndName(MyCollection coll, String name);
}
