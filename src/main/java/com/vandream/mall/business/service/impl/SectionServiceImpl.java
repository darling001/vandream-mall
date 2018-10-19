package com.vandream.mall.business.service.impl;

import com.vandream.mall.business.dao.homepage.SectionDAO;
import com.vandream.mall.business.dto.homepage.SectionDTO;
import com.vandream.mall.business.execption.SectionException;
import com.vandream.mall.business.service.SectionService;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/6
 * @time : 21:53
 * Description:
 * 版块业务实现层
 */
@Service(value = "sectionService")
public class SectionServiceImpl implements SectionService {

    //默认版块父id
    private static final int DEFAULT_PARENT_ID = 0;
    private static final Logger logger = LoggerFactory.getLogger(SectionServiceImpl.class);

    @Autowired
    private SectionDAO sectionDAO;

    @Override
    public SectionDTO getCmsBySectionId(String sectionId) throws SectionException {
        if (sectionId == null || "".equals(sectionId)) {
            logger.info("版块id为空，{}", sectionId);
        }
        try {
            List<SectionDTO> sectionEntityList = sectionDAO.getSectionByParentId(DEFAULT_PARENT_ID);
            return sectionEntityList.get(0);
        } catch (Exception e) {
            logger.info("数据库查询出错！,{}", sectionId);
            throw new SectionException(ResultStatusConstant.SECTION_ACCESS_FAIL);
        }

    }

}
