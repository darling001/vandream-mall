package com.vandream.mall.business.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/19
 * @time : 20:34
 * Description:
 * 数据字典
 */
@Data
@Getter
@Setter
public class DataDictionary implements Serializable{

    private static final long serialVersionUID = 1415309436987273386L;
    /**用户类型**/
    private String customerType;
    /**企业类型行id**/
    private String lineId;
    /**企业类型代码**/
    private String code;
    /**企业类型名称**/
    private String typeName;
    /**企业类型状态**/
    private String status;
    /**排序字段**/
    private BigDecimal sort;
    /**默认标记**/
    private String defaultFlag;
    /**系统级值集**/
    private String systemFlag;
    /**备注**/
    private String remark;
    /**父代码**/
    private String parentCode;
    /**创建人**/
    private String createUserId;
    /**创建时间**/
    private String createDate;
    /**修改人**/
    private String modifyUserId;
    /**修改时间**/
    private String modifyDate;
    /**组织机构id**/
    private String orgId;
    /**账套id**/
    private String bookId;
    /**集团id**/
    private String groupId;
    /**业务模块自定义分类**/
    private String customizeCategory;
    /**使用账套**/
    private String useBook;
    /**扩展字段1**/
    private String extCol1;
    /**扩展字段2**/
    private String extCol2;
    /**扩展字段3**/
    private String extCol3;
    /**扩展字段4**/
    private String extCol4;
    /**扩展字段5**/
    private String extCol5;
    /**扩展字段6**/
    private String extCol6;
    /**扩展字段7**/
    private String extCol7;
    /**扩展字段8**/
    private String extCol8;
    /**扩展字段9**/
    private String extCol9;
    /**扩展字段10**/
    private String extCol10;
    /**扩展字段11**/
    private String extCol11;
    /**扩展字段12**/
    private String extCol12;
    /**扩展字段13**/
    private String extCol13;
    /**扩展字段14**/
    private String extCol14;
    /**扩展字段15**/
    private String extCol15;
    /**扩展字段16**/
    private String extCol16;
    /**扩展字段17**/
    private String extCol17;
    /**扩展字段18**/
    private String extCol18;
    /**扩展字段19**/
    private String extCol19;
    /**扩展字段20**/
    private String extCol20;


}
