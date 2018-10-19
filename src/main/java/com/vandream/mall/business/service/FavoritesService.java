package com.vandream.mall.business.service;



import com.vandream.mall.business.execption.AddOrderException;
import com.vandream.mall.business.execption.FavoritesException;
import com.vandream.mall.business.execption.FindContractException;

import java.math.BigDecimal;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/5 10:59
 */

public interface FavoritesService {
    /**
     *
     * @param userId
     * @param itemId
     * @param areaCode
     */
    int addFavorites(String userId, String itemId, String areaCode, BigDecimal count) throws FavoritesException, AddOrderException, FindContractException;
    /**
     * @param id
     * @param userId 用户
     */
    void removeFavorites(Integer id, String userId);
    /**
     *
     */
    int getFavoritesCountByUserId(String userId);

}
