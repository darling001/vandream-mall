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
public class PurchaseSettlementLineDTO {
    private String purchaseSettlementLineId;

    private String purchaseSettlementLineCode;

    private String purchaseSettlementHeadId;

    private String fundType;

    private String invoiceItemId;

    private String invoiceItemLineId;

    private String invoiceItemLineCode;

    private String invoiceItemName;

    private BigDecimal invoiceQuantity;

    private String invoiceUnitType;

    private String taxCodeType;

    private String taxCode;

    private BigDecimal invoicePrice;

    private BigDecimal noTaxAmount;

    private BigDecimal taxAmount;

    private BigDecimal totalAmount;

    private String stockItemId;

    private String stockItemLineId;

    private String stockItemLineCode;

    private BigDecimal stockQuantity;

    private String stockUnitType;

    private String settlementLineRemark;

    private String fromType;

    private String fromLineId;

    private String fromLineCode;

    private String purchaseContractHeadId;

    private String purchaseContractLineId;

    private String receiptNoticeHeadId;

    private String receiptNoticeLineId;

    private String instockHeadId;

    private String instockLineId;

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

    public String getPurchaseSettlementLineId() {
        return purchaseSettlementLineId;
    }

    public void setPurchaseSettlementLineId(String purchaseSettlementLineId) {
        this.purchaseSettlementLineId = purchaseSettlementLineId == null ? null :
                purchaseSettlementLineId.trim();
    }

    public String getPurchaseSettlementLineCode() {
        return purchaseSettlementLineCode;
    }

    public void setPurchaseSettlementLineCode(String purchaseSettlementLineCode) {
        this.purchaseSettlementLineCode = purchaseSettlementLineCode == null ? null :
                purchaseSettlementLineCode.trim();
    }

    public String getPurchaseSettlementHeadId() {
        return purchaseSettlementHeadId;
    }

    public void setPurchaseSettlementHeadId(String purchaseSettlementHeadId) {
        this.purchaseSettlementHeadId = purchaseSettlementHeadId == null ? null :
                purchaseSettlementHeadId.trim();
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType == null ? null : fundType.trim();
    }

    public String getInvoiceItemId() {
        return invoiceItemId;
    }

    public void setInvoiceItemId(String invoiceItemId) {
        this.invoiceItemId = invoiceItemId == null ? null : invoiceItemId.trim();
    }

    public String getInvoiceItemLineId() {
        return invoiceItemLineId;
    }

    public void setInvoiceItemLineId(String invoiceItemLineId) {
        this.invoiceItemLineId = invoiceItemLineId == null ? null : invoiceItemLineId.trim();
    }

    public String getInvoiceItemLineCode() {
        return invoiceItemLineCode;
    }

    public void setInvoiceItemLineCode(String invoiceItemLineCode) {
        this.invoiceItemLineCode = invoiceItemLineCode == null ? null : invoiceItemLineCode.trim();
    }

    public String getInvoiceItemName() {
        return invoiceItemName;
    }

    public void setInvoiceItemName(String invoiceItemName) {
        this.invoiceItemName = invoiceItemName == null ? null : invoiceItemName.trim();
    }

    public BigDecimal getInvoiceQuantity() {
        return invoiceQuantity;
    }

    public void setInvoiceQuantity(BigDecimal invoiceQuantity) {
        this.invoiceQuantity = invoiceQuantity;
    }

    public String getInvoiceUnitType() {
        return invoiceUnitType;
    }

    public void setInvoiceUnitType(String invoiceUnitType) {
        this.invoiceUnitType = invoiceUnitType == null ? null : invoiceUnitType.trim();
    }

    public String getTaxCodeType() {
        return taxCodeType;
    }

    public void setTaxCodeType(String taxCodeType) {
        this.taxCodeType = taxCodeType == null ? null : taxCodeType.trim();
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode == null ? null : taxCode.trim();
    }

    public BigDecimal getInvoicePrice() {
        return invoicePrice;
    }

    public void setInvoicePrice(BigDecimal invoicePrice) {
        this.invoicePrice = invoicePrice;
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

    public String getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(String stockItemId) {
        this.stockItemId = stockItemId == null ? null : stockItemId.trim();
    }

    public String getStockItemLineId() {
        return stockItemLineId;
    }

    public void setStockItemLineId(String stockItemLineId) {
        this.stockItemLineId = stockItemLineId == null ? null : stockItemLineId.trim();
    }

    public String getStockItemLineCode() {
        return stockItemLineCode;
    }

    public void setStockItemLineCode(String stockItemLineCode) {
        this.stockItemLineCode = stockItemLineCode == null ? null : stockItemLineCode.trim();
    }

    public BigDecimal getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(BigDecimal stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getStockUnitType() {
        return stockUnitType;
    }

    public void setStockUnitType(String stockUnitType) {
        this.stockUnitType = stockUnitType == null ? null : stockUnitType.trim();
    }

    public String getSettlementLineRemark() {
        return settlementLineRemark;
    }

    public void setSettlementLineRemark(String settlementLineRemark) {
        this.settlementLineRemark = settlementLineRemark == null ? null : settlementLineRemark
                .trim();
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType == null ? null : fromType.trim();
    }

    public String getFromLineId() {
        return fromLineId;
    }

    public void setFromLineId(String fromLineId) {
        this.fromLineId = fromLineId == null ? null : fromLineId.trim();
    }

    public String getFromLineCode() {
        return fromLineCode;
    }

    public void setFromLineCode(String fromLineCode) {
        this.fromLineCode = fromLineCode == null ? null : fromLineCode.trim();
    }

    public String getPurchaseContractHeadId() {
        return purchaseContractHeadId;
    }

    public void setPurchaseContractHeadId(String purchaseContractHeadId) {
        this.purchaseContractHeadId = purchaseContractHeadId == null ? null :
                purchaseContractHeadId.trim();
    }

    public String getPurchaseContractLineId() {
        return purchaseContractLineId;
    }

    public void setPurchaseContractLineId(String purchaseContractLineId) {
        this.purchaseContractLineId = purchaseContractLineId == null ? null :
                purchaseContractLineId.trim();
    }

    public String getReceiptNoticeHeadId() {
        return receiptNoticeHeadId;
    }

    public void setReceiptNoticeHeadId(String receiptNoticeHeadId) {
        this.receiptNoticeHeadId = receiptNoticeHeadId == null ? null : receiptNoticeHeadId.trim();
    }

    public String getReceiptNoticeLineId() {
        return receiptNoticeLineId;
    }

    public void setReceiptNoticeLineId(String receiptNoticeLineId) {
        this.receiptNoticeLineId = receiptNoticeLineId == null ? null : receiptNoticeLineId.trim();
    }

    public String getInstockHeadId() {
        return instockHeadId;
    }

    public void setInstockHeadId(String instockHeadId) {
        this.instockHeadId = instockHeadId == null ? null : instockHeadId.trim();
    }

    public String getInstockLineId() {
        return instockLineId;
    }

    public void setInstockLineId(String instockLineId) {
        this.instockLineId = instockLineId == null ? null : instockLineId.trim();
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