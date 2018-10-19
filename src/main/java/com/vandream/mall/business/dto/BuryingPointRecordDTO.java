package com.vandream.mall.business.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author yu yingxing
 */
@Data
@Getter
@Setter
public class BuryingPointRecordDTO extends BaseDTO{

    /** 统计周期轮数 */
    private Integer cycleCode;
    /** 埋点key */
    private String buryingPointKey;
    /** 本日点击数量 */
    private Integer todayClickCount;
    /** 统计的天 */
    private String clickCountDate;
    /** 创建时间 */
    private Date createTime;



}