package com.vandream.mall.business.dto.snapshot;

import com.vandream.mall.business.dto.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *采购订单快照
 * @author : liguoqing
 * @date : 2018/7/24
 * Time: 15:35
 * Description:
 */
@Data
public class ContractSnapshotDTO extends BaseDTO {
    private   String contractHeadId;
    private   String  contractLineId;
    private List<ItemSnapshotVersionDTO>  items;
    /** 订单创建日期 **/
    private Date createDate;
    /** 商品价格 **/
    private BigDecimal price;
}
