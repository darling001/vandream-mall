package com.vandream.mall.business.dto.subAccount;

import com.vandream.mall.business.dto.BaseDTO;
import lombok.Data;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/5/29
 * Time: 14:15
 * Description:
 */
@Data
public class MenuIdListDTO{

    /** 上级根菜单ID **/
    private String rootId;

    /** 菜单ID **/
    private String menuId;
}
