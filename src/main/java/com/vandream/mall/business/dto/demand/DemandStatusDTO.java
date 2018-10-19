package com.vandream.mall.business.dto.demand;

import com.vandream.mall.business.dto.BaseDTO;
import com.vandream.mall.commons.annotation.FieldAlias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dingjie
 * @date 2018/3/30
 * @time 11:05
 * Description:
 */
@Data
@Getter
@Setter
public class DemandStatusDTO extends BaseDTO {
    /**
     * 派发单id
     */
    private String solutionId;
    /**
     * 派发状态(55 驳回 60 已确认)
     */
    @FieldAlias("status")
    private String solutionStatus;
    /**
     * 附件id
     */
    private String attachmentId;
}
