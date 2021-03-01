package me.fishlab2.blogfishlai.exhibition.service.impl;

import me.fishlab2.blogfishlai.exhibition.entity.MyCollection;
import me.fishlab2.blogfishlai.exhibition.repository.MyCollectionRepository;
import me.fishlab2.blogfishlai.exhibition.service.My_collectionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MyCollectionServiceImpl implements My_collectionService {

    @Autowired
    private MyCollectionRepository myCollectionRepository;

    @Override
    public List<MyCollection> getMyCollectionList() {
        return myCollectionRepository.findAll();
    }

    @Override
    public MyCollection findMyCollection(long id) {
        return myCollectionRepository.findById(id);
    }


}
