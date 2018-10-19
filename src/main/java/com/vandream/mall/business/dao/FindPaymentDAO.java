package com.vandream.mall.business.dao;

import com.vandream.mall.business.vo.buyerContract.FindPaymentHistoryVO;
import com.vandream.mall.business.vo.buyerContract.FindPaymentVO;
import com.vandream.mall.business.vo.buyerContract.findContract.FindContractsVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/4/4 9:42
 */
@Component
public interface FindPaymentDAO {
    /**
     *
     * @param userId
     * @param contractId
     * @param businessType
     * @return
     */
    List<FindPaymentVO> findPaymentHistory(@Param("userId") String userId, @Param("contractId") String contractId,@Param("businessType")  String businessType);

    /**
     *
     */
    FindPaymentHistoryVO findPaymentContract(@Param("contractId") String contractId);

}
