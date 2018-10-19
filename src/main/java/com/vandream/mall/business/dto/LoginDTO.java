package com.vandream.mall.business.dto;

import com.vandream.mall.business.dto.subAccount.MenuDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Li Jie
 */
@Data
@Getter
@Setter
public class LoginDTO extends BaseDTO {
    private static final long serialVersionUID = -2363914161988048711L;
    private String accountId;

    private String accountMobile;

    private String accountRegisterMobile;

    private String accountName;

    private String accountEmail;

    private String accountDuty;

    private String accountStatus;

    private String accountPwd;

    private String accountType;

    private String accountFlag;

    private String pwdFlag;

    private String parentAccountId;
    private String companyId;
    private String supplierId;
    private String companyName;
    private String companyFlag;
    private String companyCode;
    private String applicationStatus;
    private String customerId;
    private String customerLevel;
    private String customerStage;
    private String customerStatus;
    private List<MenuDTO> menuList;


}