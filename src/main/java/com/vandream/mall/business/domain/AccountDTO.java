package com.vandream.mall.business.domain;

import java.io.Serializable;
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
public class AccountDTO implements Serializable{
    private static final long serialVersionUID = -1093347843147039717L;
    private String accountId;

    private String accountMobile;

    private String accountRegisterMobile;

    private String accountName;

    private String accountEmail;

    private String accountDuty;

    private String accountStatus;

    private String accountPwd;

    private String accountType;

    private String accountFlag;

    private String parentAccountId;

    private String companyId;

    private String customerId;

    private String customerClueId;

    private String supplierId;

    private String supplierClueId;

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

    private String extCol11;

    private String extCol12;

    private String extCol13;

    private String extCol14;

    private String extCol15;

    private String extCol16;

    private String extCol17;

    private String extCol18;

    private String extCol19;

    private String extCol20;

    private String pwdFlag;


}