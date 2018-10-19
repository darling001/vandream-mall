package com.vandream.mall.business.vo.buyerContract;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/28 19:42
 */
@Data
public class ContractListVO extends BaseVO {
    private String contractId;
    private String contractCode;
    private String contractName;
    private String projectName;
    private Long contractDate;
    private BigDecimal contractAmount;
    private BigDecimal paidAmount;
    private Long deliveryStartDate;
    private Long deliveryEndDate;
    private String contractStatus;
    private String paymentStatus;
    private String deliveryStatus;
    /**
     * 发货通知单状态值
     */
    private String noticeStatus;
    private String deliveryNoticeIsRead;
    private String deliveryInfoIsRead;
    private String itemVersion;
    private Boolean  isChanged;
    private String fromType;
}
