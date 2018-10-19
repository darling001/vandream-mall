package com.vandream.mall.business.vo.demand;

import com.vandream.mall.business.dto.demand.DemandLineDetailDTO;
import com.vandream.mall.business.vo.base.BaseVO;
import com.vandream.mall.commons.annotation.FieldAlias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author dingjie
 * @date 2018/3/30
 * @time 18:06
 * Description:
 */
@Data
@Setter
@Getter
public class DemandDetailVO extends BaseVO {
    /** 需求单id */
    private String demandId;
    /** 需求方案简述 */
    private String demandResume;
    /** 需求单code */
    private String demandCode;
    /** 需求单提出时间 */
    private Long demandTime;
    /** 约定开始交货时间 */
    private Long deliveryPeriodStart;
    /** 约定结束交货时间 */
    private Long deliveryPeriodEnd;
    /** 联系人 */
    @FieldAlias("demandContacts")
    private String contact;
    /** 联系手机 */
    @FieldAlias("demandPhone")
    private String contactPhone;
    /** 联系电话 */
    private String contactTel;
    /** 收货地址 */
    @FieldAlias("customerSiteArea")
    private String address;
    /** 联系人 */
    private String saler;
    /** 需求单状态 */
    @FieldAlias("demandStatus")
    private String status;
    /** 附件名称 */
    @FieldAlias("fileName")
    private String attachmentName;
    /** 附件路径 */
    @FieldAlias("filePath")
    private String attachmentPath;
    /** 备注信息 */
    @FieldAlias("demandDiscuss")
    private String remark;
    /** 需求派发单列表/采购清单 */
    private List<DemandLineDetailDTO> demandLineDetailDTOList;
    /** 派发单列表 */
    private List<DemandSolutionVO> demandSolutionVOList;
    /** 拜访日志-版本变更 */
    private List<DemandVisitLogVO> logList;
}
