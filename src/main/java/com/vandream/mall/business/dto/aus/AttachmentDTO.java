package com.vandream.mall.business.dto.aus;

import com.vandream.mall.business.dto.BaseDTO;
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
public class AttachmentDTO extends BaseDTO {
    private static final long serialVersionUID = -8951381597957603476L;
    private String attachmentId;

    private String organizationId;

    private String billNo;

    private String businessType;

    private String businessFunction;

    private String attachmentType;

    private String reportCode1;

    private String reportCode2;

    private String reportCode3;

    private String reportCode4;

    private String reportCode5;

    private String fileName;

    private String fileType;

    private BigDecimal fileSize;

    private String filePath;

    private String fileStorageName;

    private String fileExternalId;

    private String fileStatus;

    private String createUserId;

    private String createUserName;

    private Date createDate;

    private String modifyUserId;

    private String modifyUserName;

    private Date modifyDate;

    private String orgId;

    private String bookId;

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

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId == null ? null : attachmentId.trim();
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId == null ? null : organizationId.trim();
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public String getBusinessFunction() {
        return businessFunction;
    }

    public void setBusinessFunction(String businessFunction) {
        this.businessFunction = businessFunction == null ? null : businessFunction.trim();
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType == null ? null : attachmentType.trim();
    }

    public String getReportCode1() {
        return reportCode1;
    }

    public void setReportCode1(String reportCode1) {
        this.reportCode1 = reportCode1 == null ? null : reportCode1.trim();
    }

    public String getReportCode2() {
        return reportCode2;
    }

    public void setReportCode2(String reportCode2) {
        this.reportCode2 = reportCode2 == null ? null : reportCode2.trim();
    }

    public String getReportCode3() {
        return reportCode3;
    }

    public void setReportCode3(String reportCode3) {
        this.reportCode3 = reportCode3 == null ? null : reportCode3.trim();
    }

    public String getReportCode4() {
        return reportCode4;
    }

    public void setReportCode4(String reportCode4) {
        this.reportCode4 = reportCode4 == null ? null : reportCode4.trim();
    }

    public String getReportCode5() {
        return reportCode5;
    }

    public void setReportCode5(String reportCode5) {
        this.reportCode5 = reportCode5 == null ? null : reportCode5.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public BigDecimal getFileSize() {
        return fileSize;
    }

    public void setFileSize(BigDecimal fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getFileStorageName() {
        return fileStorageName;
    }

    public void setFileStorageName(String fileStorageName) {
        this.fileStorageName = fileStorageName == null ? null : fileStorageName.trim();
    }

    public String getFileExternalId() {
        return fileExternalId;
    }

    public void setFileExternalId(String fileExternalId) {
        this.fileExternalId = fileExternalId == null ? null : fileExternalId.trim();
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus == null ? null : fileStatus.trim();
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

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId == null ? null : bookId.trim();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
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

    @Override
    public String toString() {
        return "AttachmentDTO{" +
                "attachmentId='" + attachmentId + '\'' +
                ", organizationId='" + organizationId + '\'' +
                ", billNo='" + billNo + '\'' +
                ", businessType='" + businessType + '\'' +
                ", businessFunction='" + businessFunction + '\'' +
                ", attachmentType='" + attachmentType + '\'' +
                ", reportCode1='" + reportCode1 + '\'' +
                ", reportCode2='" + reportCode2 + '\'' +
                ", reportCode3='" + reportCode3 + '\'' +
                ", reportCode4='" + reportCode4 + '\'' +
                ", reportCode5='" + reportCode5 + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", fileSize=" + fileSize +
                ", filePath='" + filePath + '\'' +
                ", fileStorageName='" + fileStorageName + '\'' +
                ", fileExternalId='" + fileExternalId + '\'' +
                ", fileStatus='" + fileStatus + '\'' +
                ", createUserId='" + createUserId + '\'' +
                ", createUserName='" + createUserName + '\'' +
                ", createDate=" + createDate +
                ", modifyUserId='" + modifyUserId + '\'' +
                ", modifyUserName='" + modifyUserName + '\'' +
                ", modifyDate=" + modifyDate +
                ", orgId='" + orgId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", groupId='" + groupId + '\'' +
                ", extCol1='" + extCol1 + '\'' +
                ", extCol2='" + extCol2 + '\'' +
                ", extCol3='" + extCol3 + '\'' +
                ", extCol4='" + extCol4 + '\'' +
                ", extCol5='" + extCol5 + '\'' +
                ", extCol6='" + extCol6 + '\'' +
                ", extCol7='" + extCol7 + '\'' +
                ", extCol8='" + extCol8 + '\'' +
                ", extCol9='" + extCol9 + '\'' +
                ", extCol10='" + extCol10 + '\'' +
                ", extCol11='" + extCol11 + '\'' +
                ", extCol12='" + extCol12 + '\'' +
                ", extCol13='" + extCol13 + '\'' +
                ", extCol14='" + extCol14 + '\'' +
                ", extCol15='" + extCol15 + '\'' +
                ", extCol16='" + extCol16 + '\'' +
                ", extCol17='" + extCol17 + '\'' +
                ", extCol18='" + extCol18 + '\'' +
                ", extCol19='" + extCol19 + '\'' +
                ", extCol20='" + extCol20 + '\'' +
                '}';
    }
}