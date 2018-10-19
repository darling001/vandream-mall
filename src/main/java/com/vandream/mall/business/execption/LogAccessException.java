package com.vandream.mall.business.execption;

import com.vandream.mall.commons.constant.ResultStatusConstant;

/**
 * Created with IntelliJ IDEA
 * @author : ShiFeng
 * @date : 2018/1/25
 * Time: 20:52
 * Description:
 * 日志业务异常
 */
public class LogAccessException extends BusinessException {

    public LogAccessException(ResultStatusConstant resultStatusConstant) {
        super.setStatus(resultStatusConstant.getStatus());
        super.setMessage(resultStatusConstant.getDesc());
        super.setCode(resultStatusConstant.getCode());
    }

}
