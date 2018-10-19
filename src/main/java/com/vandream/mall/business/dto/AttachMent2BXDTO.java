package com.vandream.mall.business.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author dingjie
 * @date 2018/4/9
 * @time 17:03
 * Description:
 */
@Data
@Setter
@Getter
public class AttachMent2BXDTO extends BaseDTO{
    private String billId;
    private String businessType;
    private String fromType;
    private String accountId;
    private String accountName;
    private List<String> attachmentIdList;
}
