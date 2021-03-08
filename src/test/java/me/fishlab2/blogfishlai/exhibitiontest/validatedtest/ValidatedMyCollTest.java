package me.fishlab2.blogfishlai.exhibitiontest.validatedtest;

import me.fishlab2.blogfishlai.exhibition.entity.MyCollection;
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
import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ValidatedMyCollTest {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private Validator validator = validatorFactory.getValidator();

    @Test
    public void validateTest() {
        MyCollection myColl =
                MyCollection.builder()
                    .id(10)
                    .name("test2")
                    .strStartDate("2020-10")
                        .strStopDate("2019-10")
                    .collAbs("this is test")
                .build();

        Set<ConstraintViolation<MyCollection>> violations = validator.validate(myColl);

        for(ConstraintViolation<MyCollection> viol : violations) {
            logger.error(viol.getMessage());
        }
    }
}
