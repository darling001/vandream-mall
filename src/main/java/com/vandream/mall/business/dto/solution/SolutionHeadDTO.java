package com.vandream.mall.business.dto.solution;

import com.vandream.mall.business.dto.BaseDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dingjie
 */
@Data
@Setter
@Getter
public class SolutionHeadDTO extends BaseDTO {

    private static final long serialVersionUID = -4993429623928077282L;
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
     * 品类
     **/
    private String categoryId;
    /**
     * 品种代码
     **/
    private String categoryCode;
    /**
     * 品种名称
     **/
    private String categoryName;
    /**
     * 派发状态(10 待提交 20 审批中 30 审核通过 15 审核驳回 40 供方方案确认 50待需方确认 55 需方驳回60 已确认 90已完成 00已关闭)
     **/
    private String solutionStatus;
    /**
     * 招采经理
     **/
    private String purchaseManagerId;
    /**
     * 需方整体方案简述
     **/
    private String customerPlanOutline;
    /**
     * 需方整体方案详述
     **/
    private String customerPlanDetail;
    /**
     * 预计要求交货期起
     */
    private Long deliveryPeriodStart;
    /**
     * 预计要求交货期止
     */
    private Long deliveryPeriodEnd;
    /**
     * 备注
     **/
    private String remark;

    private String fromType;
    private String fromId;
    private String fromCode;
    /**
     * 创建人
     **/
    private String createUserId;
    /**
     * 创建人名称
     **/
    private String createUserName;
    /**
     * 创建人时间
     **/
    private Long createDate;
    /**
     * 修改人
     **/
    private String modifyUserId;
    /**
     * 修改人名称
     **/
    private String modifyUserName;
    /**
     * 修改时间
     **/
    private Long modifyDate;
    /**
     * 组织机构ID
     **/
    private String orgId;
    /**
     * 账套ID
     **/
    private String bookId;
    /**
     * 集团id
     **/
    private String groupId;
    private String extCol1;

    private String extCol2;

    private String extCol3;

    private String extCol4;

    private String extCol5;

    private String extCol6;

    private String extCol7;

    private String extCol8;

    private String extCol9;

    private String extCol10;

    private String solutionSupplierId;
    private String supplierContacts;
    private Long supplierTime;
    private String supplierPhone;

}
