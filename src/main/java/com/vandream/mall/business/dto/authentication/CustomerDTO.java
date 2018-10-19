package com.vandream.mall.business.dto.authentication;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vandream.mall.business.domain.Attachment;
import com.vandream.mall.business.dto.BaseDTO;
import com.vandream.mall.commons.annotation.FieldAlias;
import com.vandream.mall.commons.annotation.JSONField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/21
 * @time : 13:53
 * Description:
 * 需方认证DTO
 */
@Data
@Setter
@Getter
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CustomerDTO extends BaseDTO {

    private static final long serialVersionUID = -5049000949878913629L;
    /**
     * 企业名称
     */
    @FieldAlias("companyName")
    private String companyName;
    /**
     * 企业简称
     */
    private String companyShortName;
    /**
     * 企业英文名称
     */
    private String companyEnglishName;
    /**
     * 企业联系人姓名
     */
    @FieldAlias("contacts")
    private String contactName;
    /**
     * 企业联系人手机号
     */
    @FieldAlias("contactNumber")
    private String contactMobile;
    /**
     * 企业联系人职务
     */
    @FieldAlias("position")
    private String contactDuty;
    /**
     * 企业性质
     */
    private String companyType;
    /**
     * 是否三证合一 0:老版,1:新版
     */
    @FieldAlias("certificateType")
    private String certificateType;
    /**
     * 统一社会信用代码号
     */
    @FieldAlias("creditCode")
    private String registerCreditCode;
    /**
     * 组织机构代码号
     */
    private String registerOrgCode;
    /**
     * 工商注册号
     */
    private String registerBusinessNo;

    /**
     * 纳税人识别号
     */
    private String corporationTax;
    /**
     * 企业类型
     */
    private String registerBusinessType;
    /**
     * 注册时间
     */
    private String registerDate;
    /**
     * 注册国别
     */
    private String registerCountry;
    /**
     * 注册地址
     */
    private String registerAddress;
    /**
     * 注册资本
     */
    private String registerCapital;
    /**
     * 币种
     */
    private String registerCurrency;
    /**
     * 经营期限/起
     */
    private String registerBeginDate;
    /**
     * 经营期限/止
     */
    private String registerEndDate;
    /**
     * 法人代表
     */
    private String legalPerson;

    /**
     * 所属行业
     */
    private String industry;
    /**
     * 经营范围
     */
    private String businessScope;
    /**
     * 一般纳税人
     */
    private String generalTaxpayer;
    /**
     * 发票类型
     */
    private String invoiceType;
    /**
     * 开票地址
     */
    private String invoiceAddress;
    /**
     * 开户银行
     */
    private String depositBank;
    /**
     * 开户银行账号
     */
    private String depositBankAccount;

    /**
     * 需方类别
     */
    @FieldAlias("customerType")
    private String customerType;
    /**
     * 登录账号ID，业务数据需要登录账号ID
     */
    @FieldAlias("userId")
    private String accountId;
    /**
     * 来源类别（10商城）
     */
    private String fromType;
    /**
     * 商城账号名称
     */
    @FieldAlias("userName")
    private String accountName;
    /**
     * 业务类型 数据字典未定义：暂定10
     */
    private String businessType;
    /**
     * 附件列表
     */
    @FieldAlias("attachmentList")
    private List<Attachment> attachmentList = new ArrayList<>();


}
