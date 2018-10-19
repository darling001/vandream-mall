package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.order.OrderDetailDTO;
import com.vandream.mall.business.dto.order.OrderItemDTO;
import com.vandream.mall.business.vo.order.ItemMatchAggVO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/6 17:41
 */
@Component
public interface OrderDAO {
    /**
     *
     * @param orderNo
     * @param address
     * @param leadTime
     * @param userId
     * @param userName
     */
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    void insertOrder(@Param("orderNo") Long orderNo,
                         @Param("address") String address,
                         @Param("leadTime") Long leadTime,
                         @Param("userId") String userId,
                         @Param("userName") String userName);

    /**
     *
     * @param
     * @param orderNo
     * @param itemId
     * @param itemLineId
     * @param itemUnit
     * @param itemPrice
     * @param number
     */
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    void insertOrderItem(@Param("orderNo") Long orderNo,
                             @Param("itemId") String itemId,
                             @Param("itemLineId") String itemLineId,
                             @Param("itemUnit") String itemUnit,
                             @Param("itemPrice") BigDecimal itemPrice,
                             @Param("number") BigDecimal number);

    /**
     *
     * @param userId
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
    String selectByIdUserName(@Param("userId") String userId);

    /**
     *
     * @param userId
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    OrderDetailDTO addBatchOrder(String userId);

    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    int batchInsertOrderList(List<OrderDetailDTO> orderEntityList);

    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    int batchInsertOrderItemList(List<OrderItemDTO> itemEntityList);
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    Long selectMallOrderByOrderNo(Long orderNo);

    /**
     * 通过orderId查询商品预订单详情集合
     * @param orderId
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    List<String> getOrderItemDTOListByOrderId(String orderId);
    
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    Set<String> findAreaRangeCode(@Param("cityCode")  String cityCode);
    
    @DataSourceTarget(DataSourceKey.DATABASE_PRODUCT)
    List<ItemMatchAggVO>  findItemMatchInfo(@Param("itemIdList") List<String> itemIdList,@Param("arangeCodeSet") Set<String> arangeCodeSet);
    
    @DataSourceTarget(DataSourceKey.DATABASE_PRODUCT)
    List<ItemMatchAggVO>  findUnItemMatchInfo(@Param("itemIdList") List<String> itemIdList);
}
