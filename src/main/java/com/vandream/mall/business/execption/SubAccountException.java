package com.vandream.mall.business.execption;

import com.vandream.mall.commons.constant.ResultStatusConstant;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/5/25
 * Time: 14:44
 * Description:
 */
public class SubAccountException extends BusinessException {

    private static final long serialVersionUID = -1522573178029655836L;

    public SubAccountException(ResultStatusConstant resultStatusConstant) {
        super.setStatus(resultStatusConstant.getStatus());
        super.setMessage(resultStatusConstant.getDesc());
        super.setCode(resultStatusConstant.getCode());
    }
}
