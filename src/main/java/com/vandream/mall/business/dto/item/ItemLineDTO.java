package com.vandream.mall.business.dto.item;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author liuyuhong
 * @date 2018/3/8
 * @time 15:42
 * @description
 */
@Data
public class ItemLineDTO {
    /**
     * 商品行id
     */
    @SerializedName("ITEM_LINE_ID")
    private String itemLineId;
    /**
     * 供货周期
     */
    @SerializedName("LEAD_TIME")
    private String LeadTime;
    /**
     * item_line状态（00-作废;01-下架申请审批中;02-下架;10-待定价;12-定价审批中;30-待上架;31-上架申请审批中;40-已上架）
     */
    @SerializedName("STATUS")
    private String status;
    /**
     * 商品信息ID
     */
    @SerializedName("ITEM_ID")
    private String itemId;
    /**
     * 存货类别
     */
    @SerializedName("MATERIAL_TYPE_CODE")
    private String materialTypeCode;
    /**
     * 供应商ID(与CRM统一)
     */
    @SerializedName("SUPPLIER_ID")
    private String suppLierId;
    /**
     * 是否展示（Y-是;N-否）
     */
    @SerializedName("DISPLAY_FLAG")
    private String displayFlag;
    /**
     * 供应商名称
     */
    @SerializedName("SUPPILER_NAME")
    private String suppilerName;
    /**
     * 最小起订量
     */
    @SerializedName("MIN_ORDER_NUM")
    private BigDecimal minOrderNum;
    /**
     * 供应商代码
     */
    @SerializedName("SUPPILER_CODE")
    private String supplierCode;
    /**
     * 区域Code（tlerp.cmcbs.AreaCode）
     */
    @SerializedName("AREA_CODE")
    private String areaCode;
    /**
     * 地区名称
     */
    @SerializedName("AREA_NAME")
    private String areaName;
    /**
     * 建议售价
     **/
    @SerializedName("SALE_PRICE1")
    private BigDecimal salePrice1;
    /**
     * 高级会员售价
     **/
    @SerializedName("SALE_PRICE2")
    private BigDecimal salePrice2;
    /**
     * VIP会员售价
     **/
    @SerializedName("SALE_PRICE3")
    private BigDecimal salePrice3;

}
