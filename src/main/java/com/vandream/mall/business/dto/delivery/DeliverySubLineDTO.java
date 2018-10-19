package com.vandream.mall.business.dto.delivery;

import com.vandream.mall.business.dto.BaseDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA
 *
 * @author : liguoqing
 * @date : 2018/4/11
 * Time: 10:25
 * Description:
 */
@Data
@Setter
@Getter
public class DeliverySubLineDTO extends BaseDTO{
    private String  deliveryQuantity;
    private String  fromLineId;
    private String  fromLineCode;
}
