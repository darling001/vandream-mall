package com.vandream.mall.business.dto.demand;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author dingjie
 * @date 2018/3/28
 * @time 14:53
 * Description:
 */
@Data
@Getter
@Setter
public class DemandvisitDTO {
    private String demandVisitId;

    private String demandId;

    private String demandVisitCode;

    private String visitContacts;

    private String visitCustomer;

    private Date visitTime;

    private String visitArea;

    private String visitRemark;

    private String demandCode;

    private String customerId;

    private String customerCode;

    private String customerName;

    private String demandResume;

    private String demandDiscuss;

    private String demandStatus;

    private String demandType;

    private String fromType;

    private String fromId;

    private String fromCode;

    private Date demandTime;

    private String demandAccountId;

    private String demandAccountName;

    private String demandContacts;

    private String demandPhone;

    private String demandContactsRole;

    private String projectId;

    private String projectCode;

    private String projectName;

    private BigDecimal budget;

    private String currencyCode;

    private Date deliveryPeriodStart;

    private Date deliveryPeriodEnd;

    private String platformContactsId;

    private String platformContactsCode;

    private String platformContacts;

    private String customerSiteArea;

    private String platformPhone;

    private String closeReason;

    private Date closeTime;

    private String closeUserId;

    private String closeUserName;

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