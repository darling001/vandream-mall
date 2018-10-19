package com.vandream.mall.business.vo.purchase;

import com.vandream.mall.business.dto.OrgExinfo;
import com.vandream.mall.business.dto.StaffDTO;
import com.vandream.mall.business.dto.purchase.PurchaseContractCompanyDTO;
import com.vandream.mall.business.dto.purchase.PurchaseContractRecordDTO;
import com.vandream.mall.business.vo.AttachmentVO;
import com.vandream.mall.business.vo.base.BaseVO;
import com.vandream.mall.commons.annotation.FieldAlias;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Li Jie
 */
@Data
@Getter
@Setter
public class OrderInfoVO extends BaseVO {
    private static final long serialVersionUID = -4728383976622590571L;
    private String purchaseContractHeadId;
    /**
     * 销售订单号
     */
    @FieldAlias("purchaseContractCode")
    private String saleOrderCode;
    /**
     * 订单日期
     */
    @FieldAlias("createDate")
    private Long orderDate;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 甲方
     */
    @FieldAlias("customerName")
    private String purchaser;
    /**
     * 乙方
     */
    @FieldAlias("supplierName")
    private String supplier;
    /**
     * 项目名称
     */
    @FieldAlias("projectName")
    private String projectName;
    /**
     * 收货地址
     */
    @FieldAlias("customerAddress")
    private String address;
    /**
     * 招采经理姓名
     */
    private String staffer;
    /**
     * 招采经理电话
     */
    private String contactTel;
    /**
     * 确认人
     */
    private String confirmer;
    /**
     * 确认时间
     */
    private Long confirmDateTime;
    /**
     * 订单状态
     */
    @FieldAlias("contractStatus")
    private String status;
    /**
     * 合计金额
     */
    private BigDecimal totalAmount;
    /**
     * 单位名称
     */
    private String companyName;
    /**
     * 纳税人识别码
     */
    private String identificationCode;
    /**
     * 单位地址
     */
    private String companyAddress;
    /**
     * 电话
     */
    private String tel;
    /**
     * 开户银行名称
     */
    private String bankName;
    /**
     * 银行账号
     */
    private String cardNumber;

    /**
     * 甲方代表
     */
    @FieldAlias("signPerson")
    private String firstParty;
    /**
     * 乙方代表
     */
    @FieldAlias("customerSignPerson")
    private String secondParty;
    /**
     * 生效时间
     */
    @FieldAlias("signDate")
    private Long takeEffectDateTime;
    /**
     * 订单是否全标品
     */
    private String standardFlag;
    /**
     * 商品信息列表
     */
    private List<OrderItemInfoVO> itemList;
    /**
     * 合同附件列表
     */
    private List<AttachmentVO> contractList;
    /**
     * 附件和承诺列表
     */
    private List<AttachmentVO> attachmentList;
    /**
     * 采购付款明细列表
     */
    private List<PurchaseContractPaymentVO> billingInformation;

    public void apply(PurchaseContractCompanyDTO companyDTO, OrgExinfo vandreamCompanyInfo) {
        if (companyDTO != null) {
            //甲方
            this.purchaser= vandreamCompanyInfo.getOrgName();
            //开票信息
            this.companyName = vandreamCompanyInfo.getOrgName();
            this.identificationCode = vandreamCompanyInfo.getTaxNum();
            this.tel = vandreamCompanyInfo.getTelNum();
            this.bankName = vandreamCompanyInfo.getBranchCode();
            this.cardNumber = vandreamCompanyInfo.getGlBankNum();
            this.companyAddress = vandreamCompanyInfo.getAddress();
        }

    }

    public void apply(PurchaseContractRecordDTO recordDTO) {
        if (recordDTO != null) {
            this.confirmer = recordDTO.getOperatorStaffName();
            this.confirmDateTime = recordDTO.getOperatorDate();
        }

    }

    public void apply(StaffDTO purchaser) {
        if (purchaser != null) {
            this.staffer = purchaser.getUserName();
            this.contactTel = purchaser.getTelephone();
        }

    }
}
