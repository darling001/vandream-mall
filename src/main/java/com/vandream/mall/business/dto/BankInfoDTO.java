package com.vandream.mall.business.dto;

import java.util.Date;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BankInfoDTO {
    private String glBankId;

    private String glBankCode;

    private String glBankName;

    private String glBankNum;

    private String currencyCode;

    private String bankCode;

    private String branchCode;

    private String receiptFlag;

    private String payFlag;

    private Date startDate;

    private Date endDate;

    private String legalOrgId;

    private String swiftCode;

    private String defaultFlag;

    private String objectId;

    private Date createDate;

    private String createUserId;

    private Date modifyDate;

    private String modifyUserId;

    private String orgGid;

    private String orgId;

    private String groupId;

    private String bookId;


}