package me.fishlab2.blogfishlai.exhibition.service.impl;

import me.fishlab2.blogfishlai.exhibition.entity.MyCollection;
import me.fishlab2.blogfishlai.exhibition.repository.MyCollectionRepository;
import me.fishlab2.blogfishlai.exhibition.service.MyCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyCollectionServiceImpl implements MyCollectionService {

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

    /*
     * 將字串留下[A-Za-z0-9]，
     * 比較是否重複
     */
    @Override
    public boolean isUsed(String s) {
        List<String> names = myCollectionRepository.findNames();
        boolean isNamed = false;
        String cleanStr = s.replaceAll("[\\W_]", "");
        for(String name: names) {
            name = name.replaceAll("[\\W_]", "");
            if(cleanStr.equalsIgnoreCase(name)) isNamed=true;
        }
        return isNamed;
    }

}
