package com.vandream.mall.business.vo.buyerContract;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/4/3 20:05
 */
@Data
@Getter
@Setter
public class DeliveryNoticeInfoVO {
    private String deliveryNoticeId;
    private String deliveryNoticeCode;
    private Long deliverNoticeDate;
    private Long expectDate;
    private String contractId;
    private String contractCode;
    private String customerName;
    private String projectName;
    private String address;
    private String sales;
    private String contactTel;
    private List<NoticeItemListVO> itemList;

    @Override
    public String toString() {
        return "DeliveryNoticeInfoVO{" +
                "deliveryNoticeId='" + deliveryNoticeId + '\'' +
                ", deliveryNoticeCode='" + deliveryNoticeCode + '\'' +
                ", deliverNoticeDate='" + deliverNoticeDate + '\'' +
                ", expectDate='" + expectDate + '\'' +
                ", contractId='" + contractId + '\'' +
                ", contractCode='" + contractCode + '\'' +
                ", customerName='" + customerName + '\'' +
                ", projectName='" + projectName + '\'' +
                ", address='" + address + '\'' +
                ", sales='" + sales + '\'' +
                ", contactTel='" + contactTel + '\'' +
                ", itemList=" + itemList +
                '}';
    }
}
