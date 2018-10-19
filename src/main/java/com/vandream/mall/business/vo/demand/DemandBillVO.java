package com.vandream.mall.business.vo.demand;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author dingjie
 * @date 2018/3/29
 * @time 14:53
 * Description:
 */
@Data
@Getter
@Setter
public class DemandBillVO extends BaseVO {
    /**
     * 需求单号
     */
    private String demandCode;
    /**
     * 需求简述
     */
    private String demandResume;
    /**
     * 需求id
     */
    private String demandId;
    /**
     * 销售经理名称
     */
    private String seller;
    /**
     * 创建日期
     */
    private Long createTime;
    /**
     * 期望开始交期
     */
    private Long submitStartTime;
    /**
     * 期望结束交期
     */
    private Long submitEndTime;
    /**
     * 状态
     */
    private String status;
}
