package com.vandream.mall.business.dto.demand;

import com.vandream.mall.business.dto.BaseDTO;
import com.vandream.mall.business.vo.demand.DemandSolutionVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author dingjie
 * @date 2018/3/30
 * @time 18:27
 * Description:
 */
@Data
@Getter
@Setter
public class DemandDetailDTO extends BaseDTO {
    /** 需求单id **/
    private String demandId;
    /** 需求单号 **/
    private String demandCode;
    /** 需求简述 **/
    private String demandResume;
    /** 需方提出时间 **/
    private Long demandTime;
    /** 预计要求交货期起 **/
    private Long deliveryPeriodStart;
    /** 预计要求交货期止 **/
    private Long deliveryPeriodEnd;
    /** 需求联系人 --收货人**/
    private String demandContacts;
    /** 需求联系人电话--收货电话 **/
    private String demandPhone;
    /** 收货地址 **/
    private String customerSiteArea;
    /** 销售经理**/
    private String saler;
    /** 需求状态 **/
    private String demandStatus;
    /** 附件名称 */
    private String fileName;
    /** 附件路径 */
    private String filePath;
    /** 备注信息 */
    private String demandDiscuss;
    /**
     * 需求详情-采购清单
     *
     */
    private List<DemandLineDetailDTO> demandLineDetailDTOList;
    /** 派发单列表 */
    private List<DemandSolutionVO> demandSolutionVOList;
    /**
     * 拜访日志-版本变更
     */
    private List<Long> logList;
}
