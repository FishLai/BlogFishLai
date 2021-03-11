package me.fishlab2.blogfishlai.exhibition.service;

import me.fishlab2.blogfishlai.exhibition.entity.MyCollection;

import java.util.List;

public interface MyCollectionService {
    public List<MyCollection> getMyCollectionList();
    public MyCollection findMyCollection(long coll_no);
    public boolean isUsed(String s);
}
