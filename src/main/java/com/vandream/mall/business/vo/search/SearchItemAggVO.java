package com.vandream.mall.business.vo.search;


import com.alibaba.fastjson.annotation.JSONField;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Li Jie
 */
@Data
@Getter
@Setter
public class SearchItemAggVO implements Serializable {
    private static final long serialVersionUID = -3651952303580793477L;
    @JSONField(name = "CATEGORY_ID")
    private String categoryId;
    @JSONField(name = "ITEM_ID")
    private String itemId;
    @JSONField(name = "ITEM_CODE")
    private String itemCode;
    @JSONField(name = "ITEM_NAME")
    private String itemName;
    @JSONField(name = "ITEM_SHORT_NAME")
    private String itemShortName;
    @JSONField(name = "PRIMARY_UNIT_CODE")
    private String primaryUnitCode;
    private String unitName;
    @JSONField(name = "SECONDARY_UNIT_CODE")
    private String secondaryUnitCode;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private BigDecimal minMemberPrice;
    private BigDecimal maxMemberPrice;
    @JSONField(name = "cmc_item_line")
    private List<SearchItemLineAggVO> itemLine;
    @JSONField(name = "SPEC_CONTENTS")
    private String specListJson;
    private List<SearchItemSpecVO> specList;
    @JSONField(name = "aus_at_picture_list")
    private List<SearchItemPictureVO> pictureList;
    private String pictureUrl;

    private String supplierId;

    /**
     * SPU_ID
     */
    @JSONField(name = "SPU_ID")
    private String spuId;

}
