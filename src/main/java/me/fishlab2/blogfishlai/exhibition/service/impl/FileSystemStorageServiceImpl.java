package me.fishlab2.blogfishlai.exhibition.service.impl;

import me.fishlab2.blogfishlai.exhibition.exception.StorageException;
import me.fishlab2.blogfishlai.exhibition.service.StorageService;
import me.fishlab2.blogfishlai.storage.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class FileSystemStorageServiceImpl implements StorageService {
    private final Path rootLoc;
    private Path distinationPath, distinationFile;

    @Autowired
    public FileSystemStorageServiceImpl(StorageProperties storageProperties) {
        this.rootLoc = Paths.get(storageProperties.getLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(distinationPath.toAbsolutePath().normalize());
        } catch (IOException e) {
            throw new StorageException("Couldn't initialize storage", e);
        }
    }

    private void init(Path directory) {
        try {
            Files.createDirectories(directory.toAbsolutePath().normalize());
        } catch (IOException e) {
            throw new StorageException("Couldn't create folder");
        }
    }


    @Override
    public void store(MultipartFile file) {
        try {
            if(file.isEmpty()){
                throw new StorageException("Failed to store empty file");
            }
            if (!constraintFileType(file)) throw new StorageException("只支援 image/jpeg 格式喔");

            Path destinationFile = this.distinationFile =  this.distinationPath.resolve(
                    Paths.get(file.getOriginalFilename())
            ).toAbsolutePath().normalize();
            if(!destinationFile.getParent().equals(this.distinationPath.toAbsolutePath().normalize())) {
                throw new StorageException("Cannot store file outside current directory");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, REPLACE_EXISTING);
            }

        } catch (IOException e) {

            throw new StorageException("Failed to store file", e);
        }
    }

    private boolean constraintFileType(MultipartFile file) {
        boolean isJPEG = false;
        String type = file.getContentType();
        if(type.equals("image/jpeg")) isJPEG = true;
        return isJPEG;
    }

    @Override
    public Resource loadAsResource(String filename) {
        return null;
    }

    @Override
    public HashMap<String, String> store(MultipartFile file, String pathPattern){
        this.distinationPath = this.rootLoc.resolve(pathPattern);
        HashMap<String, String> res = new HashMap<String, String>();

        try {
            store(file);

            res.put("status", "true");
            res.put("message", "檔案上傳成功");
            return res;
        } catch (StorageException e) {
            try {
                Files.delete(this.distinationFile);
                this.distinationFile = null;
            } catch (IOException ioException) {
                /*
                 * 想不到發生的理由
                 */
                ioException.printStackTrace();
            }

            res.put("status", "false");
            res.put("message", "檔案上傳失敗，僅支援 image/jpeg");
            return res;
        }
    }

    /*
     * do persist file
     */
    @Override
    public String persistFile(String name) {
        /*
         * 先去除奇怪的符號與空白成"_"
         * 以便創建新資料夾永久存存檔案
         */
        String fn = name.replaceAll("[\\W_]", "_");

        Path fnPath = this.rootLoc.resolve(fn);
        init(fnPath); //try create folder

        try {
            /*
             * distinationFile 會是tmp 圖檔路徑
             */
            Files.move(this.distinationFile,
                    fnPath.resolve(this.distinationFile.getFileName()).toAbsolutePath(),
                    StandardCopyOption.REPLACE_EXISTING);

            /*
             * 返回相對路徑給資料庫儲存
             */
            return ("files/" + fn + "/" + this.distinationFile.getFileName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Path getDistinationFile() { return this.distinationFile; }
}
