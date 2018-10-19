package com.vandream;

import com.google.common.collect.Maps;
import com.vandream.mall.business.vo.OrderResponseVO;
import com.vandream.mall.business.dto.mallCart.CartResponseDTO;
import com.vandream.mall.business.execption.AddOrderException;
import com.vandream.mall.business.execption.MallCartException;
import com.vandream.mall.business.service.MallCartService;
import com.vandream.mall.business.service.OrderService;
import com.vandream.mall.business.service.RedisService;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

/**
 * @author dingjie
 * @date 2018/3/5
 * @time 16:54
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MallCartTest {
    @Autowired
    private MallCartService mallCartService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private OrderService orderServicer;

    @Autowired
    private TransportClient transportClient;

    @Test
    public void addCart() {
        int count = 11;
        String Item_line_id = "c4fe8dae6c5ed72608299b64bb4f7ddf";
        String userId = "55c6f1a5cf12f16caa8e255e3d9c4e9c";
        String skuId="78a078bfcc787411dc132dfd2935e970";
        BigDecimal itemNo=new BigDecimal(140);
        try {
            mallCartService.addCart(userId,skuId,Item_line_id,itemNo);
        } catch (MallCartException e) {
            e.printStackTrace();
        }
        System.out.println("测试成功!");


    }

    @Test
    public void removeCart() {
        long id = 4;
        String user_id = "111";
        /*MallCartEntity mallCartEntity=new MallCartEntity();
        mallCartEntity.setUserId(user_id);
        mallCartEntity.setId(id);*/
        try {
            mallCartService.removeCart(id, user_id);
        } catch (MallCartException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addRedis() {
        Map<String, Object> target = Maps.newHashMap();
        target.put("userId", 01);
        target.put("cartNo", 10001);
        redisService.hset("mall.cart", "1", target);
    }

    @Test
    public void getRedis() {
        Map<String, Object> value = redisService.hget("mall.cart", "1", Map.class);
        System.out.println("value = " + value);
    }

    @Test
    public void getCartNo() {
        Long time = System.currentTimeMillis();
        System.out.println("time:" + new BigInteger(time.toString()));
    }

    @Test
    public void getCartList() {
        String userId = "16b6607c21f5f508e9de1bb12013d69a";
        CartResponseDTO mallEntity = null;
        try {
            mallEntity = mallCartService.getCartList(userId,"0");
        } catch (MallCartException e) {
            e.printStackTrace();
        }
        System.out.println("采购清单:" + mallEntity.toString());
    }

    @Test
    public void getBatchOrder(){
        String userId="3cf7be5d-2d6a-41c1-a98b-18a67c95";/*
        try {
//            OrderResponseVO orderResponseVO = orderServicer.addBatchOrder(userId);
//            System.out.println("商品信息:"+ orderResponseVO.toString());
        } catch (AddOrderException e) {
            e.printStackTrace();
        }*/
    }
}
