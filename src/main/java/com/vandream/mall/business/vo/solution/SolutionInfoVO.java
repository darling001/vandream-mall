package com.vandream.mall.business.vo.solution;

import com.vandream.mall.business.dto.StaffDTO;
import com.vandream.mall.business.vo.base.BaseVO;
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
public class SolutionInfoVO extends BaseVO {
    private static final long serialVersionUID = -7161092335006254481L;
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
     * 派发状态(10 初始 20 待审核 30已采纳 25已驳回 00 已关闭)
     **/
    private String solutionStatus;
    /**
     * 招采经理
     **/
    private String purchaseManagerId;
    /**
     * 招采经理id
     */
    private String purchaser;
    private String contactPhone;
    /**
     * 预计要求交货期起
     */
    private Long deliveryStartDate;
    /**
     * 预计要求交货期止
     */
    private Long deliveryEndDate;
    /**
     * 备注
     **/
    private String remark;
    /**
     * 需求附件名称
     */
    private String demandAttachmentName;
    /**
     * 需求方案附件路径
     */
    private String demandAttachmentPath;

    /**
     * 派发日期
     */
    @FieldAlias("createDate")
    private Long solutionDate;
    /**
     * 方案联系人Id
     */
    private String solutionContactId;
    /**
     * 方案联系人名称
     */
    @FieldAlias("supplierContacts")
    private String solutionContact;
    /**
     * 方案联系人电话
     */
    @FieldAlias("supplierPhone")
    private String solutionPhone;
    /**
     * 供方方案上传时间
     */
    private Long supplierTime;
    private String solutionSupplierId;
    private List<Map<String, Object>> itemList;

    private String address;
    /**
     * 是否包含非标品  Y 是 N 否
     */
    private String standardFlag;
    /**
     * 供方解决方案列表集合
     */
    private List<SolutonAttachmentVO> solutonAttachmentVOS;

    public void apply(StaffDTO staffDTO) {
        if (staffDTO != null) {
            this.purchaser = staffDTO.getUserName();
            this.contactPhone = staffDTO.getTelephone();
        }
    }

}
