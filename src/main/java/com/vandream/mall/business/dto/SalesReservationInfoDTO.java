package com.vandream.mall.business.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/4/13
 * Time: 21:13
 * Description:
 */
@Getter
@Setter
@Data
public class SalesReservationInfoDTO extends BaseDTO{

    private String userId;

    private String supplierId;

    private String salesReservationId;
}
