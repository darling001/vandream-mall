package com.vandream.mall.business.dto.demand;

import com.vandream.mall.business.dto.BaseDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dingjie
 * @date 2018/3/28
 * @time 15:37
 * Description:
 */
@Data
@Getter
@Setter
public class DemandLineDTO extends BaseDTO {
    /** 需求单号 **/
    private String demandId;
    /** 需求单明细ID **/
    private String demandLineId;
    /** 需求单明细号 **/
    private String demandLindCode;
    /** 品种 **/
    private String categoryId;
    /** 品种代码 **/
    private String categoryCode;
    /** 品种名称 **/
    private String categoryName;
    /** 招采经理ID **/
    private String purchaseManagerId;
    /** 招采经理CODE **/
    private String purchaseManagerCode;
    /** 招采经理 **/
    private String purchaseManager;
    /** 商品主ID **/
    private String itemId;
    /** 商品ID **/
    private String itemLineId;
    /** 商品类型 **/
    private String itemType;
    /** 预计商品 **/
    private String itemName;
    /** 商品代码 **/
    private String itemLineCode;
    /** 行业参数 **/
    private String itemSpecDesc;
    /** 计量单位名称 **/
    private String unitTypeName;
    /** 计量单位 **/
    private String unitType;
    /** 品牌 **/
    private String brand;
    /** 需求描述 **/
    private String demandRemark;
    /** 数量 **/
    private String quantity;
    /** 金额**/
    private String goodAmount;
    /** 建议售价 **/
    private String salePrice1;

    private String fromType;
    private String fromLineId;
    private String fromLineCode;
    /** 创建人 **/
    private String createUserId;
    /** 创建人名称 **/
    private String createUserName;
    /** 创建时间 **/
    private Long createDate;
    /** 修改人 **/
    private String modifyUserId;
    /** 修改人名称 **/
    private String modifyUserName;
    /** 修改时间 **/
    private Long modifyDate;
    /** 组织机构ID **/
    private String orgId;
    /** 账套ID **/
    private String bookId;
    /** 集团ID **/
    private String groupId;
    /** 行业参数ID **/
    private String spuId;
    private String demandLineCode;
    private String standardFlag;
    private String solutionSupplierId;

}
