package com.vandream;

import com.vandream.mall.commons.service.StorageService;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Li Jie
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class StorageServiceTests {
    @Autowired
    private StorageService storageService;

    @Test
    public void upload() throws Exception {
        String filePath = "C:\\Users\\Martial\\Documents\\123.xlsx";
        File file = new File(filePath);
        byte[] bytes = FileUtils.readFileToByteArray(file);

        String uploadResult = storageService.upload(bytes, "xlsx");
        System.out.println(uploadResult);
    }
}
