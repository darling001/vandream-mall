package com.vandream.mall.business.service;

import com.vandream.mall.business.execption.SalesReservationException;
import com.vandream.mall.business.vo.SalesListVO;
import com.vandream.mall.business.vo.SalesReservationInfoVO;
import com.vandream.mall.business.vo.SalesReservationRequestVO;
import com.vandream.mall.business.vo.SalesReservationVO;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/3/28
 * Time: 20:11
 * Description: 锁货通知
 */
public interface SalesReservationService {

    /**
     * 查询供方锁货列表
     *
     * @param salesReservationRequestVO
     * @return 供方锁货通知列表
     */
    SalesListVO findSalesReservationList(SalesReservationRequestVO salesReservationRequestVO) throws
            SalesReservationException;

    /**
     * 获取单条锁货详情
     *
     * @param salesReservationInfoVO
     * @return 锁货信息详情页面
     * @throws SalesReservationException
     */
    SalesReservationVO getSalesReservationInfo(SalesReservationInfoVO salesReservationInfoVO) throws SalesReservationException;


}
