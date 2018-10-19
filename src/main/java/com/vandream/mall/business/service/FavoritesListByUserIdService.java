package com.vandream.mall.business.service;

import com.vandream.mall.business.execption.FavoritesException;
import com.vandream.mall.business.vo.FavorityItemDataVO;

import java.util.List;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/5 17:46
 */
public interface FavoritesListByUserIdService {
    /**
     *
     * @param userId
     * @return
     */
    List<FavorityItemDataVO> getFavoritesListByUserId(String userId) throws FavoritesException;

}
