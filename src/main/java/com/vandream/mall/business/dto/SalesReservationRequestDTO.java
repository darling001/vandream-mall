package com.vandream.mall.business.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/4/4
 * Time: 18:02
 * Description:
 */
@Data
@Getter
@Setter
public class SalesReservationRequestDTO extends BaseDTO {

    private String userId;

    private String supplierId;

    private String keyword;

    private Long notifyStartTime;

    private Long notifyEndTime;

    private Integer pageSize;

    private Integer pageNo;
}
