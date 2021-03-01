package me.fishlab2.blogfishlai.exhibition.service.impl;

import me.fishlab2.blogfishlai.exhibition.entity.MyCollection;
import me.fishlab2.blogfishlai.exhibition.entity.Tech;
import me.fishlab2.blogfishlai.exhibition.repository.TechRepository;
import me.fishlab2.blogfishlai.exhibition.service.TechService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TechServiceImpl implements TechService {

    @Autowired
    private TechRepository collTechRepository;

    @Override
    public List<Tech> findTechByColl(MyCollection coll) {
        return collTechRepository.findByColl(coll);
    }

    @Override
    public List<Tech> findTechByName(String name) {
        return collTechRepository.findByName(name);
    }

    @Override
    public void deleteByCollAndName(MyCollection coll, String name) {
        collTechRepository.deleteByCollAndName(coll, name);
    }
}
