package com.vandream.mall.business.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/3/29
 * Time: 20:47
 * Description: 销售合同明细
 */
@Setter
@Getter
@Data
public class ContractLineDTO extends BaseDTO implements Serializable{

    private String itemId;

    private String itemLineId;

    /** 商品名称 **/
    private String itemName;

    /** 品牌 **/
    private String brand;

    /** 数量 **/
    private BigDecimal quantity;

    /** 单位 **/
    private String unit;

    /** 要求交付日期 **/
    private Long deliveryDate;

    /** 备注 **/
    private String itemRemark;

    /** 技術參數文本 **/
    private String technicalParameter;


}
