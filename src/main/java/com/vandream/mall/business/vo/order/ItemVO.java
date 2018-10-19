package com.vandream.mall.business.vo.order;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;

/**
 * Created with IntelliJ IDEA
 *
 * @author : liguoqing
 * @date : 2018/7/10
 * Time: 16:25
 * Description:
 */
@Data
public class ItemVO extends BaseVO {
    private String itemId;
    private Integer count;
    
}
