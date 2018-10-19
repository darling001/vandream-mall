package com.vandream.mall.business.dto.delivery;

import com.vandream.mall.business.dto.BaseDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/4/3
 * Time: 21:09
 * Description: 发货单商品信息
 */
@Getter
@Setter
@Data
public class DeliveryGoodsDTO extends BaseDTO {

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



}
