package com.vandream.mall.business.dto.delivery;

import com.vandream.mall.business.dto.BaseDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.scripting.bsh.BshScriptUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 *
 * @author : liguoqing
 * @date : 2018/4/8
 * Time: 16:32
 * Description:
 */
@Data
@Getter
@Setter
public class DeliverySubmitDTO  extends BaseDTO{

    /** 用户id **/
    private String userId;

    /** 用户名称 **/
    private String userName;
    
    /**发货时间**/
    private String  deliveryDate;

    /** 物流联系人 **/
    private String transportContacts;

    /** 物流类型 **/
    private String transportType;

    /** 物流联系人方式 **/
    private String transportContactsPhone;

    /** 物流公司名称 **/
    private String transportCompany;

    /** 物流单号 **/
    private String transportCode;

    /** 车牌号 **/
    private String carShipNo;

    /** 司机 **/
    private String carContacts;

    /** 司机联系方式 **/
    private String carContactsPhone;

    /** 来源单据类型 **/
    private String fromType ;

    /** 来源单据号 **/
    private String fromCode;

    /** 来源单据号id **/
    private String fromId;

    /** 发货备注 **/
    private String deliveryRemark;

    /** 商品发货信息 **/
    private List<DeliverySubLineDTO> subLineList;

    /** 账套code **/
    private String bookCode;

}
