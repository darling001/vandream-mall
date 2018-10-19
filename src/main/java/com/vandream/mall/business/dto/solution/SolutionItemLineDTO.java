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
public class SolutionItemLineDTO extends BaseDTO {
    private static final long serialVersionUID = -1265173329539224981L;
    private String solutionItemLineId;

    private String solutionItemId;

    private String solutionSupplierId;

    private String supplierId;

    private String supplierCode;

    private String supplierName;

    private BigDecimal quantity;

    private BigDecimal goodAmount;

    private String taxCodeType;

    private String taxCode;

    private BigDecimal minOrderNum;

    private BigDecimal packageNum;

    private Long leadTime;

    private String areaName;

    private String areaCode;

    private String priceType;

    private BigDecimal purPrice1;

    private BigDecimal purPrice2;

    private BigDecimal purPrice3;

    private BigDecimal purPrice4;

    private BigDecimal purPrice5;

    private BigDecimal salePrice1;

    private BigDecimal salePrice2;

    private BigDecimal salePrice3;

    private BigDecimal salePrice4;

    private BigDecimal salePrice5;

    private String fromType;

    private String fromLineId;

    private String fromLineCode;

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

}