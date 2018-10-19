package com.vandream.mall.business.vo.demand;

import com.vandream.mall.commons.annotation.FieldAlias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dingjie
 * @date 2018/3/29
 * @time 11:03
 * Description:
 */
@Data
@Getter
@Setter
public class DemandLineVO {
    /** 商品名称 */
    private String itemName;
    /** 品牌 */
    private String brandName;
    /** 数量 */
    private Integer quantity;
    /** 单位  */
    private String unit;
    /** 补充描述 */
    private String description;
}
