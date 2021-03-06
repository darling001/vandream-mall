package com.vandream.mall.business.execption;

import com.vandream.mall.commons.constant.ResultStatusConstant;

/**
 * @author liuyuhong
 * @date 2018/3/19
 * @time 17:44
 * @description
 */
public class SmsMsgException extends BusinessException {
    public SmsMsgException(ResultStatusConstant resultStatusConstant) {
        super.setStatus(resultStatusConstant.getStatus());
        super.setMessage(resultStatusConstant.getDesc());
        super.setCode(resultStatusConstant.getCode());
    }
}
