package com.vandream.mall.business.dto.solution;

import com.vandream.mall.business.dto.BaseDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Li Jie
 */
@Data
@Getter
@Setter
public class SolutionSupplierDTO extends BaseDTO {
    private static final long serialVersionUID = 8844750549289233635L;
    private String solutionId;
    private String solutionSupplierId;
    private String supplierId;
    private String supplierCode;
    private Long supplierTime;
    private String supplierPhone;
    private String supplierName;
    private String supplierContacts;
    private String solutionSupplierStatus;
    private String fromType;
    private String fromLineId;
    private String fromLineCode;
    private String createUserId;
    private String createUserName;
    private Long createDate;
    private String modifyUserId;
    private String modifyUserName;
    private Long modifyDate;
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
