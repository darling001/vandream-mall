package com.vandream.mall.business.vo.demand;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author dingjie
 * @date 2018/3/29
 * @time 15:09
 * Description:
 */
@Data
@Setter
@Getter
public class DemandResponseVO extends BaseVO {
    /**
     * 总条数
     */
    private Integer totalSize;
    private Integer pageSize;
    private Integer pageNo;
    /**
     * 需求记录list
     */
    private List<DemandBillVO> demandBillVOList;

    @Override
    public String toString() {
        return "DemandResponseVO{" +
                "totalSize=" + totalSize +
                ", pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                ", demandBillVOList=" + demandBillVOList +
                '}';
    }


}
