package com.vandream.mall.business.dto.snapshot;

import com.google.gson.annotations.SerializedName;
import com.vandream.mall.business.dto.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA
 *
 * @author : liguoqing
 * @date : 2018/7/30
 * Time: 16:16
 * Description:
 */
@Data
public class ItemSnapshotDTO extends BaseDTO {
    /** 商品id **/
    private String itemId;
    /** 商品编码 **/
    private String  itemCode;
    /** 商品标题 **/
    private String itemShortName;
    /** 商品名称 **/
    private String itemName;
    /** 计量单位 **/
    private String primaryUnitCode;
    /** 区域名称 **/
    private String areaName;
    /** 最小起订量 **/
    private Integer  minOrderNum;
    /** 规格参数 **/
    private String specContents;
    /** 商品描述 **/
    private String descContents;
}
