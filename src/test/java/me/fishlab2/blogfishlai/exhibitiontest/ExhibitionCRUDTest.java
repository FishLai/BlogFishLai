package me.fishlab2.blogfishlai.exhibitiontest;

import me.fishlab2.blogfishlai.exhibition.entity.Tech;
import me.fishlab2.blogfishlai.exhibition.entity.MyCollection;
import me.fishlab2.blogfishlai.exhibition.repository.TechRepository;
import me.fishlab2.blogfishlai.exhibition.repository.MyCollectionRepository;
import me.fishlab2.blogfishlai.exhibition.service.impl.MyCollectionServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.HibernateValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 測試
 * 作品與作品技術 CRUD
 * 增加、搜尋、刪除、更新
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class ExhibitionCRUDTest {
    Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private MyCollectionServiceImpl myCollectionServiceImpl;

    @Autowired
    private MyCollectionRepository myCollectionRepository;

    @Autowired
    private TechRepository collTechRepository;

    @Autowired
    Validator  validator;


    @Test
    public void add() {

        /*
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        MyCollection myCollection = new MyCollection();
        myCollection.setName("test2");
        myCollection.setCollAbs("it is a test2");
        myCollection.setCoverPath("/");

        myCollectionRepository.save(myCollection);

        Tech collTech = new Tech();
        collTech.setName("HTML");
        collTech.setColl(myCollection);

        Tech collTech1 = new Tech();
        collTech1.setColl(myCollection);
        collTech1.setName("Java");

        Tech collTech2 = new Tech();
        collTech2.setColl(myCollection);
        collTech2.setName("CSS");

        collTechRepository.save(collTech1);
        collTechRepository.save(collTech2);
        collTechRepository.save(collTech);
         */
        MyCollection bean = MyCollection.builder()
                .name("test save")
                .strStartDate("1992-02")
                .strStopDate("1993-02")
                .collAbs("test repository save")
                .build();


        myCollectionRepository.save(bean);
    }

    @Test
    public void find() {
        MyCollection coll;
        coll = myCollectionRepository.findById(1);
        System.out.println(coll);
        List<Tech> list = coll.getTechList();
        System.out.println(coll.getName());
        System.out.println(list.size());
        for (Tech tech: list) {
            System.out.println(tech.getName());
        }
    }

    @Test
    public void deleteColl() {
        myCollectionRepository.deleteById(1);
    }

    @Test
    public void findAllCollTest() {
        List<MyCollection> myCollections;
        myCollections = myCollectionRepository.findAll();
        for(MyCollection myCollection: myCollections){
            System.out.println(myCollection);
            for(Tech tech: myCollection.getTechList())
                System.out.println(tech.getName());
        }
    }

    @Test
    public void deleteTech() {
        MyCollection myColl = myCollectionRepository.findById(3);
        collTechRepository.deleteByCollAndName(myColl, "CSS");
    }

    @Test
    public void updateColl() {
        MyCollection  myCollection = myCollectionRepository.findById(2);
        myCollection.setCoverPath("collections/img/admin/my_collection/cover_my_collection.JPG");
        myCollectionRepository.save(myCollection);
    }

    /*
     * 測試找出欄位 coll_name 所有值
     */
    @Test
    public void findNamesTest() {
        List<String> names = myCollectionRepository.findNames();
        for(String name: names) {
            System.out.println(name);
        }
    }

}