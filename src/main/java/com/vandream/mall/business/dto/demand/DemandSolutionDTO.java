package com.vandream.mall.business.dto.demand;

import com.vandream.mall.business.dto.aus.AttachmentDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dingjie
 * @date 2018/3/29
 * @time 18:22
 * Description:
 */
@Data
@Getter
@Setter
public class DemandSolutionDTO {
    /**
     *需求单id
     */
    private String demandId;

    /**
     * 派发单id
     */
    private String  solutionId;
    /**
     * 方案分类/品种名称
     */
    private String  categoryName;
    /**
     * 方案名称/ 需方整体方案简述
     */
    private String  customerPlanOutline;

    /**
     * 附件名称
     */
    private String fileName;
    /**
     * 附件路径
     */
    private String filePath;
    /**
     * 上传时间
     */
    private Long createDate;
    /**
     * 派发状态(10 待提交 20 审批中 30 审核通过 15 审核驳回 40 供方方案确认 50待需方确认 55 需方驳回60 已确认 90已完成 00已关闭)
     */
    private String solutionStatus;
    /**
     * 附件ID
     */
    private String attachmentId;
}
