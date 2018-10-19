package com.vandream.mall.business.execption;

import com.vandream.mall.commons.constant.ResultStatusConstant;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/28 20:10
 */
public class FindContractException extends BusinessException{
    private static final long serialVersionUID = 6210782099832303050L;
    public FindContractException(ResultStatusConstant resultStatusConstant) {
        super.setStatus(resultStatusConstant.getStatus());
        super.setMessage(resultStatusConstant.getDesc());
        super.setCode(resultStatusConstant.getCode());
    }
}
