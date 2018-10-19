package com.vandream;

import com.vandream.mall.business.execption.FavoritesException;
import com.vandream.mall.business.service.FavoritesListByUserIdService;
import com.vandream.mall.business.vo.FavorityItemDataVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/9 14:27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FavoritesById {

    @Autowired
    private FavoritesListByUserIdService favoritesListService;
    @Test
    public void getFavoriteById(){
        String userId = "3cf7be5d-2d6a-41c1-a98b-18a67c95";
        List<FavorityItemDataVO> favorityItemData= null;
        try {
            favorityItemData = favoritesListService.getFavoritesListByUserId(userId);
        } catch (FavoritesException e) {
            e.printStackTrace();
        }
        System.out.println(favorityItemData);

    }
}
