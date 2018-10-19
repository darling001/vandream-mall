package com.vandream.mall.business.dto.findContract;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vandream.mall.business.domain.Attachment;
import com.vandream.mall.business.dto.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/4/3 15:38
 */
@Data
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class VoucherUploadDTO extends BaseDTO{
    private String salesContractHeadId;
    private String fromType;
    private String accountId;
    private String accountName;
    private String businessType;
    private List<Attachment> attachmentList;
}
