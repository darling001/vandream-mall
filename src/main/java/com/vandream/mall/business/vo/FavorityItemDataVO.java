package com.vandream.mall.business.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/8 19:15
 */
public class FavorityItemDataVO {
    private List<FavorityItemVO> favorityItemDTOS = new ArrayList<>();
    private List<String> categoryStrs = new ArrayList<>();


    public void addCategory(String categoryStr) {
        this.categoryStrs.add(categoryStr);
    }

    public void addItem( FavorityItemVO favorityItemDTO) {
        this.favorityItemDTOS.add(favorityItemDTO);
    }

    public List<FavorityItemVO> getFavorityItemDTOS() {
        return favorityItemDTOS;
    }

    public void setFavorityItemDTOS(List<FavorityItemVO> favorityItemDTOS) {
        this.favorityItemDTOS = favorityItemDTOS;
    }

    public List<String> getCategoryStrs() {
        return categoryStrs;
    }

    public void setCategoryStrs(List<String> categoryStrs) {
        this.categoryStrs = categoryStrs;
    }
}
