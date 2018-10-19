package com.vandream.mall.business.vo.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA
 *
 * @author : liguoqing
 * @date : 2018/7/10
 * Time: 16:43
 * Description:
 */
@Data
public class ItemLineAggVO extends BaseVO {
    @JSONField(name = "ITEM_LINE_CODE")
    private String  itemLineCode;
    @JSONField(name = "ITEM_LINE_ID")
    private String itemLineId;
    @JSONField(name = "SALE_PRICE1")
    private BigDecimal salePrice1;
    @JSONField(name = "SALE_PRICE2")
    private BigDecimal salePrice2;
    @JSONField(name = "SALE_PRICE3")
    private BigDecimal salePrice3;
    private String    areaRangeCode;
}
