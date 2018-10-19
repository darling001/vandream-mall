package com.vandream;


import com.vandream.mall.business.execption.AddOrderException;
import com.vandream.mall.business.execption.FavoritesException;
import com.vandream.mall.business.execption.FindContractException;
import com.vandream.mall.business.service.FavoritesListByUserIdService;
import com.vandream.mall.business.service.FavoritesService;
import com.vandream.mall.commons.utils.JSONUtil;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/5 15:50
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FavoritesTests {

    @Autowired
    private FavoritesService favoritesService;

    @Autowired
    private FavoritesListByUserIdService favoritesListByUserIdConteroller;


    @Autowired
    private TransportClient transportClient;

    @Test
    public void addFavorites() {
       String userId = "3cf7be5d-2d6a-41c1-a98b-18a63";
       String itemId = "3ee06dbebc60d07de7a55623a858f352";
       String areaCode = "";
       BigDecimal count = new BigDecimal(11);
        try {
            try {
                favoritesService.addFavorites(userId,itemId,areaCode,count);
            } catch (FindContractException e) {
                e.printStackTrace();
            }
            //favoritesService.addFavorites(userId,itemId,supplierId,count);
        } catch (FavoritesException e) {
            e.printStackTrace();
        } catch (AddOrderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeFavorites(){
        String userId="3cf7be5d-2d6a-41c1-a98b-18a67c95";
        Integer id=1;
        favoritesService.removeFavorites(id,userId);
    }


    @Test
    public void itemIdFavorites() {
        String userId = "3cf7be5d-2d6a-41c1-a98b-18a67c95";
        try {
            favoritesListByUserIdConteroller.getFavoritesListByUserId(userId);
        } catch (FavoritesException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void order(){
        String itemId = "a0ef6aa8aed9cbcede931ba9491fb6d5";
        GetRequestBuilder getRequestBuilder = transportClient.prepareGet("gms", "item_agg", itemId);
        GetResponse getResponse = getRequestBuilder.execute().actionGet();
        Map<String, Object> map = getResponse.getSource();
        String spec_contentsJson = JSONUtil.toJson(map.get("PRIMARY_UNIT_CODE"));
        System.out.println(spec_contentsJson);


    }



}
