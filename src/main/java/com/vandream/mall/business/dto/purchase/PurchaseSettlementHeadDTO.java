package com.vandream.mall.business.dto.purchase;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Li Jie
 */
@Data
@Getter
@Setter
public class PurchaseSettlementHeadDTO {
    private String purchaseSettlementHeadId;

    private String purchaseSettlementCode;

    private String invoiceType;

    private String invoiceCode;

    private String invoiceNo;

    private Date settlementDate;

    private String settlementStatus;

    private Date voucherDate;

    private String voucherStatus;

    private String supplierCompanyId;

    private String supplierId;

    private String supplierCode;

    private String supplierName;

    private String supplierTax;

    private String supplierBankCode;

    private String supplierBankNo;

    private String supplierBankName;

    private String supplierSiteId;

    private String supplierSite;

    private String currencyCode;

    private BigDecimal noTaxAmount;

    private BigDecimal taxAmount;

    private BigDecimal totalAmount;

    private String settlementRemark;

    private String fromType;

    private String fromId;

    private String fromCode;

    private String staffId;

    private String staffCode;

    private String staffName;

    private String departmentId;

    private String departmentCode;

    private String departmentName;

    private String createUserId;

    private String createUserName;

    private Date createDate;

    private String modifyUserId;

    private String modifyUserName;

    private Date modifyDate;

    private String orgId;

    private String groupId;

    private String bookId;

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

    private String extCol11;

    private String extCol12;

    private String extCol13;

    private String extCol14;

    private String extCol15;

    private String extCol16;

    private String extCol17;

    private String extCol18;

    private String extCol19;

    private String extCol20;

    public String getPurchaseSettlementHeadId() {
        return purchaseSettlementHeadId;
    }

    public void setPurchaseSettlementHeadId(String purchaseSettlementHeadId) {
        this.purchaseSettlementHeadId = purchaseSettlementHeadId == null ? null : purchaseSettlementHeadId.trim();
    }

    public String getPurchaseSettlementCode() {
        return purchaseSettlementCode;
    }

    public void setPurchaseSettlementCode(String purchaseSettlementCode) {
        this.purchaseSettlementCode = purchaseSettlementCode == null ? null : purchaseSettlementCode.trim();
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType == null ? null : invoiceType.trim();
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode == null ? null : invoiceCode.trim();
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(String settlementStatus) {
        this.settlementStatus = settlementStatus == null ? null : settlementStatus.trim();
    }

    public Date getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(Date voucherDate) {
        this.voucherDate = voucherDate;
    }

    public String getVoucherStatus() {
        return voucherStatus;
    }

    public void setVoucherStatus(String voucherStatus) {
        this.voucherStatus = voucherStatus == null ? null : voucherStatus.trim();
    }

    public String getSupplierCompanyId() {
        return supplierCompanyId;
    }

    public void setSupplierCompanyId(String supplierCompanyId) {
        this.supplierCompanyId = supplierCompanyId == null ? null : supplierCompanyId.trim();
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getSupplierTax() {
        return supplierTax;
    }

    public void setSupplierTax(String supplierTax) {
        this.supplierTax = supplierTax == null ? null : supplierTax.trim();
    }

    public String getSupplierBankCode() {
        return supplierBankCode;
    }

    public void setSupplierBankCode(String supplierBankCode) {
        this.supplierBankCode = supplierBankCode == null ? null : supplierBankCode.trim();
    }

    public String getSupplierBankNo() {
        return supplierBankNo;
    }

    public void setSupplierBankNo(String supplierBankNo) {
        this.supplierBankNo = supplierBankNo == null ? null : supplierBankNo.trim();
    }

    public String getSupplierBankName() {
        return supplierBankName;
    }

    public void setSupplierBankName(String supplierBankName) {
        this.supplierBankName = supplierBankName == null ? null : supplierBankName.trim();
    }

    public String getSupplierSiteId() {
        return supplierSiteId;
    }

    public void setSupplierSiteId(String supplierSiteId) {
        this.supplierSiteId = supplierSiteId == null ? null : supplierSiteId.trim();
    }

    public String getSupplierSite() {
        return supplierSite;
    }

    public void setSupplierSite(String supplierSite) {
        this.supplierSite = supplierSite == null ? null : supplierSite.trim();
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
    }

    public BigDecimal getNoTaxAmount() {
        return noTaxAmount;
    }

    public void setNoTaxAmount(BigDecimal noTaxAmount) {
        this.noTaxAmount = noTaxAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSettlementRemark() {
        return settlementRemark;
    }

    public void setSettlementRemark(String settlementRemark) {
        this.settlementRemark = settlementRemark == null ? null : settlementRemark.trim();
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType == null ? null : fromType.trim();
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId == null ? null : fromId.trim();
    }

    public String getFromCode() {
        return fromCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode == null ? null : fromCode.trim();
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId == null ? null : staffId.trim();
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode == null ? null : staffCode.trim();
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName == null ? null : staffName.trim();
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode == null ? null : departmentCode.trim();
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(String modifyUserId) {
        this.modifyUserId = modifyUserId == null ? null : modifyUserId.trim();
    }

    public String getModifyUserName() {
        return modifyUserName;
    }

    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName == null ? null : modifyUserName.trim();
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId == null ? null : bookId.trim();
    }

    public String getExtCol1() {
        return extCol1;
    }

    public void setExtCol1(String extCol1) {
        this.extCol1 = extCol1 == null ? null : extCol1.trim();
    }

    public String getExtCol2() {
        return extCol2;
    }

    public void setExtCol2(String extCol2) {
        this.extCol2 = extCol2 == null ? null : extCol2.trim();
    }

    public String getExtCol3() {
        return extCol3;
    }

    public void setExtCol3(String extCol3) {
        this.extCol3 = extCol3 == null ? null : extCol3.trim();
    }

    public String getExtCol4() {
        return extCol4;
    }

    public void setExtCol4(String extCol4) {
        this.extCol4 = extCol4 == null ? null : extCol4.trim();
    }

    public String getExtCol5() {
        return extCol5;
    }

    public void setExtCol5(String extCol5) {
        this.extCol5 = extCol5 == null ? null : extCol5.trim();
    }

    public String getExtCol6() {
        return extCol6;
    }

    public void setExtCol6(String extCol6) {
        this.extCol6 = extCol6 == null ? null : extCol6.trim();
    }

    public String getExtCol7() {
        return extCol7;
    }

    public void setExtCol7(String extCol7) {
        this.extCol7 = extCol7 == null ? null : extCol7.trim();
    }

    public String getExtCol8() {
        return extCol8;
    }

    public void setExtCol8(String extCol8) {
        this.extCol8 = extCol8 == null ? null : extCol8.trim();
    }

    public String getExtCol9() {
        return extCol9;
    }

    public void setExtCol9(String extCol9) {
        this.extCol9 = extCol9 == null ? null : extCol9.trim();
    }

    public String getExtCol10() {
        return extCol10;
    }

    public void setExtCol10(String extCol10) {
        this.extCol10 = extCol10 == null ? null : extCol10.trim();
    }

    public String getExtCol11() {
        return extCol11;
    }

    public void setExtCol11(String extCol11) {
        this.extCol11 = extCol11 == null ? null : extCol11.trim();
    }

    public String getExtCol12() {
        return extCol12;
    }

    public void setExtCol12(String extCol12) {
        this.extCol12 = extCol12 == null ? null : extCol12.trim();
    }

    public String getExtCol13() {
        return extCol13;
    }

    public void setExtCol13(String extCol13) {
        this.extCol13 = extCol13 == null ? null : extCol13.trim();
    }

    public String getExtCol14() {
        return extCol14;
    }

    public void setExtCol14(String extCol14) {
        this.extCol14 = extCol14 == null ? null : extCol14.trim();
    }

    public String getExtCol15() {
        return extCol15;
    }

    public void setExtCol15(String extCol15) {
        this.extCol15 = extCol15 == null ? null : extCol15.trim();
    }

    public String getExtCol16() {
        return extCol16;
    }

    public void setExtCol16(String extCol16) {
        this.extCol16 = extCol16 == null ? null : extCol16.trim();
    }

    public String getExtCol17() {
        return extCol17;
    }

    public void setExtCol17(String extCol17) {
        this.extCol17 = extCol17 == null ? null : extCol17.trim();
    }

    public String getExtCol18() {
        return extCol18;
    }

    public void setExtCol18(String extCol18) {
        this.extCol18 = extCol18 == null ? null : extCol18.trim();
    }

    public String getExtCol19() {
        return extCol19;
    }

    public void setExtCol19(String extCol19) {
        this.extCol19 = extCol19 == null ? null : extCol19.trim();
    }

    public String getExtCol20() {
        return extCol20;
    }

    public void setExtCol20(String extCol20) {
        this.extCol20 = extCol20 == null ? null : extCol20.trim();
    }
}