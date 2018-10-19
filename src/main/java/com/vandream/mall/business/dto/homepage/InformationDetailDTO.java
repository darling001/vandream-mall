package com.vandream.mall.business.dto.homepage;

import com.vandream.mall.business.dto.BaseDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/4/11
 * @time : 19:33
 * Description:
 */
@Data
@Getter
@Setter
public class InformationDetailDTO extends BaseDTO{

    private static final long serialVersionUID = 9183634235958498863L;
    /**
     *详情内容id
     */
    private Integer id;
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
    /**
     *创建时间
     */
    private Long createTime;
    /**
     *修改时间
     */
    private Long modifyTime;
}
