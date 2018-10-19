package com.vandream.mall.business.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/20 17:41
 */
@Data
public class UserInfoPassDTO extends BaseDTO implements Serializable {
    private static final long serialVersionUID = -23822276462629511L;
    private String accountId;
    private String accountMobile;
}
