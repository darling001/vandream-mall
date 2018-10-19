package com.vandream.mall.business.dto.solution;

import com.vandream.mall.business.dto.BaseDTO;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Li Jie
 */
@Data
@Getter
@Setter
public class SolutionItemDTO extends BaseDTO {
    private static final long serialVersionUID = -180714102961921077L;
    private String solutionItemId;

    private String solutionItemCode;

    private String solutionItemStatus;

    private String solutionId;

    private String solutionDemandId;

    private String demandId;

    private String demandLineId;

    private String categoryId;

    private String categoryCode;

    private String categoryName;

    private String spuId;

    private String spuCode;

    private String spuName;

    private String specHeadId;

    private String itemId;

    private String itemCode;

    private String itemDetail;

    private String itemName;

    private String itemShortname;

    private String itemDesc;

    private String trademark;

    private String standardFlag;

    private String primaryUnitCode;

    private Date validStart;

    private Date validEnd;

    private String uomWidth;

    private String uomHeight;

    private String uomLength;

    private String uomDensity;

    private String uomWeight;

    private String manufacturerName;

    private String technicalStandard;

    private String goldenTaxCode;

    private BigDecimal lossRate;

    private String origin;

    private String lockFlag;

    private String specValue1;

    private String specValue2;

    private String specValue3;

    private String specValue4;

    private String specValue5;

    private String specValue6;

    private String specValue7;

    private String specValue8;

    private String specValue9;

    private String specValue10;

    private String specValue11;

    private String specValue12;

    private String specValue13;

    private String specValue14;

    private String specValue15;

    private String specValue16;

    private String specValue17;

    private String specValue18;

    private String specValue19;

    private String specValue20;

    private String fromType;

    private String fromId;

    private String fromCode;

    private String itemSpecDesc;

    private String createUserId;

    private String createUserName;

    private Date createDate;

    private String modifyUserId;

    private String modifyUserName;

    private Date modifyDate;

    private String orgId;

    private String bookId;

    private String groupId;

    private String extCol1;

    private String extCol2;

    private String extCol3;

    private String extCol4;

    private String extCol5;

    private String extCol6;

    private String extCol7;

    private String extCol8;

    private String extCol9;

    private String extCol10;
    /**
     * 商品行id
     */
    private String solutionItemLineId;
    /**
     * 供方派发Id
     */
    private String solutionSupplierId;
    /**
     * 商品数量
     */
    private BigDecimal quantity;
    /**
     * 需求描述
     */
    private String demandRemark;

}