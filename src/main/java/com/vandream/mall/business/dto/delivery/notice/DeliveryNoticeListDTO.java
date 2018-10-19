package com.vandream.mall.business.dto.delivery.notice;

import com.vandream.mall.business.dto.BaseDTO;
import java.util.Date;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/4/4
 * Time: 14:09
 * Description: 发货管理列表DTO
 */
@Data
@Getter
@Setter
public class DeliveryNoticeListDTO extends BaseDTO {

    /** 发货通知单Id **/
    private String deliveryNoticeId;

    /** 销售号码订单id **/
    private String saleContractHeadId;

    /** 发货通知单号 **/
    private String deliveryNoticeCode;

    /** 通知日期 **/
    private Long deliveryNoticeDate;

    /** 销售订单号 **/
    private String saleContractHeadCode;

    /** 要求交货日期 **/
    private Long deliveryDate;

    /** 项目名称 **/
    private String projectName;

    /** 招采经理 **/
    private String staffer;

    /** 联系号码 **/
    private String contactTel;

    /** 状态 **/
    private String status;

    /** 发货单数量 **/
    private Integer deliveryCount;

    /** 发货通知单下的发货单是否有未读标识符:0.未读 1.已读 **/
    private String isRead;

}
