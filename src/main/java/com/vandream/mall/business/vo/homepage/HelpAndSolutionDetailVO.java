package com.vandream.mall.business.vo.homepage;

import com.vandream.mall.business.dto.homepage.InformationDetailDTO;
import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
public class HelpAndSolutionDetailVO extends BaseVO {

    private static final long serialVersionUID = -2647072257885763834L;
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

    /**
     * 详情
     */
    private List<InformationDetailVO> informationDetailList;


}
