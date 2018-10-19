package com.vandream.mall.business.vo.order;

import com.vandream.mall.business.vo.base.BaseVO;
import com.vandream.mall.commons.annotation.FieldAlias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author dingjie
 * @date 2018/4/11
 * @time 17:44
 * Description:
 */
@Data
@Setter
@Getter
public class BatchOrderVO extends BaseVO {
    /** 用户id */
    @NotBlank(message = "用户id不能为空")
    private String userId;
    /** 用户名称 */
    @NotBlank(message = "用户名称不能为空")
    private String userName;
    /** 当前需方id */
    @NotBlank(message = "当前需方id不能为空")
    private String customerId;
    /** 需方编码 */
    @NotBlank(message = "需方编码不能为空")
    private String customerCode;
    /** 需方名称 */
    @NotBlank(message = "需方名称不能为空")
    private String customerName;
    /** 联系电话 */
    @NotBlank(message = "联系电话不能为空")
    private String contactPhone;
    /** 联系人 */
    @NotBlank(message = "联系人不能为空")
    private String contactName;
    /** 收货地址 */
    @NotBlank(message = "详细收货地址不能为空")
    private String address;
    /** 交货日期 */
    @NotNull(message = "交货日期不能为空")
    private Long leadTime;
    /** 当前企业Id */
    @NotBlank(message = "当前企业Id不能为空")
    private String companyId;
    /** 省份code */
    @NotBlank(message = "省份编号不能为空")
    private String provinceCode;
    /**城市code */
    @NotBlank(message = "城市编号不能为空")
    private String cityCode;
    /** 地区code */
    @NotBlank(message = "地区编号不能为空")
    private String areaId;
    /** 省份名称  */
    @NotBlank(message = "省份名称不能为空")
    private String provinceName;
    /** 城市名称 */
    @NotBlank(message = "城市名称不能为空")
    private String cityName;
    /** 地区名称 */
    @NotBlank(message = "地区名称不能为空")
    private String areaName;
    /** 邮编 */
    @NotBlank(message = "邮编不能为空")
    private String postCode;

    /** 是否默认 */
    private String isDefault;
    /** 国家名称 */
    @NotBlank(message = "国家名称不能为空")
    private String countryName;
    /** 国家code */
    @NotBlank(message = "国家编号不能为空")
    private String countryCode;
    /**
     * 预订单id
     */
    @NotBlank(message = "预订单id不能为空")
    private String advanceOrderId;

}
