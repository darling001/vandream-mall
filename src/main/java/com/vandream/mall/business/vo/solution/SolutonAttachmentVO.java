package com.vandream.mall.business.vo.solution;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dingjie
 * @date 2018/6/5
 * @time 11:38
 * Description:
 */
@Data
@Getter
@Setter
public class SolutonAttachmentVO {
    /**
     * 解决方案附件名称
     */
    private String solutionAttachmentName;
    /**
     * 解决方案附件路径
     */
    private String solutionAttachmentPath;
    /**
     * 上传时间
     */
    private Long createDate;
    /**
     * 附件状态
     */
    private String status;
}
