package com.vandream.mall.business.dto.order;

import com.vandream.mall.commons.annotation.FieldAlias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author dingjie
 * @date 2018/4/11
 * @time 19:33
 * Description:
 */
@Data
@Getter
@Setter
public class OrderItemLineDTO {
    /** 商品主id */
    @FieldAlias("skuId")
    private String itemId;
    /** 商品行id */
    private String itemLineId;
    /** 商品名称 */
    private String itemName;
    /** 品牌 */
    private String brand;
    /** 数量 */
    @FieldAlias("itemNo")
    private BigDecimal quantity;
    /** 计量单位名称 */
    @FieldAlias("itemUnit")
    private String unitTypeName;
    /** 需求描述 */
    private String demandRemark;
}
