package com.vandream.mall.business.dto.demand;

import com.vandream.mall.commons.annotation.FieldAlias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dingjie
 * @date 2018/4/3
 * @time 17:34
 * Description:
 */
@Data
@Setter
@Getter
public class SubLineDTO {
    /** 三级品种id */
    private String categoryId;
    /** 三级品种code */
    private String categoryCode;
    /** 三级品种名称 */
    private String categoryName;
    /** 招采经理名称 */
    private String purchaseManager;
    /** 招采经理id */
    private String purchaseManagerId;
    /** 招采经理code */
    private String purchaseManagerCode;
    /** 商品主 id */
    private String itemId;
    /** 商品id */
    private String itemLineId;
    /** 商品类型 */
    private String itemType;
    /** 预计商品 */
    private String itemName;
    /** 商家代码 */
    private String itemLineCode;
    /** 行业参数 */
    private String itemSpecDesc;
    /** 品牌 */
    @FieldAlias("brandName")
    private String brand;
    /** 需求描述 */
    @FieldAlias("description")
    private String demandRemark;
    /** 计量单位名称 */
    @FieldAlias("unit")
    private String unitTypeName;
    /** 计量单位代码 */
    private String unitType;
    /** 数量 */
    private Integer quantity;
    /** 金额 */
    private String goodAmount;
    /** 售价 */
    private String salePrice1;
    private String itemPriceId;
    private String standardFlag;
}
