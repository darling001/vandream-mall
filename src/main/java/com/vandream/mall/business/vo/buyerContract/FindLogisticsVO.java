package com.vandream.mall.business.vo.buyerContract;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/30 9:52
 */
@Data
public class FindLogisticsVO extends BaseVO{
    private Long logisticsTime;
    private String logisticsInfo;

    public Long getLogisticsTime() {
        return logisticsTime;
    }

    public void setLogisticsTime(Long logisticsTime) {
        this.logisticsTime = logisticsTime;
    }

    public String getLogisticsInfo() {
        return logisticsInfo;
    }

    public void setLogisticsInfo(String logisticsInfo) {
        this.logisticsInfo = logisticsInfo;
    }
}
