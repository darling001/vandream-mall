package com.vandream.mall.business.service.impl;

import com.vandream.mall.business.dao.PurchaseContractDAO;
import com.vandream.mall.business.dto.delivery.DeliveryGoodsDTO;
import com.vandream.mall.business.dto.delivery.notice.DeliveryNoticeGoodsDTO;
import com.vandream.mall.business.execption.PurchaseContractException;
import com.vandream.mall.business.execption.SystemException;
import com.vandream.mall.business.service.PurchaseContractService;
import com.vandream.mall.business.vo.delivery.DeliveryGoodsVO;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.utils.ObjectUtil;
import com.vandream.mall.commons.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/4/3
 * Time: 20:23
 * Description: 供方发货通知单详情中的商品信息
 */
@Service("purchaseContractService")
public class PurchaseContractServiceImpl implements PurchaseContractService {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseContractServiceImpl.class);

    @Autowired
    private PurchaseContractDAO purchaseContractDAO;


    @Override
    public List<DeliveryNoticeGoodsDTO> getDeliveryNoticeGoodsInfo(String deliveryNoticeId) throws PurchaseContractException {
        if (StringUtil.isBlank(deliveryNoticeId)) {
            throw new PurchaseContractException(ResultStatusConstant.BUSINESS_ID_IS_EMPTY);
        }
        List<DeliveryNoticeGoodsDTO> deliveryNoticeGoodsDTOList = purchaseContractDAO.getDeliveryNoticeGoodsInfo(deliveryNoticeId);
        if (ObjectUtil.isEmpty(deliveryNoticeGoodsDTOList)) {
            throw new PurchaseContractException(ResultStatusConstant.DATA_READ_FAIL);
        }
        return deliveryNoticeGoodsDTOList;
    }

    @Override
    public List<DeliveryGoodsVO> getDeliveryGoodsInfo(String deliveryNoticeId) throws PurchaseContractException {
        if (StringUtil.isBlank(deliveryNoticeId)) {
            throw new PurchaseContractException(ResultStatusConstant.BUSINESS_ID_IS_EMPTY);
        }
        List<DeliveryGoodsVO> deliveryGoodsVOList = null;
        List<DeliveryGoodsDTO> deliveryGoodsDTOList = purchaseContractDAO.getDeliveryGoodsInfo(deliveryNoticeId);
        if (ObjectUtil.isNotEmpty(deliveryGoodsVOList)) {
            try {
                deliveryGoodsVOList = ObjectUtil.transfer(deliveryGoodsDTOList, DeliveryGoodsVO.class);
            } catch (SystemException e) {
                logger.error("获取供方发货单详情中的商品信息数据转化失败，{}", deliveryGoodsVOList, e);
                throw new PurchaseContractException(ResultStatusConstant.PARAM_FORMAT_FAILURE);
            }
        } else {
            deliveryGoodsVOList = new ArrayList<>();
        }
        return deliveryGoodsVOList;
    }

}
