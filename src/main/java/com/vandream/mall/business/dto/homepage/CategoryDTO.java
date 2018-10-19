package com.vandream.mall.business.dto.homepage;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/5
 * @time : 11:11
 * Description:
 * 产品类目
 */
public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = -7632269017524476349L;

    /**类目id**/
    private String categoryId;
    /**类目编码**/
    private String categoryCode;
    /**类目名称**/
    private String categoryName;
    /**父类目id**/
    private String parentCategoryId;
    /**类目等级**/
    private String categoryLevel;
    private String categoryFullCode;
    /**一级类目内容说明**/
    private String tags;
    /**条目是否展开，1=展开，0=不展开**/
    private Integer isShow;
    /**类目图标**/
    private String icon;
    private Integer sort;
    /**
     * URL状态 0=无链接 ，1=有链接
     */
    private Integer urlStatus;
    /**标签列表**/
    private List<String> tagList;
    /**子节点集合**/
    private List<CategoryDTO> categoryList = new ArrayList<>();

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(String categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<CategoryDTO> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryDTO> categoryList) {
        this.categoryList = categoryList;
    }

    public Integer getUrlStatus() {
        return urlStatus;
    }

    public void setUrlStatus(Integer urlStatus) {
        this.urlStatus = urlStatus;
    }

    public List<String> getTagList() {
        return JSON.parseObject(tags,List.class);
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public String getCategoryFullCode() {
        return categoryFullCode;
    }

    public void setCategoryFullCode(String categoryFullCode) {
        this.categoryFullCode = categoryFullCode;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "categoryId='" + categoryId + '\'' +
                ", categoryCode='" + categoryCode + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", parentCategoryId='" + parentCategoryId + '\'' +
                ", categoryLevel='" + categoryLevel + '\'' +
                ", tags='" + tags + '\'' +
                ", isShow=" + isShow +
                ", icon='" + icon + '\'' +
                ", urlStatus=" + urlStatus +
                ", tagList=" + tagList +
                ", categoryList=" + categoryList +
                '}';
    }
}
