package com.vandream.mall.business.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.vandream.mall.business.vo.base.BaseVO;
import com.vandream.mall.business.vo.search.SearchItemLineAggVO;
import com.vandream.mall.business.vo.search.SearchItemPictureVO;
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
public class ComparatorItemVO extends BaseVO {
    private static final long serialVersionUID = 7730816169570338934L;
    @JSONField(name = "ITEM_ID")
    private String itemId;
    @JSONField(name = "ITEM_NAME")
    private String itemName;
    private String pictureUrl;
    @JSONField(name = "cmc_item_line")
    private List<SearchItemLineAggVO> itemLine;
    @JSONField(name = "aus_at_picture_list")
    private List<SearchItemPictureVO> pictureList;
    @JSONField(name = "SPEC_CONTENTS")
    private String specListJson;
    /**
     * 最小起订量区间下线
     */
    private BigDecimal minOrderNum;
    /**
     * 最小起订量区间上线
     */
    private BigDecimal maxOrderNum;
    /**
     * 销售区域聚合字符串
     */
    private String saleArea;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private BigDecimal minMemberPrice;
    private BigDecimal maxMemberPrice;

}
