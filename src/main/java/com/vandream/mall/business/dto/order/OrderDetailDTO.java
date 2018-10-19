package com.vandream.mall.business.dto.order;

import com.vandream.mall.business.vo.OrderItemVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: dingjie
 * @Date: 2018/3/8 11:16
 */
@Data
@Getter
@Setter
public class OrderDetailDTO implements Serializable {
    private static final long serialVersionUID = -8579654846927417712L;
    private Long id;
    private Long orderNo;
    private String address;
    private Long leadTime;
    private String userId;
    private String name;
    /**
     * 订单明细列表
     */
    private List<OrderItemVO> orderItemEntityList;
}
