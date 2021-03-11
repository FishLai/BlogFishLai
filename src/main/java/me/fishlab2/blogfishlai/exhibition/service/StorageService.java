package me.fishlab2.blogfishlai.exhibition.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface StorageService {
    void init();

    void store(MultipartFile file);

    Resource loadAsResource(String filename);

    HashMap<String, String> store(MultipartFile file, String Pathpattern);

    /*
     * return file relative path
     */
    String persistFile(String name);
}
