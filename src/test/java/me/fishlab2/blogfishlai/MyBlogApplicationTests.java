package me.fishlab2.blogfishlai;

import me.fishlab2.blogfishlai.storage.MvcConfig;
import org.apache.catalina.core.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import javax.annotation.Resources;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest
class MyBlogApplicationTests {
	private Logger logger = LogManager.getLogger(this.getClass());
	@Test
	void contextLoads() {
		Path path = Paths.get("/springMVCDev/temp/files");
		logger.info(path.resolve("test").normalize());
	}

	/*
	 *　確認 String.replaceAll()
	 */
	@Test
	public void stringReplaceTest() {
		String s = "test the blank and symbol replace with '_'";
		String s2 = s.replaceAll("[\\W_]", "_");
		logger.info(s2);
		logger.info(new String((s2 + "/" + "filename")));
	}
}
