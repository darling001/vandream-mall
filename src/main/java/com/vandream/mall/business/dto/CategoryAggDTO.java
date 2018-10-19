package com.vandream.mall.business.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Li Jie
 */
@Data
@Getter
@Setter
public class CategoryAggDTO {
    /**
     * 一级类目
     */
    private String fid;
    private String fname;
    private String flevel;
    /**
     * 二级类目
     */
    private String sid;
    private String sname;
    private String slevel;
    /**
     * 三级类目
     */
    private String tid;
    private String tname;
    private String tlevel;
}