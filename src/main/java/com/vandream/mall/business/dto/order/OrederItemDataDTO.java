package com.vandream.mall.business.dto.order;

import com.vandream.mall.business.dto.BaseDTO;
import com.vandream.mall.commons.annotation.FieldAlias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author dingjie
 * @date 2018/7/10
 * @time 11:18
 * Description:
 */
@Data
@Setter
@Getter
public class OrederItemDataDTO extends BaseDTO {

    @FieldAlias("userId")
    private String operatorId;
    private String customerId;
    private String fromType;
    private String cusSiteCountryCode;
    private String cusSiteCountryName;
    @FieldAlias("provinceCode")
    private String cusSiteRegionCode;
    @FieldAlias("provinceName")
    private String cusSiteRegionName;
    @FieldAlias("cityCode")
    private String cusSiteCityCode;
    @FieldAlias("cityName")
    private String cusSiteCityName;
    @FieldAlias("countyCode")
    private String cusSiteCountyCode;
    @FieldAlias("countyName")
    private String cusSiteCountyName;
    @FieldAlias("address")
    private String customerSiteAddress;
    @FieldAlias("receiver")
    private String customerConsigneetName;
    @FieldAlias("receiverMobile")
    private String customerConsigneetPhone;
    private List<OrderSOMItemLineDTO> itemLineList;
    private String  expectedReceiptDate;
}
