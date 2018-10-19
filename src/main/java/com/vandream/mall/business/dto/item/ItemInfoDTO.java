package com.vandream.mall.business.dto.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author liuyuhong
 * @date 2018/3/8
 * @time 13:30
 * @description
 */
@Data
@Setter
@Getter
public class ItemInfoDTO {
    /** 商品详情面包屑 */
    private CrumbsDTO crumbs;
    /** 商品详情中商品信息 */
    private ItemDTO itemInfo;
    /** 用户级别 */
    private String customerLevel;
}
