package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.SalesReservationDTO;
import com.vandream.mall.business.dto.SalesReservationInfoDTO;
import com.vandream.mall.business.dto.SalesReservationRequestDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/3/28
 * Time: 19:28
 * Description: 锁货通知详情
 */
@Component
public interface SalesReservationDAO {

    /**
     * 获取单条锁货详情
     *
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    SalesReservationDTO getSalesReservationInfo(SalesReservationInfoDTO salesReservationInfoDTO);

    /**
     * 查询供方锁货列表
     *
     * @param salesReservationRequestDTO
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<SalesReservationDTO> findSalesReservationList(SalesReservationRequestDTO salesReservationRequestDTO);


}
