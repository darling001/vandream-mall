package com.vandream.mall.business.vo.homepage;

import com.vandream.mall.business.vo.base.BaseVO;
import com.vandream.mall.commons.annotation.FieldAlias;

import java.util.Map;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/31
 * @time : 9:33
 * Description:
 */
public class TagInfoVO extends BaseVO {
    private static final long serialVersionUID = 2212751776957508703L;
    /**
     * 标签类型
     */
    @FieldAlias("type")
    private String tag;
    /**
     *标签样式
     */
    @FieldAlias("styleType")
    private String styleType;
    /**
     *标签属性
     */
    @FieldAlias("attr")
    private Map<String,Object> attr;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

}
