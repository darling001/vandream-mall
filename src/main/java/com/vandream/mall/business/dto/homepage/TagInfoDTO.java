package com.vandream.mall.business.dto.homepage;

import com.vandream.mall.commons.annotation.FieldAlias;

import java.util.List;
import java.util.Map;

/**
 * @author wangchengli & ShiFeng
 * @version 1.0
 * @date 2018-03-30
 */
public class TagInfoDTO{

    @FieldAlias("tag")
    private String type;
    @FieldAlias("styleType")
    private String styleType;
    @FieldAlias("attr")
    private Map<String,Object> attr;

    private String attrJson;

    private List<Integer> childTagInfoIds;



    public List<Integer> getChildTagInfoIds() {
        return childTagInfoIds;
    }

    public void setChildTagInfoIds(List<Integer> childTagInfoIds) {
        this.childTagInfoIds = childTagInfoIds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStyleType() {
        return styleType;
    }

    public void setStyleType(String styleType) {
        this.styleType = styleType;
    }

    public Map<String, Object> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, Object> attr) {
        this.attr = attr;
    }

    public String getAttrJson() {
        return attrJson;
    }
    public void setAttrJson(String attrJson) {
        this.attrJson = attrJson;
    }

    @Override
    public String toString() {
        return "TagInfoDTO{" +
                "type='" + type + '\'' +
                ", styleType='" + styleType + '\'' +
                ", attr=" + attr +
                ", attrJson='" + attrJson + '\'' +
                ", childTagInfoIds=" + childTagInfoIds +
                '}';
    }
}
