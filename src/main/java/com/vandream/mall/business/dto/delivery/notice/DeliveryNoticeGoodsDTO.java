package com.vandream.mall.business.dto.delivery.notice;

import com.vandream.mall.business.dto.BaseDTO;
import java.math.BigDecimal;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/4/3
 * Time: 19:31
 * Description: 发货通知单商品信息
 */
@Setter
@Getter
@Data
public class DeliveryNoticeGoodsDTO extends BaseDTO {

    /** 商品名称 **/
    private String itemName;

    /** 品牌名称 **/
    private String brandName;

    /** 技术参数 **/
    private String parameters;

    /** 单位 **/
    private String unit;

    /** 订单总数量 **/
    private BigDecimal orderCount ;

    /** 发运总数量 **/
    private BigDecimal noticeNumber;
}
