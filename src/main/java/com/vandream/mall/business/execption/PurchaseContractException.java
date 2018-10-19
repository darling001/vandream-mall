package com.vandream.mall.business.execption;

import com.vandream.mall.commons.constant.ResultStatusConstant;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/4/3
 * Time: 20:25
 * Description:
 */
public class PurchaseContractException extends BusinessException {
    public PurchaseContractException(ResultStatusConstant resultStatusConstant) {
        super.setStatus(resultStatusConstant.getStatus());
        super.setMessage(resultStatusConstant.getDesc());
        super.setCode(resultStatusConstant.getCode());
    }
}
