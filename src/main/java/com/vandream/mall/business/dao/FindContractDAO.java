package com.vandream.mall.business.dao;

import com.vandream.mall.business.vo.buyerContract.ContractListVO;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/5 11:20
 */
@Component
public interface FindContractDAO {
    /**
     *
     * @param companyId
     * @return
     */
     List<ContractListVO> findContractList(@Param("userId") String userId,
                                           @Param("companyId") String companyId,
                                           @Param("keyword") String keyword,
                                           @Param("contractStartDate") String contractStartDate,
                                           @Param("contractEndDate") String contractEndDate,
                                           @Param("contractStatus") String contractStatus,
                                           @Param("paymentStatus") String paymentStatus,
                                           @Param("deliveryStatus") String deliveryStatus,
                                           @Param("pageNumber") int pageNumber,
                                           @Param("pageSize") int pageSize);


    int totalSize(@Param("userId") String userId,
                  @Param("companyId") String companyId,
                  @Param("keyword") String keyword,
                  @Param("contractStartDate") String contractStartDate,
                  @Param("contractEndDate") String contractEndDate,
                  @Param("contractStatus") String contractStatus,
                  @Param("paymentStatus") String paymentStatus,
                  @Param("deliveryStatus") String deliveryStatus);


}
