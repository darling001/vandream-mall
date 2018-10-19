package com.vandream.mall.business.vo.buyerContract;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;

import java.util.List;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/4/3 20:16
 */
@Data
public class DeliveryInfoVO extends BaseVO{
    private String deliveryId;
    private String deliveryCode;
    private String deliveryNoticeCode;
    private Long deliveryDate;
    private Long deliveryNoticeDate;
    private Long arrivalDate;
    private String contractCode;
    private String status;
    private String address;
    private String deliveryName;
    private String deliveryPhone;
    private String remark;
    private String receiptInfo;
    private Long receiptDateTime;
    private String attachmentName;
    private String attachmentPath;
    private String logisticsName;
    private String contactName;
    private String contactTel;
    private String logisticsNumber;
    private String carNumber;
    private String driverName;
    private String driverTel;

    private List<FindLogisticsVO> logisticsList;
    private List<InfoItemListVO> itemList;
}
