package com.vandream.mall.business.vo.search;


import com.alibaba.fastjson.annotation.JSONField;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Li Jie
 */
@Data
@Getter
@Setter
public class SearchItemLineAggVO implements Serializable {
    private static final long serialVersionUID = -8434890300519001084L;
    @JSONField(name = "ITEM_LINE_ID")
    private String itemLineId;
    @JSONField(name = "SALE_PRICE1")
    private BigDecimal salePrice1;
    @JSONField(name = "SALE_PRICE2")
    private BigDecimal salePrice2;
    @JSONField(name = "SALE_PRICE3")
    private BigDecimal salePrice3;
    @JSONField(name = "SUPPLIER_ID")
    private String supplierId;
    @JSONField(name = "AREA_NAME")
    private String areaName;
    @JSONField(name = "AREA_CODE")
    private String areaCode;
    @JSONField(name="MIN_ORDER_NUM")
    private BigDecimal miniOrderNum;
}
