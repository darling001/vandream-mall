package com.vandream.mall.business.vo.homepage;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/4/12
 * @time : 16:52
 * Description:
 */
@Data
@Getter
@Setter
public class InformationDetailVO extends BaseVO {

    private static final long serialVersionUID = -8396112191476517070L;
    /**
     *详细介绍
     */
    private String detailedIntroduction;
    /**
     *子图片地址
     */
    private String imgUrl;
    /**
     *副标题
     */
    private String subTitle;
}
