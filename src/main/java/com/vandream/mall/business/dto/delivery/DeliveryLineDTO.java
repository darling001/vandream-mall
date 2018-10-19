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
public class DeliveryLineDTO {
    private String deliveryLineId;

    private String deliveryLineCode;

    private String deliveryHeadId;

    private String saleContractLineId;

    private String salesContractLineId;

    private String saleContractLineCode;

    private String salesContractLineCode;

    private String purchaseContractLineId;

    private String purchaseContractLineCode;

    private String categoryId;

    private String categoryCode;

    private String categoryName;

    private String itemId;

    private String itemLineId;

    private String itemLineCode;

    private String itemName;

    private String itemSpecDesc;

    private String uomWidth;

    private String uomHeight;

    private String uomLength;

    private String uomDensity;

    private String uomWeight;

    private BigDecimal noticeDeliveryQuantity;

    private BigDecimal deliveryQuantity;

    private BigDecimal receiptQuantity;

    private String deliveryLineRemark;

    private String unitType;

    private String fromType;

    private String fromLineId;

    private String fromLineCode;

    private String createUserId;

    private Date createDate;

    private String createUserName;

    private String modifyUserId;

    private Date modifyDate;

    private String modifyUserName;

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