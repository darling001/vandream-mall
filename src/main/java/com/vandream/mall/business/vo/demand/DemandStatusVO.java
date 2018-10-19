package com.vandream.mall.business.vo.demand;

import com.vandream.mall.business.vo.base.BaseVO;
import com.vandream.mall.commons.annotation.FieldAlias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author dingjie
 * @date 2018/3/30
 * @time 11:07
 * Description:
 */
@Data
@Getter
@Setter
public class DemandStatusVO extends BaseVO {
    private static final long serialVersionUID = -8695602140793421626L;
    /**
     * 用户ID
     */
    @FieldAlias("operatorUserId")
    @NotBlank(message = "用户ID不能为空")
    private String userId;
    /**
     * 方案ID
     */
    @FieldAlias("solutionId")
    @NotBlank(message = "当前方案ID不能为空")
    private String solutionId;
    /**
     * 方案状态（ 55 驳回 60 已确认）
     */
    @FieldAlias("solutionStatus")
    @NotBlank(message = "当前方案状态不能为空")
    private String status;
    /**
     * 当前登录的用户名称
     */
    @NotBlank(message = "当前用户名称不能为空")
    @FieldAlias("operatorUserName")
    private String userName;
    /**
     * 附件ID
     */
    @NotBlank(message = "附件id不能为空")
    private String attachmentId;

}
