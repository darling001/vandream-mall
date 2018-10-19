package com.vandream.mall.business.dto.delivery;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Li Jie
 */
@Data
@Getter
@Setter
public class DeliveryReturnDTO {
    private String deliveryReturnId;

    private String deliveryReturntCode;

    private String deliveryLineId;

    private String deliveryHeadId;

    private String returnType;

    private String returnReason;

    private String returnRemark;

    private BigDecimal returnQuantity;

    private String returnStatus;

    private String createUserId;

    private String createUserName;

    private Date createDate;

    private String modifyUserId;

    private String modifyUserName;

    private Date modifyDate;

    private String orgId;

    private String groupId;

    private String bookId;

    private String extCol1;

    private String extCol2;

    private String extCol3;

    private String extCol4;

    private String extCol5;

    private String extCol6;

    private String extCol7;

    private String extCol8;

    private String extCol9;

    private String extCol10;

    private String extCol11;

    private String extCol12;

    private String extCol13;

    private String extCol14;

    private String extCol15;
}