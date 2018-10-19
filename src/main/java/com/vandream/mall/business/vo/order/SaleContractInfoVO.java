package com.vandream.mall.business.vo.order;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dingjie
 * @date 2018/7/12
 * @time 11:08
 * Description:
 */
@Data
@Getter
@Setter
public class SaleContractInfoVO extends BaseVO {

    private String salesContractCode;
    private String itemNameInfo;
}
