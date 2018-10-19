package com.vandream.mall.business.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author liuyuhong
 * @date 2018/4/10
 * @time 20:52
 * @description
 */
@Data
@Getter
@Setter
public class DefaultAddressDTO extends BaseDTO {
    private String companyId;
    private String companySiteId;
    private String siteType;

}
