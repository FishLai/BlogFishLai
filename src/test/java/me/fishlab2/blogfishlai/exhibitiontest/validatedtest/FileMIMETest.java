package me.fishlab2.blogfishlai.exhibitiontest.validatedtest;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.net.URLConnection;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class FileMIMETest {

    @SneakyThrows
    @Test
    public void imgMIMETest() {
        File f = new File("C:/workspace/springMVCDev/files/temp/linkedin-article-cover.JPG");
        String mimeType = URLConnection.guessContentTypeFromName(f.getName());

        assertEquals(mimeType, "image/jpeg");

    }
}
