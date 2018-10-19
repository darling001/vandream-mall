package com.vandream.mall.business.vo.delivery;

import com.vandream.mall.business.vo.base.BaseVO;
import com.vandream.mall.commons.annotation.FieldAlias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/4/3
 * Time: 15:53
 * Description: 发货管理列表
 */
@Getter
@Setter
@Data
public class DeliveryVO extends BaseVO {
    private static final long serialVersionUID = 7304349540717702802L;
    /**
     * 发货单id
     */
    @FieldAlias("deliveryHeadId")
    private String deliveryId;
    /**
     * 发货单号
     */
    private String deliveryCode;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 发货单商品清单数量
     */

    private String itemCount;
    /**
     * 发货时间
     */
    @FieldAlias("deliveryDate")
    private Long deliveryDateTime;
    /**
     * 收货货地址
     */
    @FieldAlias("customerSiteAddress")
    private String address;
    /**
     * 物流状态
     */
    @FieldAlias("deliveryStatus")
    private String status;
    /**
     * 已读未读 1：已读 2：未读
     */
    @FieldAlias("isRead")
    private String isRead;
}
