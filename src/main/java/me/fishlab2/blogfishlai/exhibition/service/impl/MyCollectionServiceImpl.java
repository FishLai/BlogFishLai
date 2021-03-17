package me.fishlab2.blogfishlai.exhibition.service.impl;

import me.fishlab2.blogfishlai.exhibition.entity.MyCollection;
import me.fishlab2.blogfishlai.exhibition.entity.constraint.MyDateConstraint;
import me.fishlab2.blogfishlai.exhibition.repository.MyCollectionRepository;
import me.fishlab2.blogfishlai.exhibition.service.MyCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintTarget;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Validated
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

    @MyDateConstraint
    public void doSetStartAndStopDates(@NotNull String strStartDate, @NotNull String strStopDate, MyCollection myColl) {
        myColl.setStartAndStopDates(strStartDate, strStopDate);
    }

    @MyDateConstraint(validationAppliesTo= ConstraintTarget.PARAMETERS)
    public HashMap<String, Date> doTransformDate(String startDate, String stopDate) {
        SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM");
        try {
            HashMap<String, Date> mapDate = new HashMap<String, Date>();
            Date beginDate = sDF.parse(startDate);
            mapDate.put("startDate", beginDate);
            if( stopDate != "") {
                Date endDate = sDF.parse(stopDate);
                mapDate.put("stopDate", endDate);
            } else mapDate.put("stopDate", null);
            return mapDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
