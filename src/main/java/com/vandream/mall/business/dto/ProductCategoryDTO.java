package com.vandream.mall.business.dto;

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
public class ProductCategoryDTO {
    private String categoryId;

    private String categoryCode;

    private String categoryName;

    private String categoryDesc;

    private String parentCategoryId;

    private String categoryFullCode;

    private String categoryFullName;

    private String categoryLevel;

    private Long categorySort;

    private String status;

    private String categoryManager;

    private String effectUserId;

    private Date effectDate;

    private String invalidUserId;

    private Date invalidDate;

    private String specDefineId;

    private String createUserId;

    private Date createDate;

    private String modifyUserId;

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

    private String createUserName;

    private String modifyUserName;


}