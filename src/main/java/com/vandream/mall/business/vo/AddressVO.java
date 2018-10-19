package com.vandream.mall.business.vo;


import com.alibaba.fastjson.annotation.JSONField;
import com.vandream.mall.commons.annotation.FieldAlias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author liuyuhong
 * @date 2018/3/21
 * @time 10:16
 * @description
 */
@Data
@Setter
@Getter
public class AddressVO {

    /** 地址记录id **/
    @FieldAlias("companySiteId")
    private String addressId;
    /** 省编码 **/
    @FieldAlias("siteRegionCode")
    private String provinceCode;
    /** 市编码 **/
    @FieldAlias("siteCityCode")
    private String cityCode;
    /** 区编码 **/
    @FieldAlias("siteCountyCode")
    private String areaCode;
    /** 省名称 **/
    @FieldAlias("siteRegionName")
    private String provinceName;
    /** 市名称 **/
    @FieldAlias("siteCityName")
    private String cityName;
    /** 区名称 **/
    @FieldAlias("siteCountyName")
    private String countyName;
    /** 所在区域 **/
    @FieldAlias("areas")
    private String areas;
    /** 详细地址 **/
    @FieldAlias("siteAddress")
    private String address;
    @FieldAlias("companyId")
    private String companyId;
    /** 联系人 **/
    @FieldAlias("siteReceiver")
    private String contacts;
    /** 联系人电话 **/
    @FieldAlias("siteReceiverMobile")
    private String contactNumber;
    /** 邮编 **/
    @FieldAlias("sitePostal")
    private String postCode;
    /** 是否为默认地址 0：否，1：是 **/
    @FieldAlias("isDefault")
    private String isDefault;
    /** 操作人姓名 **/
    @FieldAlias("operatorUserName")
    private String operatorUserName;
    /** 操作人id **/
    @FieldAlias("operatorUserId")
    private String userId;

    private String countryCode;
    private String countryName;
}
