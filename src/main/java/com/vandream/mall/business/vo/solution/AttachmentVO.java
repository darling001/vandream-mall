package com.vandream.mall.business.vo.solution;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dingjie
 * @date 2018/3/28
 * @time 14:53
 * Description:
 */
@Data
@Getter
@Setter
public class AttachmentVO {

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
  private Double fileSize;
  private String filePath;
  private String fileStorageName;
  private String fileExternalId;
  private String fileStatus;
  private String createUserId;
  private String createUserName;
  private Long createDate;
  private String modifyUserId;
  private String modifyUserName;
  private Long modifyDate;
  private String orgId;
  private String bookId;
  private String groupId;



}
