package com.vandream.mall.business.vo.buyerContract;

import com.vandream.mall.business.dto.BaseDTO;
import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/30 13:17
 */
@Data
public class ConfirmationVO extends BaseDTO {
    private String salesContractHeadId;
    private String operatorType;
    private String accountId;
    private String accountName;
    private String recordRemark;
}
