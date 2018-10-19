package com.vandream.mall.business.execption;

import com.vandream.mall.commons.constant.ResultStatusConstant;

/**
 * @author dingjie
 * @date 2018/10/10
 * @time 10:44
 * @description
 */
public class PublishException extends BusinessException {
    public PublishException(ResultStatusConstant resultStatusConstant) {
        super.setStatus(resultStatusConstant.getStatus());
        super.setMessage(resultStatusConstant.getDesc());
        super.setCode(resultStatusConstant.getCode());
    }
}
