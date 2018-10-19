package com.vandream.mall.business.vo.demand;

import com.vandream.mall.commons.annotation.FieldAlias;
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
public class DemandSolutionVO {
    /**
     * 派发单id
     */
    @FieldAlias("solutionId")
    private String  schemeId;
    /**
     * 方案分类/品种名称
     */
    @FieldAlias("categoryName")
    private String  schemeCategory;
    /**
     * 方案名称
     */
    @FieldAlias("customerPlanOutline")
    private String  schemeName;
    /**
     * 附件路径
     */
    @FieldAlias("filePath")
    private String  schemeAttachment;

    /**
     * 附件名称
     */
    @FieldAlias("fileName")
    private String attachmentName;
    /**
     * 上传时间
     */
    @FieldAlias("createDate")
    private Long uploadTime;
    /**
     * 派发状态(10 待提交 20 审批中 30 审核通过 15 审核驳回 40 供方方案确认 50待需方确认 55 需方驳回60 已确认 90已完成 00已关闭)
     */
    @FieldAlias("solutionStatus")
    private String status;
    /**
     * 附件id
     */
    private String attachmentId;

}
