package com.vandream;

import com.alibaba.fastjson.JSON;
import com.vandream.mall.business.dao.homepage.AdvertisementDAO;
import com.vandream.mall.business.dto.homepage.AdvertisementDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/5
 * @time : 15:45
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AdvertisementDAOTests {

    @Autowired
    private AdvertisementDAO advertisementDAO;

    @Test
    public void getAdvertisementList(){
        String sectionId="123";
        List<AdvertisementDTO> advertisementList = advertisementDAO.getAdvertisementList(sectionId);
        System.out.println(JSON.toJSONString(advertisementList));
        /*for (AdvertisementEntity advertisementEntity:advertisementList){
            System.out.println(advertisementEntity.toString());
        }*/

    }
}
