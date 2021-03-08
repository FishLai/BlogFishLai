package me.fishlab2.blogfishlai.exhibition.entity.constraint;

import me.fishlab2.blogfishlai.exhibition.service.impl.MyCollectionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=Unique.UniqueValidator.class)
public @interface Unique {
    String message() default "{me.fishlab2.blogfishlai.exhibition.entity.constraint.Unique.message}";
    Class<?>[] group() default {};
    Class<? extends Payload>[] payload() default {};

    public class UniqueValidator implements ConstraintValidator<Unique, String> {
        private final MyCollectionServiceImpl myCollService = new MyCollectionServiceImpl();

        @Override
        public void initialize(Unique constraintAnnotation) {

        }

        @Override
        public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
            return myCollService.isUsedName(s);
        }
    }
}
