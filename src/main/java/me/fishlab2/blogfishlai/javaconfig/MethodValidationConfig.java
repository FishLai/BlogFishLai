package me.fishlab2.blogfishlai.javaconfig;

import me.fishlab2.blogfishlai.exhibition.service.impl.MyCollectionServiceImpl;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Map;

@Configuration
public class MethodValidationConfig {
    //Todo clean if no used
    //@Autowired implements HibernatePropertiesCustomizer
    //private ValidatorFactory validatorFactory;

    //public void customize(Map<String, Object> hibernateProperties) {
/*
        if (validatorFactory != null) {
            hibernateProperties.put("javax.persistence.validation.factory", validatorFactory);
        }
*/

//    }
    /*
     * 使 spring 自動對函數較驗
     */

    /*@Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }*/
    /*
    @Bean
    public Validator validatorFactory () {
        return new LocalValidatorFactoryBean();
    }

    /*@Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                // 快速失敗模式
                .failFast(true)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }*/

}