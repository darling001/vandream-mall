package com.vandream.mall.business.dto.mallCart;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dingjie
 * @date 2018/3/13
 * @time 16:12
 * Description:
 */
@Data
@Getter
@Setter
public class CmcItemLineDTO {
    @SerializedName("SALE_PRICE1")
    private String salePrice1;

    @SerializedName("SALE_PRICE2")
    private String salePrice2;
    @SerializedName("SALE_PRICE3")
    private String salePrice3;
    @SerializedName("ITEM_PRICE_ID")
    private String itemPriceId;
    @SerializedName("SUPPLIER_NAME")
    private String supplierName;
    @SerializedName("ITEM_LINE_ID")
    private String itemLineId;
    @SerializedName("ITEM_ID")
    private String itemId;
    @SerializedName("SUPPLIER_CODE")
    private String supplierCode;
    @SerializedName("MIN_ORDER_NUM")
    private String minOrderNum;
    @SerializedName("STATUS")
    private String itemStatus;
}
