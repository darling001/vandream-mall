package com.vandream.mall.business.dto.demand;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dingjie
 * @date 2018/4/2
 * @time 14:48
 * Description:
 */
@Data
@Getter
@Setter
public class DemandLineDetailDTO {
    /**
     * 需求单id
     */
    private String demandId;

    /**
     *商品名称
     */
    private String itemName;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 需求描述
     */
    private String demandRemark;
    /**
     * 计量单位名称
     */
    private String unitTypeName;
    /**
     * 数量
     */
    private String quantity;
    /**
     * 拜访跟踪记录当前ID
     */
    private String demandVisitId;
}
