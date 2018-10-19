package com.vandream.mall.business.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author dingjie
 * @date 2018/3/8
 * @time 14:15
 * Description:
 */
@Data
@Setter
@Getter
public class OrderResponseVO {
    private  String orderId;
    private List<String> itemList;
    private  String orderCode;
}
