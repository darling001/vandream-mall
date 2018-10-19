package com.vandream.mall.business.dto.purchase;

import java.math.BigDecimal;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Li Jie
 */
@Data
@Getter
@Setter
public class PurchaseContractLineDTO {
    private String purchaseContractLineId;

    private String purchaseContractLineCode;

    private String purchaseContractHeadId;

    private Long expectedReceiptDate;

    private String categoryId;

    private String categoryCode;

    private String categoryName;

    private String itemId;
    /**
     * 商品名称
     */
    private String itemName;

    private String itemLineId;

    private String itemLineCode;

    private String itemType;

    private String itemSpecDesc;

    private String materialTypeCode;

    private String brandName;

    private String lockFlag;

    private Long leadTime;

    private String areaCode;

    private String itemLineOutsysCode;

    private BigDecimal purchaseQuantity;

    private String unitType;

    private String taxCodeType;

    private String taxCode;

    private BigDecimal purchasePrice;

    private BigDecimal noTaxAmount;

    private BigDecimal taxAmount;

    private BigDecimal totalAmount;

    private String contractLineRemarks;

    private BigDecimal moreRate;

    private BigDecimal lessRate;

    private String solutionLineId;

    private String solutionLineCode;

    private String demandLineId;

    private String demandLineCode;

    private String salesContractLineId;

    private String fromType;

    private String fromLineId;

    private String fromLineCode;

    private String createUserId;

    private String createUserName;

    private Long createDate;

    private String modifyUserId;

    private String modifyUserName;

    private Long modifyDate;

    private String orgId;

    private String groupId;

    private String bookId;

    private String extCol1;

    private String extCol3;

    private String extCol5;

    private String extCol4;

    private String extCol2;

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

    private String extCol16;

    private String extCol17;

    private String extCol18;

    private String extCol19;

    private String extCol20;
    /**
     * 清单条目数量
     */
    private Integer itemTotal;
    /**
     *最大预计交期
     */
    private Long maxExpectedDelivery;
    /**
     *最小预计交期
     */
    private Long minExpectedDelivery;
    
    private Boolean isChanged;
    
    private String itemVersion;

    private String itemLineVersion;





}