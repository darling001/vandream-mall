package com.vandream.mall.business.execption;

import com.vandream.mall.commons.constant.ResultStatusConstant;

/**
 * @Author Oakley(fangchao)
 * @Date 2018-01-26 15:47
 */
public class LoginTimeExpireException extends BusinessException {

    public LoginTimeExpireException(ResultStatusConstant resultStatusConstant){
        super.setStatus(resultStatusConstant.getStatus());
        super.setMessage(resultStatusConstant.getDesc());
        super.setCode(resultStatusConstant.getCode());
    }
}
