package com.vandream;


import com.vandream.mall.business.service.CityListService;
import com.vandream.mall.business.vo.CityListVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/5 21:40
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CityListTests {

    @Autowired
    private CityListService cityListService;

    @Test
    public void cityList(){
        try {
            List<CityListVO> cityEntity = cityListService.getCityListService();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void provinceList(){
//        String id="";
//        List<ProvinceEntity> pro = cityListController.getCityList(id);
//        System.out.println(pro);pro
    }

}
