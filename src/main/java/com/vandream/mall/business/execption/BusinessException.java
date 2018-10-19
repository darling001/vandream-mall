package com.vandream.mall.business.execption;


import com.vandream.mall.commons.constant.ResultStatusConstant;

import java.lang.reflect.InvocationTargetException;

/**
 * @Author Oakley(fangchao)
 * @Date 2018-01-23 20:26
 */
public class BusinessException extends InvocationTargetException {

    private String status;
    private int code;
    private String message;

    public BusinessException() {
        status = ResultStatusConstant.SERVER_ERROR.getStatus();
        code = ResultStatusConstant.SERVER_ERROR.getCode();
        message = ResultStatusConstant.SERVER_ERROR.getDesc();
    }

    public BusinessException(String status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public BusinessException(ResultStatusConstant resultStatusConstant) {
        status = resultStatusConstant.getStatus();
        code = resultStatusConstant.getCode();
        message = resultStatusConstant.getDesc();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
