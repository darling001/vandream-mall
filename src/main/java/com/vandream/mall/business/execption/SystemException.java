package com.vandream.mall.business.execption;

import com.vandream.mall.commons.constant.ResultStatusConstant;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author Oakley(fangchao)
 * @Date 2018-01-23 19:02
 */
public class SystemException extends InvocationTargetException {

    private String status;
    private int code;
    private String message;

    public SystemException() {
        this.status ="System exception";
        this.code = 500;
        this.message ="系统异常";
    }
    public SystemException(String message) {
        this.status ="System exception";
        this.code = 500;
        this.message =message;
    }
    public SystemException(Throwable cause) {
        super(cause);
    }
    public SystemException(ResultStatusConstant resultStatusConstant) {
        status = resultStatusConstant.getStatus();
        code = resultStatusConstant.getCode();
        message = resultStatusConstant.getDesc();
    }


    public SystemException(String status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
