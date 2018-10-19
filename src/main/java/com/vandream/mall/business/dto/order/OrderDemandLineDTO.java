package com.vandream.mall.business.dto.order;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vandream.mall.business.domain.Attachment;
import com.vandream.mall.business.dto.BaseDTO;
import com.vandream.mall.commons.annotation.FieldAlias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author dingjie
 * @date 2018/4/11
 * @time 18:21
 * Description:
 */
@Data
@Getter
@Setter
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class OrderDemandLineDTO extends BaseDTO {
    /** 需求单id **/
    private String demandId;
    /** 需求单号 **/
    private String demandCode;
    /** 需方id **/
    private String customerId;
    /** 客户编码 **/
    private String customerCode;
    /** 需方名称 **/
    private String customerName;
    /** 需求简述 **/
    private String demandResume;
    /** 需求详述 **/
    private Object  demandDiscuss;
    /** 需求状态 **/
    private String demandStatus;
    /** 需求类型 **/
    private String demandType;
    /** 来源类别 **/
    private String fromType;
    /**  **/
    private String fromId;
    private String fromCode;
    /** 需方提出时间 **/
    private String demandTime;
    /** 需求提出人id **/
    @FieldAlias("userId")
    private String demandAccountId;
    /** 需求提出人名称 **/
    @FieldAlias("userName")
    private String demandAccountName;
    /** 需求联系人 **/
    @FieldAlias("contactName")
    private String demandContacts;
    /** 需求联系人电话 **/
    @FieldAlias("contactPhone")
    private String demandPhone;
    /** 需求联系人角色 **/
    private String demandContactsRole;
    /** 项目id **/
    private String projectId;
    /** 项目编号 **/
    private String projectCode;
    /** 项目名称 **/
    private String projectName;
    /** 预算 **/
    private String budget;
    /** 币种 **/
    private String currencyCode;
    /** 预计要求交货期起 **/
    private String deliveryPeriodStart;
    /** 预计要求交货期止 **/
    private String deliveryPeriodEnd;
    /** 平台联系人 **/
    private String platformContacts;
    /** 平台联系人code **/
    private String platformContactsCode;
    /** 平台联系人id **/
    private String platformContactsId;
    /** 国家代码 **/
    private String siteCountryCode;
    /** 国家名称 **/
    private String siteCountryName;
    /** 省代码 **/
    private String siteRegionCode;
    /** 省名称 **/
    private String siteRegionName;
    /** 市代码 **/
    private String siteCityCode;
    /** 市名称 **/
    private String siteCityName;
    /** 区代码 **/
    private String siteCountyCode;
    /** 区名称 **/
    private String siteCountyName;
    /** 乡镇代码 **/
    private String siteTownCode;
    /** 乡镇名称 **/
    private String siteTownName;
    /** 收货地址 **/
    @FieldAlias("address")
    private String customerSiteArea;
    /** 商城账号ID **/
    private String accountId;
    /** 商城账号名称 **/
    private String accountName;
    /** 账套code */
    private String bookCode;
    /** 业务类型 */
    private String businessType;
    /**
     * 需求单明细信息
     */
    private List<OrderItemLineDTO> subLineList;
    /**
     * 附件信息（传入格式参照）
     */
    private List<Attachment> attachmentList;
    /**
     * 收货人名称
     */
    private String customerConsigneetName;
    /**
     * 收货人联系电话
     */
    private String customerConsigneetPhone;
}
