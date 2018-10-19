package com.vandream.mall.business.service;

import com.vandream.mall.business.dto.homepage.TagInfoDTO;
import com.vandream.mall.business.execption.SectionException;
import com.vandream.mall.business.vo.homepage.TagInfoVO;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/31
 * @time : 9:40
 * Description:
 */
public interface TagInfoService {

    List<TagInfoDTO> getTagInfoList(Integer id ) throws SectionException;
}
