package com.vandream.mall.business.vo.buyerContract.findContract;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/4/9 19:38
 */
@Data
@Setter
@Getter
public class FindContractsVO extends BaseVO{
    private String userId;
    private String companyId;
    private String keyword;
    private Long contractStartDate;
    private Long contractEndDate;
    private int pageSize;
    private int pageNo;
    private String contractStatus;
    private String paymentStatus;
    private String deliveryStatus;
}
