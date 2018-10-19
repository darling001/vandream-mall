package com.vandream.mall.business.dto.delivery;

import com.vandream.mall.business.dto.BaseDTO;
import com.vandream.mall.business.vo.delivery.DeliveryGoodsVO;
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
 * Time: 15:18
 * Description: 供方发货单详情DTO类
 */
@Setter
@Getter
@Data
public class DeliveryDetailDTO extends BaseDTO {

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
    /** 到货时间 */
    private Long arrivalDate;

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

    /** 物流公司名称 */
    private String logisticsName;
    /** 物流单号 */
    private String logisticsCode;
    /** 车牌号 */
    private String plateNumber;
    /** 司机 */
    private String plateContactName;
    /** 司机联系号码 */
    private String plateContactTel;
    /** 物流联系人 */
    private String contactName;
    /** 物流联系号码 */
    private String contactPhone;
    /** 物流备注 */
    private String remark;
    /** 签收时间 */
    private Long signingTime;
    /** 签收信息 */
    private String  signingInfo;







}