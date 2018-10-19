package com.vandream.mall.business.execption;

import com.vandream.mall.commons.constant.ResultStatusConstant;

/**
 * @author dingjie
 * @date 2018/3/9
 * @time 14:33
 * Description:采购清单-购物车操作异常
 */
public class MallCartException extends BusinessException {
    public MallCartException(ResultStatusConstant resultStatusConstant) {
        super.setStatus(resultStatusConstant.getStatus());
        super.setMessage(resultStatusConstant.getDesc());
        super.setCode(resultStatusConstant.getCode());
    }
}
