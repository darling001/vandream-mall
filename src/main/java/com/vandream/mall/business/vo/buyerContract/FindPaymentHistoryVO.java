package com.vandream.mall.business.vo.buyerContract;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/4/3 19:52
 */
@Data
@Setter
@Getter
public class FindPaymentHistoryVO extends BaseVO{
    private String contractId;
    private String contractCode;
    private String contractName;
    private List<FindPaymentVO> paymentedHistory;
}
