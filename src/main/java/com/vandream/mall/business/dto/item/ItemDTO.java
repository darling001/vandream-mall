package com.vandream.mall.business.dto.item;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author liuyuhong
 * @date 2018/3/8
 * @time 13:35
 * @description
 */
@Data
public class ItemDTO {
    /** 商品ID */
    private String itemId;
    /** 商品名称 */
    private String name;
    /** 商品标题 */
    private String title;
    /** 商品编号 */
    private String itemNum;
    /** 计价单位 */
    private String unit;
    /** 普通价格 */
    private BigDecimal price;
    /** 会员价格 */
    private BigDecimal memberPrice;
    /** 最低价格 */
    private BigDecimal minPrice;
    /** 最高价格 */
    private BigDecimal maxPrice;
    /** 最小起订量 */
    private BigDecimal minQuantity;
    /** 是否为标品 */
    private String standardFlag;
    /** 商品供货区域 */
    private List<AreaDTO> area;
    /** 商品计价参数 */
    private List<ItemAttributeDTO> attributes;
    /** 商品图片 */
    private List<ImageDTO> imageList;
    /** 品牌对应的提示语 */
    private List<String> tipList;

}
