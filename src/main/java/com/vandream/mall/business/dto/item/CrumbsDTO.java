package com.vandream.mall.business.dto.item;

import lombok.Data;
import java.util.Map;

/**
 * @author liuyuhong
 * @date 2018/3/8
 * @time 13:34
 * @description
 */
@Data
public class CrumbsDTO {
    /** 一级类目 */
    private Map<String,String> levelOne;
    /** 二级类目 */
    private Map<String,String> levelTwo;
    /** 三级类目 */
    private Map<String,String> levelThree;

}
