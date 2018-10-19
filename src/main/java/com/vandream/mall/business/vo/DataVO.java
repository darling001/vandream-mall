package com.vandream.mall.business.vo;

import lombok.Data;

/**
 * @Author Oakley(fangchao)
 * @Date 2018-01-12 11:25
 */
@Data
public class DataVO {
    private String data;
    private String tk;
    private String token;

    public DataVO() {
    }

    public DataVO(String data, String tk) {
        this.data = data;
        this.tk = tk;
    }
}
