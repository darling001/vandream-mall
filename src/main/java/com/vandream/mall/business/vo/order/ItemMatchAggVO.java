package com.vandream.mall.business.vo.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : liguoqing
 * @date : 2018/7/10
 * Time: 16:52
 * Description:
 */
@Data
public class ItemMatchAggVO extends BaseVO {
    @JSONField(name = "ITEM_ID")
    private String itemId;
    @JSONField(name = "ITEM_NAME")
    private String itemName;
    @JSONField(name = "cmc_item_line")
    private List<ItemLineAggVO>  itemLineList;
    @JSONField(name = "SPEC_CONTENTS")
    private List<ItemSpecVO>     itemSpecList;
    private  String   specContents;
    
}
