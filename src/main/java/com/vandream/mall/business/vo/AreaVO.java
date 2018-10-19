package com.vandream.mall.business.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author dingjie
 * @date 2018/3/21
 * @time 15:16
 * Description:
 */
@Data
@Setter
@Getter
public class AreaVO {
    private String id;
    private String name;
    private List<AreaVO> cityListEntityList;
}
