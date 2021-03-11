package me.fishlab2.blogfishlai.exhibition.entity.constraint;

import me.fishlab2.blogfishlai.exhibition.repository.MyCollectionRepository;
import me.fishlab2.blogfishlai.exhibition.service.impl.MyCollectionServiceImpl;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import javax.validation.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=Unique.UniqueValidator.class)
public @interface Unique {
    String message() default "{me.fishlab2.blogfishlai.exhibition.entity.constraint.Unique.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class UniqueValidator implements ConstraintValidator<Unique, String> {

        @Autowired
        private MyCollectionServiceImpl myCollectionServiceImpl;

        @Override
        public void initialize(Unique constraintAnnotation) {

        }

        @Override
        public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
            boolean isUnique = !(myCollectionServiceImpl.isUsed(s));
            if(!isUnique) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(
                        "{me.fishlab2.blogfishlai.exhibition.entity.constraint.Unique.message}" +
                                "作品名稱重複"
                ).addConstraintViolation();
            }
            return isUnique;
        }
    }
}
