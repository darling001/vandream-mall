package com.vandream.mall.business.dao;


import com.vandream.mall.business.vo.FavoritesVO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/5 11:20
 */
@Component
public interface FavoritesDAO {
    /**
     *
     * @param userId 用户id
     * @param skuId  skuId
     */
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    void  addFavorites(@Param("userId") String userId,
                       @Param("itemId") String skuId,
                       @Param("itemLineId") String itemLineId,
                       @Param("spuId") String spuId,
                       @Param("status") Byte status,
                       @Param("count") BigDecimal count);

    /**
     *
     * @param id
     * @param userId
     */
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    void removeFavorites(@Param("id") Integer id,
                         @Param("userId") String userId);
    /**
     *
     * @param UserId
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    List<FavoritesVO> selectSkuId(@Param("UserId") String UserId);
    /**
     *
     * @param itemId
     * @param areaCode
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_PRODUCT)
    String

    selectItemLineId(@Param("itemId") String itemId, @Param("areaCode") String areaCode);
    /**
     *
     * @param userId
     * @param itemLineId
     * @param isAdd
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    int updateFavoritesStatus(String userId, String itemLineId, Byte isAdd);
    /**
     *
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
   BigDecimal selectFavorites(@Param("itemLineId") String itemLineId,@Param("userId") String userId);
    /**
     *
     */
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    void addFavoritesCount(@Param("count") BigDecimal number,
                           @Param("itemLineId") String itemLineId);

    /**
     *
     * @param itemLineId
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_PRODUCT)
    BigDecimal selectItemLineMinOrder(@Param("itemLineId") String itemLineId);

    /**
     *
     * @param userId
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    int selectFavoritesCountByUserId(@Param("userId") String userId);

}
