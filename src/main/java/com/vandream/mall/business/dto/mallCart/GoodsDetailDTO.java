package com.vandream.mall.business.dto.mallCart;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author dingjie
 * @date 2018/3/8
 * @time 16:36
 * Description:
 */
@Data
@Getter
@Setter
public class GoodsDetailDTO implements Serializable{

    private static final long serialVersionUID = -170987395632340471L;
    /**
     * 购物车 标识id
     */
    private Long id;
    /**
     * 商品itemId
     */
    @JSONField(name = "ITEM_ID")
    @SerializedName("ITEM_ID")
    private String itemId;
    /**
     * 商品行id
     */
    @JSONField(name = "ITEM_LINE_ID")
    @SerializedName("ITEM_LINE_ID")
    private String itemLineId;
    /**
     * 商品编码
     */
    @JSONField(name = "ITEM_CODE")
    @SerializedName("ITEM_CODE")
    private String code;
    /**
     * 商品名称
     */
    @JSONField(name = "ITEM_NAME")
    @SerializedName("ITEM_NAME")
    private  String name;
    /**
     * 商品单价
     */
    private BigDecimal unitPrice;
    /**
     * 商品数量
     */
    private  BigDecimal number;
    /**
     * 商品参数
     */
    @JSONField(name = "SPEC_CONTENTS")
    @SerializedName("SPEC_CONTENTS")
    private String specContents;

    @JSONField(name = "cmc_item_line")
    @SerializedName("cmc_item_line")
    private String cmcItemLine;

    private List<GoodsParams> params;
    @JSONField(name="MIN_ORDER_NUM")
    @SerializedName("MIN_ORDER_NUM")
    private BigDecimal minOrderNum;
    /**
     * 商品行的参考总价
     */
    private BigDecimal subTotal;
    /**
     * 商品url
     */
    private String imagePath;
    /**
     * 商品是否上下架
     */
    private String itemStatus;
    /**
     * 商品是否加入购物车
     */
    private String status;
    /**
     * 商品单位
     */
    private String unitName;

    private String itemLineVersion;
    private Integer  isChangeType;
}
