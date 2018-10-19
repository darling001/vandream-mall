package com.vandream.mall.business.vo.purchase;

import com.vandream.mall.business.vo.base.BaseVO;
import com.vandream.mall.commons.annotation.FieldAlias;
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
public class OrderItemInfoVO extends BaseVO {
    /**
     * 商品 Id
     */
    private String itemId;
    /**
     * 供应商商品 Id
     */
    private String itemLineId;
    /**
     * 是否标品 Y-是;N-否
     */
    private String standardFlag;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 行业参数
     */
    @FieldAlias("itemSpecDesc")
    private String paramters;
    /**
     * 数量
     */
    @FieldAlias("purchaseQuantity")
    private BigDecimal quantity;
    /**
     * 计量单位
     */
    @FieldAlias("unitType")
    private String unit;
    /**
     * 含税单价
     */
    @FieldAlias("purchasePrice")
    private BigDecimal unitPrice;
    /**
     * 税率
     */
    @FieldAlias("taxCode")
    private String taxRate;
    /**
     * 金额
     */
    @FieldAlias("totalAmount")
    private BigDecimal amountPrice;
    /**
     * 预计交付日期
     */
    @FieldAlias("expectedReceiptDate")
    private Long expectDate;

}
