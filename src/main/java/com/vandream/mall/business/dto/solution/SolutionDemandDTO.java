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
public class SolutionDemandDTO extends BaseDTO {
    private static final long serialVersionUID = -6207038673819694732L;
    private String solutionDemandId;

    private String solutionId;

    private String solutionDemandCode;

    private String demandLineId;

    private String categoryId;

    private String categoryCode;

    private String categoryName;

    private String itemId;

    private String itemLineId;

    private String itemLineCode;

    private String itemType;

    private String itemName;

    private String itemSpecDesc;

    private String demandRemark;

    private String brand;

    private String supplierId;

    private String supplierName;

    private String supplierCode;

    private String standardFlag;

    private String itemPriceId;



    private String fromType;

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

    private String solutionSupplierId;


    private BigDecimal quantity;

    private String unitTypeName;

}