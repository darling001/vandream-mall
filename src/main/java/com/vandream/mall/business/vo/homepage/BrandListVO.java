package com.vandream.mall.business.vo.homepage;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/4/10
 * @time : 17:44
 * Description:
 */
@Data
@Getter
@Setter
public class BrandListVO extends BaseVO {

    private static final long serialVersionUID = 6279845658804199546L;
    /**
     * 品牌ID
     */
    private String brandId;
    /**
     *品牌名称
     */
    private String brandName;
    /**
     *LOGO地址
     */
    private String logoUrl;
    /**
     *品牌介绍
     */
    private String brandIntroductionNotes;

}
