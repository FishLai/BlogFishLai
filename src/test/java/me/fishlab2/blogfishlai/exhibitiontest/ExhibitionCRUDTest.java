package me.fishlab2.blogfishlai.exhibitiontest;

import me.fishlab2.blogfishlai.exhibition.entity.MyCollection;
import me.fishlab2.blogfishlai.exhibition.entity.Tech;
import me.fishlab2.blogfishlai.exhibition.repository.MyCollectionRepository;
import me.fishlab2.blogfishlai.exhibition.repository.TechRepository;
import me.fishlab2.blogfishlai.exhibition.service.impl.MyCollectionServiceImpl;
import me.fishlab2.blogfishlai.javaconfig.MethodValidationConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Iterator;
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
    private MyCollectionServiceImpl myCollectionService;

    /*public ExhibitionCRUDTest() {};
    @Autowired
    public ExhibitionCRUDTest(MyCollectionServiceImpl mCSI) {
        this.myCollectionService = mCSI;
    }*/

    @Autowired
    private MyCollectionRepository myCollectionRepository;

    @Autowired
    private TechRepository collTechRepository;


    @Test
    public void insertTest() {
        /*
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
        /*
        MyCollection collEntity = MyCollection.builder()
                .name("test save")
                .collAbs("test repository save")
                .coverPath(null)
                .build();

        collEntity.setStartAndStopDates("1994-05", "1994-05");

        Assert.assertNotNull(collEntity);

         */
        MyCollection collEntity = new MyCollection();

        //Todo costom unique validator cannot use
        // cross-parameters validator 怎麼自動執行時驗證
        collEntity.setName("test 261!@ # $");
        collEntity.setCollAbs("test insert wrong dates");

        myCollectionService.doSetStartAndStopDates("2000-02", "2000-05", collEntity);

        //collEntity.setStartAndStopDates("3000-02", "1000-05");


        myCollectionRepository.save(collEntity);
    }

    @Test
    public void findAllTest() {
        MyCollection coll;
        Iterator<MyCollection> colls = myCollectionRepository.findAll().iterator();
        while(colls.hasNext()) {
            logger.info(colls.next().toString());
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