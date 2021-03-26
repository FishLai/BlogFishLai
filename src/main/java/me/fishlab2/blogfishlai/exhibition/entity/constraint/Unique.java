package me.fishlab2.blogfishlai.exhibition.entity.constraint;

import me.fishlab2.blogfishlai.exhibition.entity.MyCollection;
import me.fishlab2.blogfishlai.exhibition.repository.MyCollectionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=Unique.UniqueValidator.class)
public @interface Unique {
    String message() default "{me.fishlab2.blogfishlai.exhibition.entity.constraint.Unique.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class UniqueValidator implements ConstraintValidator<Unique, MyCollection> {
        private Logger logger = LogManager.getLogger(this.getClass());

        private EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(
                "me.fishlab2.blogfishlai.mycollection_catalog");
        private EntityManager em = emFactory.createEntityManager();

        @Override
        public void initialize(Unique constraintAnnotation) {
        }

        @Override
        public boolean isValid(MyCollection mC, ConstraintValidatorContext constraintValidatorContext) {
            if(mC.getName() == null) return false;

            /*
             * 被使用過則回傳未通過較驗訊息
             */
            boolean isUnique = !isUsed(mC.getId(), mC.getName());
            if(!isUnique) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(
                        "{me.fishlab2.blogfishlai.exhibition.entity.constraint.Unique.message}" +
                                "作品名稱重複"
                ).addPropertyNode("name").addConstraintViolation();
            }
            return isUnique;
        }

        /*
         * 回傳名稱是否被用過
         */
        private boolean isUsed(long id, String s) {
            String collName = s.replaceAll("[\\h\\s\\v\\p{Punct}]", "");
            Session session = this.em.unwrap(Session.class);

            DetachedCriteria criteria = DetachedCriteria
                    .forClass(MyCollection.class)
                    .setProjection(Projections.rowCount())
                    .add(Restrictions.conjunction()
                            .add(Restrictions.sqlRestriction(
                                "REGEXP_REPLACE({alias}.coll_name, '[[:space:][:punct:]]', '') = (?)",
                                collName,
                                StringType.INSTANCE))
                            .add(Restrictions.ne("id", id))
                    );
            Number count = (Number) criteria.getExecutableCriteria(session)
                    .list()
                    .iterator()
                    .next();

            //em.close();


            //Todo 待釐清無法再同一 session 驗證+儲存會丟出null id...，
            // 估計是transaction 觀念問題

            return count.intValue() > 0;
        }
    }
}
