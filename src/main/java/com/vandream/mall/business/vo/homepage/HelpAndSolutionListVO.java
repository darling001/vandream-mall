package com.vandream.mall.business.vo.homepage;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/4/11
 * @time : 15:56
 * Description:
 * 帮助中心及解决方案DTO
 */
@Data
@Getter
@Setter
public class HelpAndSolutionListVO extends BaseVO {

    private static final long serialVersionUID = -8516740851650754631L;
    /**
     * 信息主键id
     */
    private Integer id;

    /**
     *图片url地址
     */
    private String pictureUrl;
    /**
     *标题
     */
    private String title;
    /**
     *简介
     */
    private String briefIntroduction;


}
