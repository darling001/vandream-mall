package com.vandream.mall.business.dao;

import com.vandream.mall.business.vo.buyerContract.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Create by shkstart on 2018/4/4.
 */
@Component
public interface ContractInfoDAO {
    List<ContractInfoitemListVO> getContractInfoItem(@Param("comtractId") String comtractId);

    List<AttachmentListVO> getAttachment(@Param("comtractId") String comtractId);

    List<BillingInformationVO> getBillingInformation(@Param("comtractId") String comtractId);

    List<AttachmentListVO> getContractList(@Param("comtractId") String comtractId);

    BigDecimal getTotalAmount(@Param("comtractId") String comtractId);

    ContractInfoVO getContractInfo(@Param("userId") String userId,@Param("comtractId") String comtractId);

    ContractInfoVO getContractIdentifyPeople(@Param("comtractId") String comtractId);

    String getContractCodeById(@Param("contractId") String contractId);
}
