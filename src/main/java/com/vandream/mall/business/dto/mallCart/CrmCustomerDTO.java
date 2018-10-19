package com.vandream.mall.business.dto.mallCart;

import java.io.Serializable;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/9 11:41
 */
public class CrmCustomerDTO implements Serializable {
    private static final long serialVersionUID = -170987395632340479L;
    private String customerId;
    private String companyId;
    private String customerLevel;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCustomerLevel() {
        return customerLevel;
    }

    public void setCustomerLevel(String customerLevel) {
        this.customerLevel = customerLevel;
    }
}
