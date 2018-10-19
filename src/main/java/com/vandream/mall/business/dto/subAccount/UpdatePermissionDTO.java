package com.vandream.mall.business.dto.subAccount;

import com.vandream.mall.business.dto.BaseDTO;
import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/5/25
 * Time: 16:02
 * Description:
 */
@Data
@Setter
@Getter
public class UpdatePermissionDTO extends BaseDTO{

    /** 来源类别 **/
    private String fromType;

    /** 使用者ID **/
    private String accountId;

    /** 使用者名称 **/
    private String accountName;

    /** 父账号ID **/
    private String parentId;

    /** 父账号名称 **/
    private String parentName;

    /** 角色ID **/
    private String roleId;

    /** 角色名称 **/
    private String roleName;

    /** 登录账号ID **/
    private String loginUserId;

    /** 登录账号名称 **/
    private String loginUserName;

    /** 选中的菜单列表 **/
    private List<MenuIdListDTO> infPermissionInfoList;
}
