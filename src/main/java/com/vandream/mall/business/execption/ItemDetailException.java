package com.vandream.mall.business.execption;


import com.vandream.mall.commons.constant.ResultStatusConstant;

/**
 * @author liuyuhong
 * @date 2018/3/12
 * @time 10:49
 * @description
 */
public class ItemDetailException extends BusinessException {
    public ItemDetailException(ResultStatusConstant resultStatusConstant) {
        super.setStatus(resultStatusConstant.getStatus());
        super.setMessage(resultStatusConstant.getDesc());
        super.setCode(resultStatusConstant.getCode());
    }
}
