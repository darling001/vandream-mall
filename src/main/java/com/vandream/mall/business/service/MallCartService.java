package com.vandream.mall.business.service;


import com.vandream.mall.business.dto.mallCart.CartResponseDTO;
import com.vandream.mall.business.execption.MallCartException;

import java.math.BigDecimal;

/**
 * @author dingjie
 * @date 2018/3/5
 * @time 10:46
 * Description:
 */
public interface MallCartService {
    /**
     * 商品加入购物车
     * @param itemNo
     * @param item_line_id
     * @param userId
     * @return
     */
     int addCart(String userId, String skuId, String item_line_id, BigDecimal itemNo) throws MallCartException;

    /**
     * 从购物车移除 商品
     * @param id
     * @param userId
     * @return
     */
     int removeCart(long id, String userId)throws MallCartException;

    /**
     * 根据userId获取购物车列表
     * @param userId
     * @return
     */
     CartResponseDTO getCartList(String userId,String advanceOrderId)throws MallCartException;

    /**
     * 修改购物车内 商品数量
     * @param id
     * @param itemNo
     * @return
     */
     int modifyCart(long id, String userId, BigDecimal itemNo)throws MallCartException;

    /**
     * 查询加入购物车的有效商品数量
     * @param userId
     * @return
     */
    int selectCartCountByUserId(String userId)throws MallCartException;
}
