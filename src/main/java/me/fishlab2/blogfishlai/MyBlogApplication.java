package me.fishlab2.blogfishlai;

import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManagerFactory;
import java.util.Date;

//@EnableConfigurationProperties
@SpringBootApplication
public class MyBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBlogApplication.class, args);
	}
//Todo clean
/*
 * 設定 hibernate session 提供建立與資料庫的連線
	@Bean
	public SessionFactory sessionFactory(EntityManagerFactory emf) {
		if (emf.unwrap(SessionFactory.class) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		return emf.unwrap(SessionFactory.class);
	}

 */
}
