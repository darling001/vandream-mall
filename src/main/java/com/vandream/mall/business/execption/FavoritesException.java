package com.vandream.mall.business.execption;

import com.vandream.mall.commons.constant.ResultStatusConstant;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/12 19:25
 */
public class FavoritesException extends BusinessException{
    private static final long serialVersionUID = 6210782099832303050L;
    public FavoritesException(ResultStatusConstant resultStatusConstant) {
        super.setStatus(resultStatusConstant.getStatus());
        super.setMessage(resultStatusConstant.getDesc());
        super.setCode(resultStatusConstant.getCode());
    }
}
