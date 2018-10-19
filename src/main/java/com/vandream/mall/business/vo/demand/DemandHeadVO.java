package com.vandream.mall.business.vo.demand;

import com.vandream.mall.business.domain.Attachment;
import com.vandream.mall.business.vo.base.BaseVO;
import com.vandream.mall.commons.annotation.FieldAlias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author dingjie
 * @date 2018/3/28
 * @time 20:27
 * Description:
 */
@Data
@Getter
@Setter
public class DemandHeadVO extends BaseVO {
    @FieldAlias("demandAccountId")
    @NotBlank(message = "用户id不能为空")
    private String userId;
    @FieldAlias("demandAccountName")
    @NotBlank(message = "用户名不能为空")
    private String userName;
    @FieldAlias("customerId")
    @NotBlank(message = "当前需方Id不能为空")
    private String customerId;
    /** 企业id */
    @NotBlank(message = "当前企业Id不能为空")
    private String companyId;
    @FieldAlias("customerCode")
    @NotBlank(message = "当前需方编码不能为空")
    private String customerCode;
    @FieldAlias("customerName")
    @NotBlank(message = "当前需方名称不能为空")
    private String customerName;
    @FieldAlias("demandResume")
    @NotBlank(message = "需求简述不能为空")
    private String demandResume;
    @FieldAlias("demandType")
    @NotBlank(message = "需求类型不能为空")
    private String demandType;

    /**附件名称**/
    private String attachmentName;
    /**附件类型**/
    private String attachmentType;
    /**附件路径**/
    private String attachmentPath;
    /**文件类型**/
    private String fileType;
    /**文件大小**/
    private String fileSize;

    private String demandRemark;

    private Object demandDiscuss;
    @FieldAlias("deliveryPeriodStart")
    @NotNull(message = "起始时间不能为空")
    private Long dateTimeStart;
    @FieldAlias("deliveryPeriodEnd")
    @NotNull(message = "结束时间不能为空")
    private Long dateTimeEnd;
    /** 地址记录id*/
    @NotBlank(message = "用户收货地址不能为空")
    private String addressId;
    @FieldAlias("demandContacts")
    @NotBlank(message = "收货人姓名不能为空")
    private String demandContacts;
    @FieldAlias("demandPhone")
    @NotBlank(message = "收货人手机不能为空")
    private String demandPhone;
}
