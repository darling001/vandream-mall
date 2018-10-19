package com.vandream.mall.business.vo;

/**
 * @Author Oakley(fangchao)
 * @Date 2018-01-11 14:54
 */
public class ResponseDataVO {
    private String status;
    private int code;
    private Object data;
    private String token;

    public ResponseDataVO() {
    }

    public ResponseDataVO(String status, int code, String data, String token) {
        this.status = status;
        this.code = code;
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
