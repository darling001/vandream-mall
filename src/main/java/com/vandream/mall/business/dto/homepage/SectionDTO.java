package com.vandream.mall.business.dto.homepage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/5
 * @time : 13:03
 * Description:
 * 首页版块
 */
public class SectionDTO implements Serializable {

    private static final long serialVersionUID = -6919002992292084640L;

    /**
     * 版块记录id
     **/
    private Integer id;
    /**
     * 版块业务id
     **/
    private String sectionId;
    /**
     * 版块名称
     **/
    private String sectionName;
    /**
     * 图片url
     **/
    private String imageUrl;
    /**
     * 版块标题
     **/
    private String title;
    /**
     * 版块父id
     **/
    private Integer parentId;
    /**
     * 版块url
     **/
    private String url;
    /**
     * 版块层级
     **/
    private Integer level;
    /**
     * 子版块列表
     **/
    private List<SectionDTO> data = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<SectionDTO> getData() {
        return data;
    }

    public void setData(List<SectionDTO> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SectionDTO{" +
                "id=" + id +
                ", sectionId='" + sectionId + '\'' +
                ", sectionName='" + sectionName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", title='" + title + '\'' +
                ", parentId=" + parentId +
                ", url='" + url + '\'' +
                ", level=" + level +
                ", data=" + data +
                '}';
    }
}
