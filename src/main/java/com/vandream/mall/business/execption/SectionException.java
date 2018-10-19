package com.vandream.mall.business.execption;


import com.vandream.mall.commons.constant.ResultStatusConstant;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/14
 * @time : 16:07
 * Description:
 */
public class SectionException extends BusinessException {

    private static final long serialVersionUID = -5761758630712905081L;

    public SectionException(ResultStatusConstant resultStatusConstant) {

        super.setStatus(resultStatusConstant.getStatus());
        super.setMessage(resultStatusConstant.getDesc());
        super.setCode(resultStatusConstant.getCode());
    }
}
