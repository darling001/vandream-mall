package com.vandream.mall.business.service;

import com.vandream.mall.business.dto.homepage.SectionDTO;
import com.vandream.mall.business.execption.SectionException;


/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/5
 * @time : 13:10
 * Description:
 * 版块业务接口
 */
public interface SectionService {
    /**
     * 获取版块信息
     * @param sectionId
     * @return
     */
    SectionDTO getCmsBySectionId(String sectionId) throws SectionException;
}
