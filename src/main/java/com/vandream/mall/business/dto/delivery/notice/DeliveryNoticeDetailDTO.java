package com.vandream.mall.business.dto.delivery.notice;

import com.vandream.mall.business.dto.BaseDTO;
import com.vandream.mall.business.vo.delivery.DeliveryNoticeGoodsVO;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/4/3
 * Time: 17:07
 * Description: 发货通知单详情
 */
@Setter
@Getter
@Data
public class DeliveryNoticeDetailDTO extends BaseDTO {

    /** 发货通知单id **/
    private String deliveryNoticeId;

    /** 发货通知单号 **/
    private String deliveryNoticeCode;

    /** 发货通知日期 **/
    private Long deliveryNoticeDate;

    /** 预计到货日期 **/
    private Long expectDate;

    /** 销售订单id **/
    private String saleContractId;

    /** 销售订单 **/
    private String saleContractCode;

    /** 客户名称 **/
    private String customerName;

    /** 收货地址 **/
    private String address;

    /** 招采经理 **/
    private String purchaser;

    /** 电话 **/
    private String contactTel;

    /** 备注信息 **/
    private String remark;

    /** 商品信息 **/
    private List<DeliveryNoticeGoodsVO> deliveryGoodsList;


}
