package com.vandream.mall.business.vo.solution;

import com.vandream.mall.business.vo.base.BaseVO;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Li Jie
 */
@Data
@Getter
@Setter
public class UploadAttachmentVO extends BaseVO {
    private static final long serialVersionUID = -4593612082159785094L;
    /**
     * 用户id
     */
    @NotBlank(message = "用户id不能为空")
    private String userId;
    /**
     * 用户名称
     */
    @NotBlank(message = "用户名称不能为空")
    private String userName;
    /**
     * 派发单ID
     */
    @NotBlank(message = "派发单ID不能为空")
    private String solutionId;
    /**
     * 供方派发单解决方案ID
     */
    @NotBlank(message = "供方派发单解决方案ID不能为空")
    private String solutionSupplierId;
    /**
     * 附件名称
     */
    @NotBlank(message = "附件名称不能为空")
    private String attachmentName;
    /**
     * 附件类型
     */
    private String attachmentType;
    /**
     * 附件路径
     */
    @NotBlank(message = "附件路径不能为空")
    private String attachmentPath;
    /**
     * 附件的文件类型
     */
    @NotBlank(message = "附件的文件类型不能为空")
    private String fileType;
    /**
     * 附件的文件大小
     */
    @NotNull(message = "附件的文件大小不能为空")
    private Long fileSize;
    /**
     * 供方方案联系电话
     */
    private String phoneNumber;
}