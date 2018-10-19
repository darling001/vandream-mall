package com.vandream.mall.business.dto.findContract;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vandream.mall.business.domain.Attachment;
import com.vandream.mall.business.dto.BaseDTO;
import java.util.List;
import lombok.Data;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/4/4 9:34
 */
@Data
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class LogisticsReceiptDTO extends BaseDTO {
    private String deliveryHeadId;
    private String deliveryCode;
    private String receiptRemark;
    private String businessType;
    private List<SubLineList> subLineList;
    private List<Attachment> attachmentList;
}
