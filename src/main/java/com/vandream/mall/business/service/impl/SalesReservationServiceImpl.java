package com.vandream.mall.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vandream.mall.business.dao.SalesReservationDAO;
import com.vandream.mall.business.dto.SalesReservationDTO;
import com.vandream.mall.business.dto.SalesReservationInfoDTO;
import com.vandream.mall.business.dto.SalesReservationRequestDTO;
import com.vandream.mall.business.execption.SalesReservationException;
import com.vandream.mall.business.execption.SystemException;
import com.vandream.mall.business.service.SalesReservationService;
import com.vandream.mall.business.vo.SalesListVO;
import com.vandream.mall.business.vo.SalesReservationInfoVO;
import com.vandream.mall.business.vo.SalesReservationRequestVO;
import com.vandream.mall.business.vo.SalesReservationVO;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.utils.ObjectUtil;
import com.vandream.mall.commons.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/3/28
 * Time: 20:12
 * Description: 锁货通知
 */
@Service(value = "salesReservationService")
public class SalesReservationServiceImpl implements SalesReservationService {

    private static final Logger logger = LoggerFactory.getLogger(SalesReservationServiceImpl.class);

    @Autowired
    private SalesReservationDAO salesReservationDAO;

    @Override
    public SalesListVO findSalesReservationList(SalesReservationRequestVO salesReservationRequestVO) throws SalesReservationException {
        //参数校验
        if (StringUtil.isBlank(salesReservationRequestVO.getUserId())) {
            throw new SalesReservationException(ResultStatusConstant.USERID_CAN_NOT_NULL);
        }
        if (StringUtil.isBlank(salesReservationRequestVO.getSupplierId())) {
            throw new SalesReservationException(ResultStatusConstant.BUSINESS_ID_IS_EMPTY);
        }
        if (salesReservationRequestVO.getNotifyEndTime() != null) {
            salesReservationRequestVO.setNotifyEndTime(salesReservationRequestVO.getNotifyEndTime() + (1000 * 60 * 60 * 24));
        }
        PageHelper.startPage(salesReservationRequestVO.getPageNo(), salesReservationRequestVO.getPageSize());
        SalesReservationRequestDTO salesReservationRequestDTO = null;
        try {
            salesReservationRequestDTO = ObjectUtil.transfer(salesReservationRequestVO, SalesReservationRequestDTO.class);
        } catch (SystemException e) {
            logger.error("锁货通知数据转换失败!，{}", e.getMessage());
            throw new SalesReservationException(ResultStatusConstant.PARAM_FORMAT_FAILURE);
        }
        List<SalesReservationDTO> salesReservationList = salesReservationDAO.findSalesReservationList(salesReservationRequestDTO);
        SalesListVO salesListVO = null;
        if (ObjectUtil.isNotEmpty(salesReservationList)) {
            PageInfo<SalesReservationDTO> pageInfo = new PageInfo<>(salesReservationList);
            salesListVO = new SalesListVO(pageInfo);
            salesListVO.setStockList(salesReservationList);
        } else {
            salesListVO = new SalesListVO(salesReservationRequestVO.getPageNo());
        }
//        logger.debug("salesListVO: \n{}", salesListVO);
        return salesListVO;
    }

    @Override
    public SalesReservationVO getSalesReservationInfo(SalesReservationInfoVO salesReservationInfoVO) throws SalesReservationException {
        //参数校验
        if (StringUtil.isBlank(salesReservationInfoVO.getUserId())) {
            throw new SalesReservationException(ResultStatusConstant.USERID_CAN_NOT_NULL);
        }
        if (StringUtil.isBlank(salesReservationInfoVO.getSupplierId()) || StringUtil.isBlank(salesReservationInfoVO.getSalesReservationId())) {
            throw new SalesReservationException(ResultStatusConstant.BUSINESS_ID_IS_EMPTY);
        }
        SalesReservationVO salesReservationVO = null;
        SalesReservationDTO salesReservationDetail = null;
        SalesReservationInfoDTO salesReservationInfoDTO = null;
        try {
            salesReservationInfoDTO = ObjectUtil.transfer(salesReservationInfoVO, SalesReservationInfoDTO.class);
        } catch (SystemException e) {
            logger.error("salesReservationInfoDTO数据转换异常，{}", salesReservationInfoVO, e.getMessage());
            throw new SalesReservationException(ResultStatusConstant.PARAM_FORMAT_FAILURE);
        }

        try {
            salesReservationDetail = salesReservationDAO.getSalesReservationInfo(salesReservationInfoDTO);
            if (ObjectUtil.isEmpty(salesReservationDetail)) {
                salesReservationDetail = new SalesReservationDTO();
            }
            salesReservationVO = ObjectUtil.transfer(salesReservationDetail, SalesReservationVO.class);
        } catch (Exception e) {
            logger.error("获取单条锁货详情数据转换异常，{}", salesReservationDetail, e.getMessage());
            throw new SalesReservationException(ResultStatusConstant.PARAM_FORMAT_FAILURE);
        }
        return salesReservationVO;
    }
}
