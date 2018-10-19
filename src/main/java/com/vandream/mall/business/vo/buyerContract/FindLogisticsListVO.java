package com.vandream.mall.business.vo.buyerContract;

import java.util.List;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/30 9:52
 */
@Data
public class FindLogisticsListVO extends BaseVO {
    private String logisticsName;
    private String logisticsCode;
    private List<FindLogisticsVO> logisticsList;

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public List<FindLogisticsVO> getLogisticsList() {
        return logisticsList;
    }

    public void setLogisticsList(List<FindLogisticsVO> logisticsList) {
        this.logisticsList = logisticsList;
    }
}
