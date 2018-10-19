package com.vandream.mall.business.service;


import com.vandream.mall.business.dto.item.ItemDetailDTO;
import com.vandream.mall.business.dto.item.ItemInfoDTO;
import com.vandream.mall.business.dto.mallCart.GoodsParams;
import com.vandream.mall.business.execption.ItemDetailException;

import java.util.List;

/**
 * @author liuyuhong
 * @date 2018/3/6
 * @time 13:27
 * @description
 */
public interface ItemDetailService {
    /**
     *
     * @param userId
     * @param areaCode
     * @param itemId
     * @return
     */
    ItemInfoDTO getItemBaseInfo(String userId,String itemId,String areaCode) throws ItemDetailException;

    /**
     *
     * @param itemId
     * @return
     */
    List<GoodsParams> getSkuAttributeList(String itemId) throws ItemDetailException;

    /**
     *
     * @param itemId
     * @return
     */
    ItemDetailDTO getItemInfoDetail(String itemId) throws ItemDetailException;
}
