package com.vandream.mall.business.vo.demand;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author dingjie
 * @date 2018/3/29
 * @time 15:07
 * Description:
 */
@Data
@Setter
@Getter
public class DemandRequestVO extends BaseVO {
    /**
     * 用户id
     */
    @NotBlank(message = "用户ID不能为空")
    private String userId;
    /**
     * 需求单id
     */
    private String demandId;
    /**
     * 企业id
     */
    @NotBlank(message = "企业ID不能为空")
    private String companyId;
    /**
     * 每页条数
     */
    private Integer pageSize;
    /**
     * 页码
     */
    private Integer pageNo = 1;
    /**
     * 需求单状态（“”: 所有，“10”：待受理，“，20|30”：方案制定中，"40":已完成
     */
    private String demandStatus;
    /**
     * 起始时间
     */
    private Long submitStartTime;
    /**
     * 结束时间
     */
    private Long submitEndTime;
    /**
     * 关键字
     */
    private String keyword;
}
