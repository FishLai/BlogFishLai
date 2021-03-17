package me.fishlab2.blogfishlai.exhibitiontest.validatedtest;

import me.fishlab2.blogfishlai.exhibition.entity.MyCollection;
import me.fishlab2.blogfishlai.exhibition.repository.MyCollectionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ValidatedMyCollTest {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private MyCollectionRepository myCollectionRepository;


    @Autowired
    private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private Validator validator = validatorFactory.getValidator();


    @Test
    public void validateTest() {
        ExecutableValidator exeValidator = validator.forExecutables();

        /*
         * 測試 abstract 給值 ""、" "、"  "、"this is a validator test"
         */
        MyCollection myColl = new MyCollection();
        myColl.setName("test 2");
        myColl.setCollAbs("this is a validator test");


        myColl.setStartAndStopDates("1994-01", "1994-01");
        try {
            Method method = MyCollection.class.getMethod("setStartAndStopDates", String.class, String.class);
            /*
             * V測試 start 給 ""、1992、199、3994、1994-01
             * V 測試 stop 給 1993、1993-01、""、2333-01、1994-01
             */
            Object[] params = { "", "1994-01" };
            Set<ConstraintViolation<MyCollection>> paraValidations = exeValidator.validateParameters(myColl, method, params);

            Iterator<ConstraintViolation<MyCollection>> iterator = paraValidations.iterator();
            while(iterator.hasNext()){
                logger.error(iterator.next().getMessage());
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


        Set<ConstraintViolation<MyCollection>> violations = validator.validate(myColl);

        for(ConstraintViolation<MyCollection> viol : violations) {
            logger.error(viol.getMessage());
        }

    }
 /*
    @Test
    public void validNameTest() {
        MyCollection myColl =
                MyCollection.builder()
                    .name("test validator")
                .build();
        Set<ConstraintViolation<MyCollection>> viols = validator.validate(myColl);
        for(ConstraintViolation<MyCollection> viol: viols) {
            logger.error(viol.getMessage());
        }
    }

 */
}
