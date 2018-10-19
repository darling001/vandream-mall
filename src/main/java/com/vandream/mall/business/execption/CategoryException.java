package com.vandream.mall.business.execption;


import com.vandream.mall.commons.constant.ResultStatusConstant;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/14
 * @time : 16:03
 * Description:
 */
public class CategoryException extends BusinessException {

    private static final long serialVersionUID = -2510436043049744775L;

    public CategoryException(ResultStatusConstant resultStatusConstant) {
        super.setStatus(resultStatusConstant.getStatus());
        super.setCode(resultStatusConstant.getCode());
        super.setMessage(resultStatusConstant.getDesc());
    }
}
