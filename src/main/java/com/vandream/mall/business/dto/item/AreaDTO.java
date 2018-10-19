package com.vandream.mall.business.dto.item;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author liuyuhong
 * @date 2018/3/8
 * @time 14:58
 * @description
 */
@Data
public class AreaDTO implements Comparable<AreaDTO> {
    /** 区域名称 */
    private String areaName;
    /** 区域编码 */
    private String areaCode;
    /** 区域模板 */
    private String areaRangeName;
    /** 区域状态 */
    private String areaStatus;
    /** 供应商id */
    List<String> itemIds = new ArrayList<>();
    /** 是否默认 */
    boolean isDefault;

    public void addItemId(String itemId) {
        this.itemIds.add(itemId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AreaDTO areaDTO = (AreaDTO) o;
        return Objects.equals(areaCode, areaDTO.areaCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), areaCode);
    }

    @Override
    public int compareTo(AreaDTO o) {
        boolean flag = this.getAreaCode().equals(o.getAreaCode());
        if (flag) {
            return 0;
        }
        return 1;
    }

}
