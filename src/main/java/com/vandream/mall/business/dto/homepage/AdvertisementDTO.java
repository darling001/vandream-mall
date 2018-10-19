package com.vandream.mall.business.dto.homepage;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/5
 * @time : 12:55
 * Description:
 * 广告实体
 */
public class AdvertisementDTO implements Serializable {

    private static final long serialVersionUID = 7475522714181843670L;

    /**广告id**/
    private Integer id;
    /**所在的版块id**/
    private String sectionCode;
    /**广告编码**/
    private String code;
    /**广告图片存储地址**/
    private String imageUrl;
    /**广告标题**/
    private String title;
    /**广告链接地址**/
    private String url;
    /**广告类型**/
    private Integer type;
    /**状态**/
    private Integer status;
    /**用户id**/
    private Integer userId;
    /**创建时间**/
    private Long createTime;
    /**修改时间**/
    private Long modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Long modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "AdvertisementDTO{" +
                "id=" + id +
                ", sectionCode='" + sectionCode + '\'' +
                ", code='" + code + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
