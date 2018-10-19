package com.vandream.mall.business.vo.order;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : liguoqing
 * @date : 2018/7/10
 * Time: 10:18
 * Description:
 */
@Data
public class OrderItemMatchVO extends BaseVO {
    private BigDecimal  totalAmount;
    private Integer     totalCount;
    private List<ItemMatchVO> list;
}
