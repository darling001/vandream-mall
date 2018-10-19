package com.vandream.mall.business.dao;

import com.vandream.mall.business.vo.LogisticsVO;
import com.vandream.mall.business.vo.buyerContract.FindLogisticsListVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/29 16:29
 */
@Component
public interface FindLogisticsDAO {
    /**
     * @param userId
     * @param deliverInfold
     * @return
     */
    FindLogisticsListVO findLogisticsList(@Param("userId") String userId,
                                          @Param("deliverInfold") String deliverInfold);
    List<LogisticsVO> findLongisticsListByDeliveryId(@Param("deliveryHeadId")String deliveryHeadId);
}
