package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.mallCart.MallCartDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author dingjie
 * @date 2018/3/5
 * @time 11:01
 * Description:
 */
@Component
public interface MallCartDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    int addCart(MallCartDTO mallCartDTO);
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    int removeCart(MallCartDTO mallCartDTO);
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    List<MallCartDTO> getCartList(Map paramsMap);
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    int modifyCart(MallCartDTO mallCartDTO);
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    MallCartDTO selectCartById(MallCartDTO mallCartDTO);
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    int updateCartStatus(Map paramsMap);
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    int updateMallCartEntity(MallCartDTO mallCartDTO);
    /**
     *查询购物车是否已存在当前no,id
     */
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    MallCartDTO selectMallCartByItemLineId(@Param("userId")String userId,
                                   @Param("item_line_id") String item_line_id);

    /**
     * 查询购物车商品信息
     */
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    List<MallCartDTO> selectMallCartListByItemLineIds(@Param("userId")String userId,
                                                      @Param("itemLineIds") List<String> itemLineIds);
    /**
     * 购物车生成预订单商品 状态更新
     */
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    int updateItemLineBatch(Map map);

    /**
     * 查询加入购物车的有效商品数量
     */
    int selectCartCountByUserId(String userId);
}
