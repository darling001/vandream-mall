package com.vandream.mall.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vandream.mall.business.dao.homepage.HelpAndSolutionDAO;
import com.vandream.mall.business.dto.homepage.HelpAndSolutionDTO;
import com.vandream.mall.business.execption.BusinessException;
import com.vandream.mall.business.service.HelpAndSolutionService;
import com.vandream.mall.business.vo.base.DataListVO;
import com.vandream.mall.business.vo.homepage.HelpAndSolutionDetailVO;
import com.vandream.mall.business.vo.homepage.HelpAndSolutionListVO;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.utils.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/4/12
 * @time : 11:21
 * Description:
 */
@Service(value = "helpAndSolutionService")
public class HelpAndSolutionServiceImpl implements HelpAndSolutionService {
    private static final Logger logger = LoggerFactory.getLogger(HelpAndSolutionServiceImpl.class);

    @Resource
    private HelpAndSolutionDAO helpAndSolutionDAO;

    @Override
    public DataListVO findHelpAndSolutionList(Integer pageSize, Integer pageNo, Integer type) throws
            InvocationTargetException {
        if (null == type || 0 == type) {
            logger.info("资讯类型type={}", type);
            throw new BusinessException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        if (null == pageNo || 0 == pageNo) {
            pageNo = 1;
        }
        if (null == pageSize || 0 == pageSize) {
            pageSize = 10;
        }

        PageHelper.startPage(pageNo, pageSize);
        List<HelpAndSolutionDTO> helpAndSolutionDTOList = null;
        List<HelpAndSolutionListVO> helpAndSolutionVOList = null;
        try {
            helpAndSolutionDTOList = helpAndSolutionDAO.findHelpAndSolutionList(type);

            PageInfo<HelpAndSolutionDTO> pageInfo = new PageInfo<>(helpAndSolutionDTOList);

            helpAndSolutionVOList = ObjectUtil.transfer(helpAndSolutionDTOList, HelpAndSolutionListVO.class);

            DataListVO dataListVO = new DataListVO(pageInfo);
            dataListVO.setList(helpAndSolutionVOList);
            return dataListVO;
        } catch (Exception e) {
            throw new BusinessException(ResultStatusConstant.DATA_READ_FAIL);
        }
    }

    @Override
    public HelpAndSolutionDetailVO getInformationDetail(Integer id) throws InvocationTargetException {
        if (null == id || 0 == id) {
            logger.info("入参id={}", id);
            throw new BusinessException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }

        HelpAndSolutionDTO informationDetail = null;
        try {
            informationDetail = helpAndSolutionDAO.getInformationDetail(id);
            HelpAndSolutionDetailVO helpAndSolutionDetailVO = ObjectUtil.transfer(informationDetail,
                    HelpAndSolutionDetailVO.class);
            return helpAndSolutionDetailVO;
        } catch (Exception e) {
            logger.info("数据库查出数据informationDetail={}", JSON.toJSONString(informationDetail));
            throw new BusinessException(ResultStatusConstant.DATA_READ_FAIL);
        }
    }
}
