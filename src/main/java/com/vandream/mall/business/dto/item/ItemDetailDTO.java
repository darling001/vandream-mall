package com.vandream.mall.business.dto.item;

import lombok.Data;

import java.util.List;

/**
 * @author liuyuhong
 * @date 2018/5/6
 * @time 16:39
 * @description
 */
@Data
public class ItemDetailDTO {
    /** 商品详情描述图片列表 */
    private List<ImageDTO> imageDTOList;
    /** 商品详情描述列表 */
    private List<ItemDescDTO> itemDescDTOList;
}
