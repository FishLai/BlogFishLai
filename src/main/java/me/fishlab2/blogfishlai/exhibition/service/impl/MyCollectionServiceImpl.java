package me.fishlab2.blogfishlai.exhibition.service.impl;

import me.fishlab2.blogfishlai.exhibition.entity.MyCollection;
import me.fishlab2.blogfishlai.exhibition.entity.Tech;
import me.fishlab2.blogfishlai.exhibition.entity.constraint.MyDateConstraint;
import me.fishlab2.blogfishlai.exhibition.repository.MyCollectionRepository;
import me.fishlab2.blogfishlai.exhibition.service.MyCollectionService;
import org.apache.commons.collections.Bag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.collection.internal.PersistentBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintTarget;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ExecutableValidator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Spliterator.ORDERED;

@Validated
@Service
public class MyCollectionServiceImpl implements MyCollectionService {

    final Logger logger = LogManager.getLogger();

    @Autowired
    private MyCollectionRepository myCollectionRepository;

    @Autowired
    Validator validator;

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

    /*
     * 提供controller 驗證日期
     */
    public HashMap<String, String> validateDates(@NotNull String strStartDate,@NotNull String strStopDate) throws NoSuchMethodException {
        /*
         * 手動對方法參數較驗
         */
        ExecutableValidator exValidator = validator.forExecutables();
        Iterator<ConstraintViolation<MyCollection>> viols = exValidator.validateParameters(
                new MyCollection(),
                MyCollection.class.getMethod("setStartAndStopDates", String.class, String.class),
                new Object[]{strStartDate, strStopDate}
        ).iterator();
        if(viols.hasNext()){
            ConstraintViolation<MyCollection> viol = viols.next();
            Iterator nodes = viol.getPropertyPath().iterator();
            Path.Node node = null;
            while(nodes.hasNext()) node = (Path.Node) nodes.next();
            HashMap<String, String> error = new HashMap();
            error.put("property", node.getName());
            error.put("msg", viol.getMessage());
            return error;
        }
        return null;
        //myColl.setStartAndStopDates(strStartDate, strStopDate);
    }

    /*
     * 提供前端字串型態日期轉換日期型態
     */
    //@MyDateConstraint(validationAppliesTo = ConstraintTarget.PARAMETERS)
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

    /*
     * handle the tech names from front end
     */
    public List<Tech> str2List(String strTeches, MyCollection mC) {
        List<String> lsTech = Arrays.asList(strTeches.split(","));
        Iterator<String> iterTech = lsTech.iterator();

        List<Tech> teches = new ArrayList<Tech>();

        while(iterTech.hasNext()) {
            teches.add(new Tech(mC, iterTech.next()));
        }
        return teches;
    }

    /*
     * ToDo
     */
    public final static String listEntityName2Str(ArrayList<Tech> lsEntity) {
        return lsEntity == null ?
                "" : lsEntity.stream()
                        .map((n) -> String.valueOf(n.getName()))
                        .collect(Collectors.joining(","));
    }
}
