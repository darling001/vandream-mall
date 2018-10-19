package com.vandream.mall.business.execption;


import com.vandream.mall.commons.constant.ResultStatusConstant;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/12 19:21
 */
public class AddOrderException extends BusinessException{
    private static final long serialVersionUID = -1522573178029655835L;
    public AddOrderException(ResultStatusConstant resultStatusConstant) {
        super.setStatus(resultStatusConstant.getStatus());
        super.setMessage(resultStatusConstant.getDesc());
        super.setCode(resultStatusConstant.getCode());
    }
}
