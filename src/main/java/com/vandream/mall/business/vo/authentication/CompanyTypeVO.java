package com.vandream.mall.business.vo.authentication;

import com.vandream.mall.commons.annotation.FieldAlias;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/20
 * @time : 13:34
 * Description:
 * 企业类型展示对象
 */
public class CompanyTypeVO implements Serializable {

    private static final long serialVersionUID = 7079661317394410149L;

    /**用户类型**/
    @FieldAlias("valueCode")
    private String id;
    /**企业类型名称 **/
    @FieldAlias("valueName")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CompanyTypeVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
