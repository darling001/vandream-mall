package com.vandream.mall.business.dto.mallCart;

import java.io.Serializable;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/9 11:35
 */
public class UcmAccountDTO implements Serializable {
    private static final long serialVersionUID = -8530639727001239867L;
    private String accountId;
    private String companyId;
    private CrmCustomerDTO crmCustomerDTO;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public CrmCustomerDTO getCrmCustomerDTO() {
        return crmCustomerDTO;
    }

    public void setCrmCustomerDTO(CrmCustomerDTO crmCustomerDTO) {
        this.crmCustomerDTO = crmCustomerDTO;
    }
}
