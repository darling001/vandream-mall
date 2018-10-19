package com.vandream;

import com.alibaba.fastjson.JSON;
import com.vandream.mall.business.service.BrandService;
import com.vandream.mall.business.vo.base.DataListVO;
import com.vandream.mall.business.vo.homepage.BrandDetailVO;
import com.vandream.mall.business.vo.homepage.BrandListVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/4/10
 * @time : 19:28
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BrandServiceTests {


    @Resource
    private BrandService brandService;

    @Test
    public void findBrandList() throws InvocationTargetException {
        DataListVO<BrandListVO> brandList = brandService.findBrandList(0, 0);
        System.out.println(JSON.toJSONString(brandList));
    }

    @Test
    public void getBrandDetailById() throws InvocationTargetException {
        BrandDetailVO brandDetail = brandService.getBrandDetailById("IMPL0000000000201805220350207678");
        System.out.println(JSON.toJSONString(brandDetail));
    }
}


