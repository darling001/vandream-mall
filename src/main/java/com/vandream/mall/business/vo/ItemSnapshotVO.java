package com.vandream.mall.business.vo;

import com.vandream.mall.business.dto.item.ImageDTO;
import com.vandream.mall.business.dto.item.ItemAttributeDTO;
import com.vandream.mall.business.dto.item.ItemDetailDTO;
import com.vandream.mall.business.dto.mallCart.GoodsParams;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *商品快照VO
 * @author : liguoqing
 * @date : 2018/7/30
 * Time: 14:10
 * Description:
 */
@Data
public class ItemSnapshotVO  implements Serializable {
    private static final long serialVersionUID = -583148513239537621L;
    /** 商品id **/
    private String itemId;
    /** 商品编码 **/
    private String itemCode;
    /** 商品标题 **/
    private String itemTitle;
    /** 商品名称 **/
    private String itemName;
    /** 商品价格 **/
    private BigDecimal itemLinePrice;
    /** 计量单位 **/
    private String unit;
    /** 最小起订量 **/
    private Integer  minOrderNum;
    /** 区域名称 **/
    private String areaName;
    /** 订单日期 **/
    private Date orderDate;
    /** 规格参数 **/
    private List<GoodsParams> specContentList;
    /** 商品图片列表 **/
    private List<ImageDTO> imageList;
    /** 商品描述 **/
    private ItemDetailDTO itemDesc;

    
}
