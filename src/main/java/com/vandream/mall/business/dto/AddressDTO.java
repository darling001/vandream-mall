package com.vandream.mall.business.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author liuyuhong
 * @date 2018/3/19
 * @time 17:05
 * @description
 */
@Data
public class AddressDTO extends BaseDTO{
    /** 操作类型 新增：N;修改-U; **/
    private String operatorType;
    /** 公司id **/
    private String companyId;
    /** 地址记录id **/
    private String companySiteId;
    /** 地址类型，10：发票邮寄地址；20：收货地址；30：暂不使用 **/
    private String siteType;
    /** 国家编码 **/
    private String siteCountryCode;
    /** 国家名称 **/
    private String siteCountryName;
    /** 省编码 **/
    private String siteRegionCode;
    /** 省名称 **/
    private String siteRegionName;
    /** 市编码 **/
    private String siteCityCode;
    /** 市名称 **/
    private String siteCityName;
    /** 区编码 **/
    private String siteCountyCode;
    /** 区名称 **/
    private String siteCountyName;
    /** 详细地址 **/
    private String siteAddress;
    /** 联系人 **/
    private String siteReceiver;
    /** 联系人电话 **/
    private String siteReceiverMobile;
    /** 邮编 **/
    private String sitePostal;
    /** 地址状态 地址状态：00停用-40启用**/
    private String siteStatus;
    /** 所在区域 **/
    private String areas;
    /** 是否默认地址0：否，1是 **/
    private String isDefault;
    private String countryCode;
    private String countryName;
}
