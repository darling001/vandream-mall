package com.vandream.mall.business.dto.homepage;

import com.vandream.mall.business.dto.BaseDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/4/10
 * @time : 17:17
 * Description:
 */
@Data
@Getter
@Setter
public class BrandDTO extends BaseDTO{
    private static final long serialVersionUID = 107005883873141070L;
    /**
     * 品牌ID
     */
    private String brandId;
    /**
     *品牌名称
     */
    private String brandName;
    /**
     *状态（00-作废;01-停用;10-初始;40-启用）
     */
    private String status;
    /**
     *品牌简称
     */
    private String briefName;
    /**
     *网站
     */
    private String website;
    /**
     *类目id
     */
    private String categoryId;
    /**
     *LOGO地址
     */
    private String logoUrl;
    /**
     *品牌介绍
     */
    private String brandIntroduction;
    /**
     *品牌故事标题
     */
    private String brandStoryTitle;
    /**
     *品牌故事
     */
    private String brandStory;
    /**
     *所属国家
     */
    private String belongCountry;
    /**
     *所属厂家
     */
    private String belongCompany;
    /**
     *成立时间
     */
    private String foundYear;
    /**
     *创建人
     */
    private String createUserId;
    /**
     *创建时间
     */
    private Long createDate;
    /**
     *修改人
     */
    private String modifyUserId;
    /**
     *修改时间
     */
    private Long modifyDate;
    /**
     *组织机构ID
     */
    private String orgId;
    /**
     *账套ID
     */
    private String bookId;
    /**
     *集团ID
     */
    private String groupId;
    /**
     *扩展字段1
     */
    private String extCol1;
    /**
     *扩展字段2
     */
    private String extCol2;
    /**
     *扩展字段3
     */
    private String extCol3;
    /**
     *扩展字段4
     */
    private String extCol4;
    /**
     *扩展字段5
     */
    private String extCol5;
    /**
     *扩展字段6
     */
    private String extCol6;
    /**
     *扩展字段7
     */
    private String extCol7;
    /**
     *扩展字段8
     */
    private String extCol8;
    /**
     *扩展字段9
     */
    private String extCol9;
    /**
     *扩展字段10
     */
    private String extCol10;
    /**
     *创建人姓名
     */
    private String createUserName;
    /**
     *修改人姓名
     */
    private String modifyUserName;
    /**
     *品牌介绍描述
     */
    private String brandIntroductionNotes;
    /**
     *品牌故事描述
     */
    private String brandStoryNotes;

}
