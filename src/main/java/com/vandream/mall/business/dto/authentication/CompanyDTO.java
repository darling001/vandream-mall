package com.vandream.mall.business.dto.authentication;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/3/19
 * Time: 19:20
 * Description: 企业认证
 */
@Data
@Setter
@Getter
public class CompanyDTO {

    /** 企业名称 **/
    private String companyName;

    /** 企业标识；1、供方，2、需方，3、两者都是 **/
    private String companyFlag;

    /** 工商注册号 **/
    private String businessLicense;

    /** 统一社会信用代码号 **/
    private String registerCreditCode;

    /** 是否三证合一；0：老版，1：新版 **/
    private String certificateType;

    /** 法人代表 **/
    private String businessEntity;

    /** 注册地址 **/
    private String registeredAddress;

    /** 企业性质 **/
    private String companyType;

    /** 经营（业务）范围 **/
    private String businessScope;

    /** 企业联系人姓名 **/
    private String contact;

    /** 企业联系人手机号 **/
    private String contactNumber;

    /** 经营区域 **/
    private String businessArea;

    /** 需方类别 **/
    private String customerType;

    /** 供方类别 **/
    private String supplierType;

    /** 主营产品 **/
    private String mainProduct;

    /** 需方会员等级 **/
    private String memberRating;

    /** 品牌（供方）**/
    private String brand;

    /** 供方id **/
    private String supplierId;

    /** 需方id **/
    private String customerId;




}
