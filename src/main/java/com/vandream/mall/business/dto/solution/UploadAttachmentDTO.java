package com.vandream.mall.business.dto.solution;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vandream.mall.business.dto.BaseDTO;
import com.vandream.mall.commons.annotation.FieldAlias;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Li Jie
 */
@Data
@Getter
@Setter
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class UploadAttachmentDTO extends BaseDTO {
    private static final long serialVersionUID = -2013359614026318634L;
    /**
     * 派发单id
     */
    private String solutionId;
    /**
     * 派发单供方解决方案子项ID
     */
    private String solutionSupplierId;
    /**
     * 附件类型
     */
    private String attachmentType;
    /**
     * 业务类型
     */
    private String businessType;
    /**
     * 来源类别(10 商城)
     */
    private String fromType;
    /**
     * 供方方案联系电话
     */
    private String supplierPhone;
    /**
     * 商城账号ID
     */
    @FieldAlias("userId")
    private String accountId;
    /**
     * 商城账号名称
     */
    private String accountName;
    /**
     * 附件列表
     */
    private List<Map<String, Object>> attachmentList;

}
