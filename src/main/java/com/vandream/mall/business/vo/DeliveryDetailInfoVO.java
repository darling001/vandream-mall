package com.vandream.mall.business.vo;

import com.vandream.mall.business.vo.base.BaseVO;
import com.vandream.mall.business.vo.delivery.DeliverySendedGoodsVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author dingjie
 * @date 2018/4/4
 * @time 13:40
 * Description:
 */
@Data
@Setter
@Getter
public class DeliveryDetailInfoVO extends BaseVO {
    /** 发货状态 */
    private String status;
    /** 发货单id */
    private String saleHeadId;
    /** 发货单号 */
    private String deliveryCode;
    /** 发货通知号 */
    private String deliveryNoticeCode;
    /** 销售订单号 */
    private String purchaseHeadId;
    /** 收货地址+联系人信息 */
    private String address;
    /** 发运通知日期 **/
    private Long deliveryNoticeDate;
    /** 发货日期 */
    private Long deliveryDate;
    /** 到货时间 */
    private Long arrivalDate;
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
    /** 物流记录集合 */
    private List<LogisticsVO> logisticsList;
    /** 商品集合 */
    private List<DeliverySendedGoodsVO> itemList;
    /** 物流备注 */
    private String remark;
    /** 签收时间 */
    private Long signingTime;
    /** 签收信息 */
    private String  signingInfo;
    /** 附件 */
    private List<AttachmentVO> attachmentList;

    @Override
    public String toString() {
        return "DeliveryDetailInfoVO{" +
                "status='" + status + '\'' +
                ", saleHeadId='" + saleHeadId + '\'' +
                ", deliveryCode='" + deliveryCode + '\'' +
                ", deliveryNoticeCode='" + deliveryNoticeCode + '\'' +
                ", purchaseHeadId='" + purchaseHeadId + '\'' +
                ", address='" + address + '\'' +
                ", deliveryNoticeDate=" + deliveryNoticeDate +
                ", deliveryDate=" + deliveryDate +
                ", arrivalDate=" + arrivalDate +
                ", logisticsName='" + logisticsName + '\'' +
                ", logisticsCode='" + logisticsCode + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", plateContactName='" + plateContactName + '\'' +
                ", plateContactTel='" + plateContactTel + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", logisticsList=" + logisticsList +
                ", itemList=" + itemList +
                ", remark='" + remark + '\'' +
                ", signingTime=" + signingTime +
                ", signingInfo='" + signingInfo + '\'' +
                ", attachmentList=" + attachmentList +
                '}';
    }
}
