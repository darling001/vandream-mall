package com.vandream.mall.business.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/12 17:21
 */
@Data
@Setter
@Getter
public class AddOrderReturnInformationDTO {
    private String orderCode;
    private String orderId;
    private String itemIdName;

}
