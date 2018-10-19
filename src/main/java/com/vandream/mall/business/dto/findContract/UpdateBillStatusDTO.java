package com.vandream.mall.business.dto.findContract;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vandream.mall.business.dto.BaseDTO;
import lombok.Data;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/4/3 15:29
 */
@Data
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)

public class UpdateBillStatusDTO extends BaseDTO{
    private String billId;
    private String billType;
    private String companyId;
    private String companyName;
    private String accountId;
    private String accountName;
    private String operatorType;
}
