package com.vandream.mall.business.vo.solution;

import com.vandream.mall.business.dto.StaffDTO;
import com.vandream.mall.business.dto.aus.AttachmentDTO;
import com.vandream.mall.business.vo.base.BaseVO;
import com.vandream.mall.commons.annotation.FieldAlias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Li Jie
 */
@Data
@Getter
@Setter
public class SolutionListVO extends BaseVO {
    private static final long serialVersionUID = 4760496246242475640L;
    /**
     * 派发单id
     **/
    private String solutionId;
    /**
     * 派发单号
     **/
    private String solutionCode;
    /**
     * 需求单id
     **/
    private String demandId;
    /**
     * 品种名称
     **/
    @FieldAlias("categoryName")
    private String demandCategory;
    /**
     * 派发状态(10 待上传方案 20 方案待审核 30方案审核通过 25驳回待上传方案 00 方案审核未通过)
     **/
    @FieldAlias("solutionStatus")
    private String status;
    /**
     * 招采经理
     **/
    private String purchaseManagerId;
    private String purchaser;
    private String contactPhone;
    private String attachmentName;
    private String attachmentPath;

    /**
     * 派发日期
     */
    @FieldAlias("createDate")
    private Long dispatchDate;

    private String solutionSupplierId;

    public void apply(StaffDTO staffDTO) {
        if (staffDTO != null) {
            this.purchaser = staffDTO.getUserName();
            this.contactPhone = staffDTO.getTelephone();
        }

    }

    public void apply(AttachmentDTO attachmentDTO) {
        if (attachmentDTO != null) {
            this.attachmentName = attachmentDTO.getFileName();
            this.attachmentPath = attachmentDTO.getFilePath();
        }

    }
}
