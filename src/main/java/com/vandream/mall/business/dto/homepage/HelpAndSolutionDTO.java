package com.vandream.mall.business.dto.homepage;

import com.vandream.mall.business.dto.BaseDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
public class HelpAndSolutionDTO extends BaseDTO {

    private static final long serialVersionUID = 3921615119469448783L;
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
    /**
     *信息类型
     */
    private Integer fromType;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     *修改时间
     */
    private Long modifyTime;
    /**
     * 信息详情
     */
    private List<InformationDetailDTO> informationDetailList;


}
