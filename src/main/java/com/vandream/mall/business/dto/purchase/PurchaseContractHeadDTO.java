package com.vandream.mall.business.dto.purchase;

import com.vandream.mall.business.dto.BaseDTO;
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
public class PurchaseContractHeadDTO extends BaseDTO{

    /**
     * 采购订单id
     */
    private String purchaseContractHeadId;
    /**
     * 采购订单号
     */
    private String purchaseContractCode;

    private String contractType;

    private String contractName;

    private Long contractBeginDate;

    private Long contractEndDate;

    private String currencyCode;

    private String outContractCode;
    /**
     * 供方企业id
     */
    private String supplierCompanyId;

    private String supplierId;

    private String supplierCode;

    private String supplierName;

    private String staffId;

    private String staffCode;

    private String staffName;

    private String departmentId;

    private String departmentCode;

    private String departmentName;
    /**
     * 订单状态
     */
    private String contractStatus;

    private String contractRemarks;

    private String projectId;

    private String projectCode;
    /**
     * 项目名称
     */
    private String projectName;

    private String demandId;

    private String demandCode;

    private String solutionId;

    private String solutionCode;

    private String salesContractHeadId;

    private String salesContractCode;

    private String customerCompanyId;

    private String customerId;

    private String customerCode;

    private String customerName;

    private String customerConsigneetId;

    private String customerConsigneetName;

    private String customerConsigneetPhone;

    private String customerSiteId;

    private String cusSiteCountryCode;

    private String cusSiteCountryName;

    private String cusSiteRegionCode;

    private String cusSiteRegionName;

    private String cusSiteCityCode;

    private String cusSiteCityName;

    private String cusSiteCountyCode;

    private String cusSiteCountyName;

    private String customerSiteAddress;

    private BigDecimal noTaxAmount;

    private BigDecimal taxAmount;

    /**
     * 订单金额
     */
    private BigDecimal totalAmount;

    private String templateId;

    private String templateCode;

    private String signType;

    private Long signDate;

    private String taxCodeType;

    private String signPlace;

    private String signPerson;

    private String customerSignPerson;

    private String fromType;

    private String fromId;

    private String fromCode;
    /**
     * 订单创建时间
     */
    private Long createDate;

    private String createUserId;

    private String createUserName;

    private Long modifyDate;

    private String modifyUserId;

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

    private String extCol16;

    private String extCol17;

    private String extCol18;

    private String extCol19;

    private String extCol20;
    /**
     *清单名称
     */
    private String itemName;

    /**
     *实收金额
     */
    private BigDecimal receipts;
    /**
     * 地址
     */
    private String customerAddress;
    /**
     * 发货信息头id,用以判断此订单下是否有发货信息
     */
    private String deliveryHeadId;
    /**
     * 已读未读 1：已读 2：未读
     */
    private String isRead;

}