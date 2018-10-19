package com.vandream.mall.business.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/3/28
 * Time: 19:13
 * Description: 企业认证
 */
@Setter
@Getter
@Data
public class SalesReservationDTO extends BaseDTO implements Serializable {

    /** 锁货通知单id **/
    private String salesReservationId;

    /** 商品id **/
    private String itemId;

    /** 销售合同id **/
    private String contractId;

    /** 锁货通知单 **/
    private String salesReservationCode;

    /** 通知时间 **/
    private Long notifyDate;

    /** 项目名称 **/
    private String projectName;

    /** 收货地址 **/
    private String address;

    /** 省 **/
    private String provinceName;

    /** 城市 **/
    private String cityName;

    /** 县区 **/
    private String countryName;

    /** 派发单号 **/
    private String dispatchCode;

    /** 招采经理 **/
    private String purchaser;

    /** 联系方式 **/
    private String contactPhone;

    /** 备注 **/
    private String remark;

    /** 是否已读 **/
    private String isRead;

    /**
     * 商品信息
     **/
    private List<ContractLineDTO> itemList;

}
