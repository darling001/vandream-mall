package com.vandream.mall.business.vo;

import com.vandream.mall.business.vo.subAccount.MenuVO;
import com.vandream.mall.commons.annotation.FieldAlias;
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
public class LoginVO {
    @FieldAlias("accountId")
    private String userId;
    private String token;
    @FieldAlias("accountName")
    private String userName;
    @FieldAlias("accountMobile")
    private String phoneNumber;
    /**
     * 1、注册未用户；2、待认证用户；3、已认证用户
     */
    private String accountFlag;
    /**
     * 用户昵称
     */
    @FieldAlias("accountMobile")
    private String nickName;
    /**
     * 密码标识
     */
    private String pwdFlag;
    /**
     * 企业ID
     */
    private String companyId;
    /**
     * 企业名称
     */
    private String companyName;
    /**
     * 企业代码
     */
    private String companyCode;
    /**
     * 企业标识 1、供方，2、需方，3、两者都是
     */
    private String companyFlag;
    /**
     * 1、待提交；2、待认证；3、已认证；4、已驳回；
     */
    @FieldAlias("applicationStatus")
    private String companyStatus;
    /**
     * 职务
     */
    private String accountDuty;
    /**
     * 账户类型，00：未明确；10：供方管理员；11：供方子账号；20：需方管理员；21：需方子账号；30
     */
    private String accountType;
    /**
     * 需方ID 不是需方企业id
     */
    private String customerId;
    /**
     * 供方Id 不是供方企业Id
     */
    private String supplierId;
    /**
     * 需方会员等级：00、普通会员 ；10、高级会员；20、VIP会员
     */
    private String customerLevel;
    /**
     * 需方所处阶段；10：潜在客户；11：邀约客户；12：意向客户；13：商务谈判；14：战略协议；15：签约完成
     */
    private String customerStage;
    private List<MenuVO> menuList;

    public LoginVO() {

    }
}
