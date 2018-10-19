package com.vandream.mall.business.dto.delivery;

import com.vandream.mall.business.dto.BaseDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/4/11
 * Time: 14:04
 * Description: 发货单列表
 */
@Getter
@Setter
@Data
public class DeliveryInfoListDTO extends BaseDTO {

    /** 发货单id **/
    private String deliveryHeadId;

    /** 发货单Code **/
    private String deliveryCode;

    /** 发货单行id **/
    private String deliveryLineId;

    /** 发货单行Code **/
    private String deliveryLineCode;

    /** 商品名称 **/
    private String itemName;

    /** 发货清单总量 **/
    private BigDecimal itemCount;

    /** 发货时间 **/
    private Long deliveryDate;

    /** 收货地址 **/
    private String address;

    /** 收货人 **/
    private String contact;

    /** 联系电话 **/
    private String contactPhone;

    /** 物流状态 **/
    private String status;

    /**  发货单已读未读状态:0.未读 1.已读**/
    private String isRead;
}
