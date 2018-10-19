package com.vandream.mall.business.vo.delivery;

import com.vandream.mall.business.dto.delivery.DeliverySubLineDTO;
import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 *
 * @author : liguoqing
 * @date : 2018/4/8
 * Time: 14:48
 * Description:
 */
@Data
@Getter
@Setter
public class DeliverySubmitVO extends BaseVO {

    /** 自有车队类型 **/
    public static final String LOGISTICS_TYPE_SELF ="1";

    /** 其他物流类型 **/
    public static final String LOGISTICS_TYPE_OTHERS = "2";

    public static final String SUBMIT_SUCCESS_MESSAGE = "发货成功";
    
    public static final String SUBMIT_FAILURE_MESSAGE = "发货失败";

    /** 用户id **/
    @NotBlank(message = "用户id为空")
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
    private String fromType = "SOMNOTICE";

    /** 来源单据号 **/
    private String fromCode;

    /** 来源单据号id **/
    private String fromId;

    /** 发货备注 **/
    private String deliveryRemark;

    /** 账套code **/
    private String bookCode ="1000";

    /** 商品发货信息 **/
    private Object subList;

}
