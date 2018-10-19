package com.vandream.mall.business.vo.delivery;

import com.vandream.mall.business.dto.BaseDTO;
import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/4/4
 * Time: 14:09
 * Description: 发货管理列表VO
 */
@Getter
@Setter
@Data
public class DeliveryNoticeListVO extends BaseVO{

    /** 发货通知单Id **/
    private String deliveryNoticeId;

    /** 销售号码订单id **/
    private String saleContractHeadId;

    /** 发货通知单号 **/
    private String deliveryNoticeCode;

    /** 通知日期 **/
    private Date deliveryNoticeDate;

    /** 销售订单号 **/
    private String saleContractHeadCode;

    /** 要求交货日期 **/
    private Date DeliveryDate;

    /** 项目名称 **/
    private String projectName;

    /** 招采经理 **/
    private String staffer;

    /** 联系号码 **/
    private String contactTel;

    /** 状态 **/
    private String status;



}
