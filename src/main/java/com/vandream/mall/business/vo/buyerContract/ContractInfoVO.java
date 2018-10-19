package com.vandream.mall.business.vo.buyerContract;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/4/3 20:41
 */
@Data
public class ContractInfoVO extends BaseVO{
    private String contractName;
    private String contractCode;
    private Long contractDate;
    private String purchaser;
    private String supplier;
    private String projectName;
    private String address;
    private String name;
    private String phone;
    private String status;
    private List<ContractInfoitemListVO> itemList;
    private List<AttachmentListVO> attachmentList;
    private List<BillingInformationVO> billingInformation;
    private BigDecimal totalAmount;
    private String companyName;
    private String identificationCode;
    private String companyAddress;
    private String tel;
    private String bankName;
    private String cardNumber;
    private List<AttachmentListVO> contractList;
    private String confirmer;
    private Long confirmDateTime;
    private String firstParty;
    private String secondParty;
    private Long takeEffectDateTime;
    private String contractRemarks;

}
