package com.vandream;

import com.vandream.mall.business.dto.subAccount.MenuDTO;
import com.vandream.mall.commons.utils.JSONUtil;
import org.junit.Test;

/**
 * @author liuyuhong
 * @date 2018/5/28
 * @time 17:45
 * @description
 */
public class CommonTest {
    @Test
    public void test1() {
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setMenuId("1");
        menuDTO.setMenuList(null);
        System.out.println("JSONUtil.toJson(menuDTO) = " + JSONUtil.toJson(menuDTO));
    }
}
