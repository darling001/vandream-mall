package com.vandream.mall.business.vo.delivery;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/4/3
 * Time: 21:21
 * Description: 发货单商品信息
 */
@Getter
@Setter
@Data
public class DeliveryGoodsVO extends BaseVO{

    /** 发货单明细id **/
    private String deliveryNoticeLineId;

    /** 发货单明细Code **/
    private String deliveryNoticeLineCode;

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
        return "DeliveryGoodsVO{" +
                "itemName='" + itemName + '\'' +
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
