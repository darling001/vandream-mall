package com.vandream.mall.business.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/19 16:08
 */
@Data
public class UserInfoVO implements Serializable {
     private static final long serialVersionUID = -6650951757567265300L;
     private String userId;
     private String userName;
     private String phoneNumber;
     private String accountFlag;
     private String companyId;
     private String nickName;

     @Override
     public String toString() {
          return "UserInfoVo{" +
                  "userId='" + userId + '\'' +
                  ", userName='" + userName + '\'' +
                  ", phoneNumber='" + phoneNumber + '\'' +
                  ", accountFlag='" + accountFlag + '\'' +
                  ", companyId='" + companyId + '\'' +
                  ", nickName='" + nickName + '\'' +
                  '}';
     }
}
