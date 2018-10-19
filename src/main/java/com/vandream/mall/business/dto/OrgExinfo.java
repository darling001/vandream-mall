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
public class OrgExinfo {
    private String orgExinfoId;

    private String organizationId;
    private String orgName;

    private String orgExinfoType;

    private String flag;

    private String refBookId;

    private String legalAgent;

    private String taxNum;

    private String telNum;

    private String faxNum;

    private String zipCode;

    private String address;

    private String addressKey;

    private String contactPerson;

    private String contactPersonTel;

    private String defaultFlag;

    private String exCol1;

    private String exCol2;

    private String exCol3;

    private String exCol4;

    private String exCol5;

    private String exCol6;

    private String exCol7;

    private String exCol8;

    private String exCol9;

    private String exCol10;

    private String objectId;

    private Date createDate;

    private String createUserId;

    private Date modifyDate;

    private String modifyUserId;

    private String orgGid;

    private String orgId;

    private String groupId;

    private String bookId;
    /**
     * 银行账号
     */
    private String glBankNum;
    /**
     * 开户行
     */
    private String branchCode;


}