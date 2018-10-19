package com.vandream.mall.business.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/5 20:00
 */
public class CityListVO implements Serializable{
    private static final long serialVersionUID = -8579654846927417712L;
    private Integer id;
    private String name;
    private Integer pid;
    private List<CityListVO> cityListEntityList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public List<CityListVO> getCityListEntityList() {
        return cityListEntityList;
    }

    public void setCityListEntityList(List<CityListVO> cityListEntityList) {
        this.cityListEntityList = cityListEntityList;
    }
}
