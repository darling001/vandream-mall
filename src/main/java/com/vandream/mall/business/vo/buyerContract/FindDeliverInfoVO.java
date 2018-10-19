package com.vandream.mall.business.vo.buyerContract;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/29 17:01
 */
@Data
@Setter
@Getter
public class FindDeliverInfoVO extends BaseVO {
    private String id;
    private String code;
    private String itemName;
    private Long deliverTime;
    private String address;
    private String customerName;
    private String customerPhone;
    private String status;
    private String isRead;
    private int goodsNum;
}
