package com.vandream.mall.business.dto.mallCart;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author dingjie
 * @date 2018/3/5
 * @time 10:08
 * Description:
 */
@Data
@Setter
@Getter
public class MallCartDTO {

    /**
     * 购物车的标识id
     */
    private long id;
    /**
     * 商品id
     */
    private String skuId;
    /**
     * 商品行id
     */
    private String itemLineId;
    /**
     * 商品数量
     */
    private BigDecimal itemNo;
    /**
     * 商品单位
     */
    private String itemUnit;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 修改时间
     */
    private Long modifyTime;
    /**
     * 商品是否加入购物车的状态
     */
    private Character itemStatus;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 商品code码
     */
    private String itemCode;
    /**
     * 商品品牌
     */
    private String brand;
    /**
     * 商品是否生成预订单
     */
    private Character itemIsOrdered;
    /**
     * 商品版本
     */
    private String itemLineVersion;


}
