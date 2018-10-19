package com.vandream;

import com.vandream.mall.business.execption.AddOrderException;
import com.vandream.mall.business.service.OrderService;

import com.vandream.mall.business.vo.order.BatchOrderVO;
import com.vandream.mall.commons.utils.JSONUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/11 15:19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OrderAddTest {

    @Autowired
    private OrderService orderService;
    @Test
    public void addOrder(){

        try {
            String userId ="3cf7be5d-2d6a-41c1-a98b-18a67c95";
            String itemId ="a0ef6aa8aed9cbcede931ba9491fb6d5";
            String supplierId="12444" ;
            BigDecimal number = new BigDecimal(3) ;
            String address = "美国";
            Long leadTime = 1520753228000L ;
//            orderService.addOrder(userId,itemId,
//                    supplierId,number,address,leadTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void addBatchOrder(){
        String json="{\"countryCode\":\"CN\",\"countryName\":\"中国\",\"userId\":\"0de11d948721bd1bffe3a782926fea1c\"," +
                "\"userName\":\"13758154380\",\"customerCode\":\"WJLJ021000074\",\"customerName\":\"糜炯测试需方\"," +
                "\"leadTime\":1524240000000,\"provinceName\":\"河北省\",\"cityName\":\"秦皇岛市\",\"areaName\":\"海港区\"," +
                "\"provinceCode\":2,\"cityCode\":2,\"areaId\":2,\"address\":\"河北省秦皇岛市海港区sdfsdfsd\"," +
                "\"postCode\":\"310000\",\"contactName\":\"sdfsd\",\"contactPhone\":\"13312311234\"," +
                "\"customerId\":\"20c1dfa73c497d3398b03fa48df44f43\",\"companyId\":\"a5ef432df6a47d3bd01a72724df92047\"}";
        BatchOrderVO batchOrderVO= JSONUtil.toBean(json,BatchOrderVO.class);
        try {
            orderService.addBatchOrder(batchOrderVO);
        } catch (AddOrderException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testBigDecimal(){
        BigDecimal test1=new BigDecimal(-1);
        System.out.println("===========test1 = " + test1);
    }
}
