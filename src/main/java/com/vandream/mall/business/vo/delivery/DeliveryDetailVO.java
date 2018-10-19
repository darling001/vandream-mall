package com.vandream.mall.business.vo.delivery;

import com.vandream.mall.business.dto.delivery.DeliveryGoodsDTO;
import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/4/3
 * Time: 16:50
 * Description: 供方发货单详情VO类
 */
@Setter
@Getter
@Data
public class DeliveryDetailVO extends BaseVO{

    /** 发货单号 */
    private String deliveryCode;

    /** 发货日期 */
    private Long deliveryDate;

    /** 销售订单号 **/
    private String purchaseHeadId;

    /** 发货知单id **/
    private String deliveryNoticeId;

    /** 发运通知日期 **/
    private Long deliveryNoticeDate;

    /** 发货通知号 **/
    private String deliveryNoticeCode;

    /** 固定传：SOMNOTICE **/
    private String fromType;

    /** 发货状态 **/
    private String status;

    /** 收货地址+联系人信息 **/
    private String address;

    /** 商品详情 **/
    private List<DeliveryGoodsVO> itemList;



}
