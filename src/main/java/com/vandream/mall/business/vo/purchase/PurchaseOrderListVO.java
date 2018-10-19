package com.vandream.mall.business.vo.purchase;

import com.vandream.mall.business.dto.purchase.PurchaseContractLineDTO;
import com.vandream.mall.commons.annotation.FieldAlias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng & Li Jie
 * @date : 2018/4/4
 * @time : 11:25
 * Description:
 */
@Data
@Setter
@Getter
public class PurchaseOrderListVO {

    /**
     * 订单id
     */
    @FieldAlias("purchaseContractHeadId")
    private String orderId;
    /**
     * 销售订单号
     */
    @FieldAlias("purchaseContractCode")
    private String orderCode;
    /**
     * 项目名称
     */
    @FieldAlias("projectName")
    private String projectName;
    /**
     * 订单日期
     */
    @FieldAlias("createDate")
    private Long createTime;
    /**
     * 清单名称
     */
    @FieldAlias("itemName")
    private String itemName;
    /**
     * 清单数量
     */
    private Integer itemTotal;
    /**
     * 订单金额
     */
    @FieldAlias("totalAmount")
    private BigDecimal orderAmount;
    /**
     * 最大预计交期
     */
    private Long maxExpectedDelivery;
    /**
     * 最小预计交期
     */
    private Long minExpectedDelivery;
    /**
     * 实收金额
     */
    @FieldAlias("receipts")
    private BigDecimal receipts;
    /**
     * 订单状态
     */
    @FieldAlias("contractStatus")
    private String status;
    /**
     * 发货信息头id,用以判断是否存在发货信息
     */
    @FieldAlias("deliveryHeadId")
    private String deliveryHeadId;
    /**
     * 是否存在发货信息
     */
    private Boolean isExistDeliveryInfo;
    /**
     *已读未读 1：已读 2：未读
     */
    @FieldAlias("isRead")
    private String isRead;

    public void apply(PurchaseContractLineDTO purchaseContractLineDTO) {
        this.itemTotal = purchaseContractLineDTO.getItemTotal();
        this.maxExpectedDelivery = purchaseContractLineDTO.getMaxExpectedDelivery();
        this.minExpectedDelivery = purchaseContractLineDTO.getMinExpectedDelivery();
    }
}
