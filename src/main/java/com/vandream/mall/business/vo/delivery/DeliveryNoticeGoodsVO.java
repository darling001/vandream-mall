package com.vandream.mall.business.vo.delivery;

import com.vandream.mall.business.dto.BaseDTO;
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
 * Time: 19:31
 * Description: 发货通知单商品信息
 */
@Setter
@Getter
@Data
public class DeliveryNoticeGoodsVO extends BaseVO {

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
