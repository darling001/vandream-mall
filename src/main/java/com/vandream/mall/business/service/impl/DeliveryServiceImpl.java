package com.vandream.mall.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vandream.mall.business.constant.AttachmentType;
import com.vandream.mall.business.constant.BusinessType;
import com.vandream.mall.business.dao.*;
import com.vandream.mall.business.dto.BxApiResult;
import com.vandream.mall.business.dto.aus.AttachmentDTO;
import com.vandream.mall.business.dto.delivery.*;
import com.vandream.mall.business.dto.delivery.notice.DeliveryNoticeDetailDTO;
import com.vandream.mall.business.dto.delivery.notice.DeliveryNoticeGoodsDTO;
import com.vandream.mall.business.dto.delivery.notice.DeliveryNoticeListDTO;
import com.vandream.mall.business.execption.DeliveryException;
import com.vandream.mall.business.execption.SystemException;
import com.vandream.mall.business.service.DeliveryService;
import com.vandream.mall.business.service.FindContractService;
import com.vandream.mall.business.service.ValueSetService;
import com.vandream.mall.business.vo.AttachmentVO;
import com.vandream.mall.business.vo.DeliveryDetailInfoVO;
import com.vandream.mall.business.vo.LogisticsVO;
import com.vandream.mall.business.vo.delivery.*;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.service.ApiExecutorBxService;
import com.vandream.mall.commons.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/4/3
 * Time: 15:38
 * Description: 发货管理
 */
@Service("deliveryService")
public class DeliveryServiceImpl implements DeliveryService {

    Logger logger = LoggerFactory.getLogger(DeliveryServiceImpl.class);

    private static final String VALUE_SET_CODE_UNIT_TYPE = "tlerp.ausbs.unitType";

    @Autowired
    private DeliveryDAO deliveryDAO;

    @Autowired
    private FindLogisticsDAO findLogisticsDAO;

    @Autowired
    private AttachmentDAO attachmentDAO;

    @Autowired
    private PurchaseContractDAO purchaseContractDAO;

    @Autowired
    private ApiExecutorBxService apiExecutorBxService;

    @Autowired
    private ValueSetService valueSetService;

    @Autowired
    private FindContractService findContractService;

    @Autowired
    private BillReadDAO billReadDAO;

    @Override
    public DeliveryListVO findDeliveryNoticeList(DeliveryRequestVO request) throws DeliveryException {
        if (request.getEndTime() != null) {
            request.setEndTime(request.getEndTime() + (1000 * 60 * 60 * 24));
        }
        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        DeliveryRequestDTO requestDTO = null;
        try {
            requestDTO = ObjectUtil.transfer(request, DeliveryRequestDTO.class);
        } catch (SystemException e) {
            logger.error("获取发货管理列表数据转化失败，{}", request, e.getMessage());
            throw new DeliveryException(ResultStatusConstant.PARAM_FORMAT_FAILURE);
        }
        List<DeliveryNoticeListDTO> deliveryNoticeList = deliveryDAO.findDeliveryNoticeList(requestDTO);
        DeliveryListVO deliveryListVO = null;
        if (ObjectUtil.isNotEmpty(deliveryNoticeList)) {
            PageInfo<DeliveryNoticeListDTO> pageInfo = new PageInfo<>(deliveryNoticeList);
            deliveryListVO = new DeliveryListVO(pageInfo);
            deliveryListVO.setNoticeList(deliveryNoticeList);
        } else {
            deliveryListVO = new DeliveryListVO(requestDTO.getPageNo());
        }
        logger.debug("deliveryListVO: \n{}", deliveryListVO);
        return deliveryListVO;
    }

    @Override
    public DeliveryNoticeDetailVO getSupplierDeliveryNoticeInfo(String userId, String deliveryNoticeId) throws DeliveryException {
        if (StringUtil.isBlank(userId)) {
            throw new DeliveryException(ResultStatusConstant.USERID_CAN_NOT_NULL);
        }
        if (StringUtil.isBlank(deliveryNoticeId)) {
            throw new DeliveryException(ResultStatusConstant.BUSINESS_ID_IS_EMPTY);
        }
        List<DeliveryNoticeGoodsVO> deliveryNoticeGoodsVOList = null;
        DeliveryNoticeDetailVO deliveryDetailVO = null;
        DeliveryNoticeDetailDTO deliveryDetailDTO = deliveryDAO.getSupplierDeliveryNoticeInfo(deliveryNoticeId);
        if (ObjectUtil.isNotEmpty(deliveryDetailDTO)) {
            try {
                deliveryDetailVO = ObjectUtil.transfer(deliveryDetailDTO, DeliveryNoticeDetailVO.class);
            } catch (Exception e) {
                logger.error("获取供方发货通知单详情数据转化失败，{}", deliveryDetailDTO, e.getMessage());
                throw new DeliveryException(ResultStatusConstant.PARAM_FORMAT_FAILURE);
            }
        } else {
            deliveryDetailVO = new DeliveryNoticeDetailVO();
        }

        List<DeliveryNoticeGoodsDTO> deliveryNoticeGoodsDTOList = purchaseContractDAO.getDeliveryNoticeGoodsInfo(deliveryNoticeId);
        if (ObjectUtil.isNotEmpty(deliveryNoticeGoodsDTOList)) {
            try {
                Map<String, String> unitTypeMap = valueSetService.getItemUnitTypeNameMap();
                for (DeliveryNoticeGoodsDTO deliveryNoticeGoodsDTO : deliveryNoticeGoodsDTOList) {
                    if (ObjectUtil.isNotEmpty(unitTypeMap) && unitTypeMap.get(deliveryNoticeGoodsDTO.getUnit()) != null) {
                        deliveryNoticeGoodsDTO.setUnit(unitTypeMap.get(deliveryNoticeGoodsDTO.getUnit()));
                    }
                }
                deliveryNoticeGoodsVOList = ObjectUtil.transfer(deliveryNoticeGoodsDTOList, DeliveryNoticeGoodsVO.class);
            } catch (Exception e) {
                logger.error("获取供方发货通知单详情数据转化失败，{}", deliveryNoticeGoodsDTOList, e.getMessage());
                throw new DeliveryException(ResultStatusConstant.PARAM_FORMAT_FAILURE);
            }
        } else {
            deliveryNoticeGoodsVOList = new ArrayList<DeliveryNoticeGoodsVO>();
        }
        deliveryDetailVO.setItemList(deliveryNoticeGoodsVOList);
        return deliveryDetailVO;
    }

    @Override
    public DeliveryDetailVO getDeliveryItem(String userId, String deliveryNoticeId, String supplierId) throws DeliveryException {
        if (StringUtil.isBlank(userId)) {
            throw new DeliveryException(ResultStatusConstant.USERID_CAN_NOT_NULL);
        }
        if (StringUtil.isBlank(deliveryNoticeId) || StringUtil.isBlank(supplierId)) {
            throw new DeliveryException(ResultStatusConstant.BUSINESS_ID_IS_EMPTY);
        }
        DeliveryDetailVO deliveryDetailVO = null;
        List<DeliveryGoodsVO> deliveryGoodsLists = null;
        DeliveryDetailDTO deliveryDetailDTO = deliveryDAO.getDeliveryItem(deliveryNoticeId);
        if (deliveryDetailDTO != null) {
            List<DeliveryGoodsDTO> deliveryGoodsList = purchaseContractDAO.getDeliveryGoodsInfo(deliveryNoticeId);

            if (ObjectUtil.isNotEmpty(deliveryGoodsList)) {
                Iterator<DeliveryGoodsDTO> it = deliveryGoodsList.iterator();
                BigDecimal historyQuantity = null;
                BigDecimal noticeQuantity = null;
                while (it.hasNext()) {
                    DeliveryGoodsDTO deliveryGoodsItem = it.next();
                    if (deliveryGoodsItem.getHistoryQuantity() != null) {
                        historyQuantity = deliveryGoodsItem.getHistoryQuantity();
                    } else {
                        historyQuantity = new BigDecimal(0);
                    }
                    if (deliveryGoodsItem.getNoticeQuantity() != null) {
                        noticeQuantity = deliveryGoodsItem.getNoticeQuantity();
                    } else {
                        noticeQuantity = new BigDecimal(0);
                    }
                    if ((historyQuantity.compareTo(noticeQuantity)) >= 0) {
                        it.remove();
                    }
                }
            }

            if (ObjectUtil.isNotEmpty(deliveryGoodsList)) {
                try {
                    deliveryGoodsLists = ObjectUtil.transfer(deliveryGoodsList, DeliveryGoodsVO.class);
                    Map<String, String> unitMap = valueSetService.getItemUnitTypeNameMap();
                    for (DeliveryGoodsVO deliveryGoodsVO : deliveryGoodsLists) {
                        deliveryGoodsVO.setUnit(unitMap.get(deliveryGoodsVO.getUnit()));
                    }
                } catch (SystemException e) {
                    logger.error("获取供方发货通知单详情中的商品信息数据转化失败，{}", deliveryGoodsList, e.getMessage());
                    throw new DeliveryException(ResultStatusConstant.PARAM_FORMAT_FAILURE);
                }
            } else {
                deliveryGoodsLists = new ArrayList<>();
            }
            deliveryDetailDTO.setItemList(deliveryGoodsLists);
            try {
                deliveryDetailVO = ObjectUtil.transfer(deliveryDetailDTO, DeliveryDetailVO.class);

            } catch (Exception e) {
                logger.error("待发货订单发货商品详情数据转化失败，{}", deliveryDetailDTO, e.getMessage());
                throw new DeliveryException(ResultStatusConstant.PARAM_FORMAT_FAILURE);
            }
        } else {
            deliveryDetailVO = new DeliveryDetailVO();
        }
        logger.debug("deliveryListVO: \n{}", deliveryDetailVO);
        return deliveryDetailVO;
    }

    @Override
    public DeliveryDetailInfoVO getDeliveryInfo(String userId, String userName, String deliveryHeadId, String deliveryNoticeId, String companyId, String companyName) throws DeliveryException {
        Integer count = billReadDAO.selectBillReadCount(userId, deliveryHeadId);
        if (count == 0) {
            try {
                findContractService.updateBillStatus(deliveryHeadId, "20", companyId, companyName, userId, userName, "10");
            } catch (Exception e) {
                logger.error("修改单据状态失败", e.getMessage());
                throw new DeliveryException(ResultStatusConstant.PARAM_FORMAT_FAILURE);
            }
        }
        if (StringUtil.isBlank(userId) || StringUtil.isBlank(deliveryHeadId) || StringUtil.isBlank(companyId)) {
            throw new DeliveryException(ResultStatusConstant.DELIVERY_INFO_PARAM_IS_NULL_EXCEPTION);
        }
        DeliveryDetailInfoVO detailInfoVO = new DeliveryDetailInfoVO();
        //获取发货单基础信息
        try {
            DeliveryDetailDTO deliveryItem = deliveryDAO.getDeliveryInfo(deliveryHeadId);
            if (null != deliveryItem) {
                detailInfoVO = ObjectUtil.transfer(deliveryItem, DeliveryDetailInfoVO.class);
            }
            List<LogisticsVO> logisticsVOList = new ArrayList<>();
            List<LogisticsVO> longisticsList = findLogisticsDAO.findLongisticsListByDeliveryId(deliveryHeadId);
            //物流记录,去除掉空值
            if (null != longisticsList && longisticsList.size() > 0) {
                for (LogisticsVO logVo : longisticsList) {
                    if (null != logVo && null != logVo.getDateTime() && null != logVo.getLogisticInfo()) {
                        logisticsVOList.add(logVo);
                    }
                }
            }
            List<DeliveryGoodsDTO> deliveryGoodsDTOList = purchaseContractDAO.getDeliverySendedGoodsInfo(deliveryHeadId);
            List<DeliverySendedGoodsVO> deliveryGoodsVOList = null;
            if (null != deliveryGoodsDTOList && deliveryGoodsDTOList.size() > 0) {
                deliveryGoodsVOList = ObjectUtil.transfer(deliveryGoodsDTOList, DeliverySendedGoodsVO.class);
                Map<String, String> unitMap = valueSetService.getItemUnitTypeNameMap();
                for (DeliverySendedGoodsVO deSendVo : deliveryGoodsVOList) {
                    deSendVo.setUnit(unitMap.get(deSendVo.getUnit()));
                }
            } else {
                deliveryGoodsVOList = new ArrayList<>();
            }
            //查询需求单对应的附件表信息
            Map<String, Object> map = new HashMap<>();
            map.put("billNo", deliveryHeadId);
            map.put("businessType", BusinessType.POM_PURCHASE_CONTRACT);
            map.put("attachmentType", AttachmentType.PURCHASE_CONTRACT_DOC);
            List<AttachmentDTO> attachmentList = attachmentDAO.findByBillNo(map);
            List<AttachmentVO> attachmentVOList = new ArrayList<AttachmentVO>();
            if (null != attachmentList && attachmentList.size() > 0) {
                attachmentVOList = ObjectUtil.transfer(attachmentList, AttachmentVO.class);
            }

            detailInfoVO.setLogisticsList(logisticsVOList);
            detailInfoVO.setItemList(deliveryGoodsVOList);
            detailInfoVO.setAttachmentList(attachmentVOList);
        } catch (Exception e) {
            logger.error("获取订单发货商品详情数据转化失败，{}", e.getMessage());
            throw new DeliveryException(ResultStatusConstant.PARAM_FORMAT_FAILURE);
        }
        logger.debug("detailInfoVO: \n{}", detailInfoVO);
        return detailInfoVO;
    }

    @Override
    public String submitDeliveryInfo(DeliverySubmitVO deliverySubmitVO) throws DeliveryException {
        Boolean operateResult = true;
        if (StringUtils.isBlank(deliverySubmitVO.getTransportType())) {
            throw new DeliveryException(ResultStatusConstant.OBTAINING_USER_INFORMATION_ERROR);
        } else {
            if (DeliverySubmitVO.LOGISTICS_TYPE_SELF.equals(deliverySubmitVO.getTransportType())) {
                if (StringUtils.isBlank(deliverySubmitVO.getCarShipNo()) || StringUtils.isBlank(deliverySubmitVO.getCarContacts())
                        || StringUtils.isBlank(deliverySubmitVO.getCarContactsPhone())) {
                    throw new DeliveryException(ResultStatusConstant.OBTAINING_USER_INFORMATION_ERROR);
                }
            } else if (StringUtils.isBlank(deliverySubmitVO.getTransportCompany()) || StringUtils.isBlank(deliverySubmitVO.getTransportContacts()) || StringUtils.isBlank(deliverySubmitVO.getCarShipNo()) || StringUtils.isBlank(deliverySubmitVO.getCarContacts())
                    || StringUtils.isBlank(deliverySubmitVO.getCarContactsPhone())) {
                throw new DeliveryException(ResultStatusConstant.OBTAINING_USER_INFORMATION_ERROR);
            }
        }
        deliverySubmitVO.setDeliveryDate(DateUtils.formatDate(new Date(System.currentTimeMillis()), DateUtils.BX_DATE_FMT));
        DeliverySubmitDTO deliverySubmitDTO = null;
        try {
            deliverySubmitDTO = ObjectUtil.transfer(deliverySubmitVO, DeliverySubmitDTO.class);
            ObjectTrimUtil.beanAttributeValueTrim(deliverySubmitDTO);
            String subStr = JSON.toJSONString(deliverySubmitVO.getSubList());
            List<DeliverySubLineDTO> subLineDTOList = JSONUtil.toList(subStr, DeliverySubLineDTO.class);
            if (ObjectUtil.isEmpty(subLineDTOList) && !checkSubLineNotEmpty(subLineDTOList)) {
                throw new DeliveryException(ResultStatusConstant.OBTAINING_USER_INFORMATION_ERROR);
            }
            deliverySubmitDTO.setOperatorUserId(deliverySubmitVO.getUserId());
            deliverySubmitDTO.setOperatorUserName(deliverySubmitVO.getUserName());
            deliverySubmitDTO.setSubLineList(subLineDTOList);
            String submitStr = apiExecutorBxService.submitDeliveryInfo(deliverySubmitDTO);
            logger.info("信息:" + submitStr);
            BxApiResult bxApiResult = JSONUtil.toBean(submitStr, BxApiResult.class);
            if (ObjectUtil.isNotEmpty(bxApiResult)) {
                logger.debug("bxApiResult: \n{}", bxApiResult);
                if ((bxApiResult.getStatus().toString()).equals(BxApiResult.DO_FAILURE)) {
                    throw new DeliveryException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
                } else {
                    operateResult = (bxApiResult.getStatus().toString()).equals(BxApiResult.DO_SUCCESS);
                }
            }
        } catch (Exception e) {
            throw new DeliveryException(ResultStatusConstant.DELIVERY_INFO_SUBMIT_ERROR_EXCEPTION);
        }
        if (operateResult) {
            return DeliverySubmitVO.SUBMIT_SUCCESS_MESSAGE;
        } else {
            return DeliverySubmitVO.SUBMIT_FAILURE_MESSAGE;
        }
    }

    @Override
    public List<DeliveryInfoListVO> findDeliveryList(String userId, String deliveryNoticeId) throws DeliveryException {
        if (StringUtil.isBlank(userId)) {
            throw new DeliveryException(ResultStatusConstant.USERID_CAN_NOT_NULL);
        }
        if (StringUtil.isBlank(deliveryNoticeId)) {
            throw new DeliveryException(ResultStatusConstant.BUSINESS_ID_IS_EMPTY);
        }
        List<DeliveryInfoListDTO> deliveryList = deliveryDAO.findDeliveryList(userId, deliveryNoticeId);
        List<DeliveryInfoListVO> deliveryInfoList = null;
        if (ObjectUtil.isNotEmpty(deliveryList)) {
            try {
                deliveryInfoList = ObjectUtil.transfer(deliveryList, DeliveryInfoListVO.class);
            } catch (Exception e) {
                logger.error("获取供方发货单列表数据转换失败! ，{}", deliveryList, e.getMessage());
                throw new DeliveryException(ResultStatusConstant.PARAM_FORMAT_FAILURE);
            }
        } else {
            deliveryInfoList = new ArrayList<>();
        }
        return deliveryInfoList;
    }

    private boolean checkSubLineNotEmpty(List<DeliverySubLineDTO> sublineList) {
        for (DeliverySubLineDTO deliverySubLineDTO : sublineList) {
            if (StringUtil.isEmpty(deliverySubLineDTO.getFromLineId()) || StringUtils.isEmpty(deliverySubLineDTO.getFromLineCode())
                    || StringUtil.isEmpty(deliverySubLineDTO.getDeliveryQuantity())) {
                return false;
            }
        }
        return true;
    }
}
