package com.vandream.mall.business.vo;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/4/11 17:49
 */
@Data
@Setter
@Getter
public class AddOrderVO extends BaseVO{
    @NotBlank(message = "用户id不能为空")
    private String userId;
    @NotBlank(message = "用户userName不能为空")
    private String userName;
    @NotBlank(message = "customerCode不能为空")
    private String customerCode;
    @NotBlank(message = "customerId不能为空")
    private String customerId;
    @NotBlank(message = "customerNam不能为空")
    private String customerName;
    @NotBlank(message = "itemId不能为空")
    private String itemId;
    @NotBlank(message = "areaCode不能为空")
    private String areaCode;
    private BigDecimal number;
    @NotBlank(message = "address不能为空")
    private String address;
    @NotBlank(message = "contactPhone不能为空")
    private String contactPhone;
    @NotBlank(message = "ContactName不能为空")
    private String contactName;
    @NotNull(message = "leadTime不能为空")
    private Long leadTime;
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
}
