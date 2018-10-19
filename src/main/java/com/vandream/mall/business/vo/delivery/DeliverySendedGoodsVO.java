package com.vandream.mall.business.vo.delivery;

import com.vandream.mall.business.vo.base.BaseVO;
import com.vandream.mall.commons.annotation.FieldAlias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author dingjie
 * @date 2018/4/11
 * @time 13:58
 * Description:
 */
@Data
@Getter
@Setter
public class DeliverySendedGoodsVO extends BaseVO {

    private static final long serialVersionUID = 5686657474939814324L;
    /** 发货单明细id **/
    @FieldAlias("deliveryNoticeLineId")
    private String deliveryLineId;

    /** 发货单明细Code **/
    @FieldAlias("deliveryNoticeLineCode")
    private String deliveryLineCode;

    /** 商品名称 **/
    private String itemName;

    /** 品牌名称 **/
    private String brandName;

    /** 技术参数 **/
    private String parameters;

    /** 数量 **/
    private BigDecimal quantity;

    /** 单位 **/
    private String unit;

    /** 发货通知量 **/
    private BigDecimal noticeQuantity;

    /** 已发数量 **/
    private BigDecimal historyQuantity;

    /** 实发数量 **/
    private BigDecimal realQuantity;
    /** 备注 */
    private String itemRemark;

    @Override
    public String toString() {
        return "DeliverySendedGoodsVO{" +
                "deliveryLineId='" + deliveryLineId + '\'' +
                ", deliveryLineCode='" + deliveryLineCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", brandName='" + brandName + '\'' +
                ", parameters='" + parameters + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                ", noticeQuantity=" + noticeQuantity +
                ", historyQuantity=" + historyQuantity +
                ", realQuantity=" + realQuantity +
                ", itemRemark='" + itemRemark + '\'' +
                '}';
    }
}
