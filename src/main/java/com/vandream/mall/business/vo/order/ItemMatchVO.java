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
 * Time: 10:02
 * Description:
 */
@Data
public class ItemMatchVO  extends BaseVO {
    private String itemId;
    private String itemLineId;
    private String itemName;
    private String itemLineCode;
    private BigDecimal unitPrice;
    private Integer count;
    private boolean  matched;
    private List<ItemSpecVO> itemSpecList;
    
}
