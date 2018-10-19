package com.vandream.mall.business.vo.homepage;

import com.vandream.mall.business.vo.base.BaseVO;
import com.vandream.mall.commons.annotation.FieldAlias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/4/10
 * @time : 17:48
 * Description:
 */
@Data
@Getter
@Setter
public class BrandDetailVO extends BaseVO{

    private static final long serialVersionUID = -938386029574489277L;
    /**
     *品牌名称
     */
    @FieldAlias("brandName")
    private String brandName;
    /**
     *LOGO地址
     */
    @FieldAlias("logoUrl")
    private String logoUrl;
    /**
     *网站
     */
    @FieldAlias("website")
    private String website;
    /**
     *成立时间
     */
    @FieldAlias("foundYear")
    private String foundYear;
    /**
     *品牌简介
     */
    @FieldAlias("brandIntroductionNotes")
    private String brandIntroductionNotes;
    /**
     *品牌故事标题
     */
    @FieldAlias("brandStoryTitle")
    private String brandStoryTitle;
    /**
     *品牌故事
     */
    @FieldAlias("brandStoryNotes")
    private String brandStoryNotes;
}
