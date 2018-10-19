package com.vandream.mall.business.service.impl;


import com.vandream.mall.business.dao.FavoritesDAO;
import com.vandream.mall.business.execption.AddOrderException;
import com.vandream.mall.business.execption.FavoritesException;
import com.vandream.mall.business.execption.FindContractException;
import com.vandream.mall.business.service.FavoritesService;
import com.vandream.mall.business.service.RedisService;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.utils.ObjectUtil;
import com.vandream.mall.commons.utils.StringUtil;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/5 10:56
 */
@Service(value = "favoritesService")
public class FavoritesServiceImpl implements FavoritesService {

    private static final Logger logger = LoggerFactory.getLogger(FavoritesServiceImpl.class);

    private static final byte STATUS_FLAG = 1;
    @Autowired
    private FavoritesDAO favoritesDao;


    /**
     * 添加收藏列表
     * @param userId
     * @param itemId
     * @param areaCode
     * @param count
     * @return
     * @throws FavoritesException
     * @throws FindContractException
     */
    @Override
    public int addFavorites(String userId, String itemId, String areaCode, BigDecimal count)
            throws FavoritesException, FindContractException {
        if(userId == null || itemId == null){
            logger.error("参数为空");
            throw new FindContractException(ResultStatusConstant.CONTRACT_PARAMETERS_INTRODUCE_INTO_NULL);
        }

        String spuId = "";
        Byte status = STATUS_FLAG;
        String itemLineId = favoritesDao.selectItemLineId(itemId, areaCode);
        if (null == itemLineId) {
            throw new FavoritesException(ResultStatusConstant.ITEMLINEID_INVALID_STATE);
        }
        if (StringUtil.isBlank(itemLineId)) {
            logger.info("itemLineId为空", itemLineId);
            throw new FavoritesException(ResultStatusConstant.ITEM_LINE_INVALID);
        }
        BigDecimal minOrderNum = favoritesDao.selectItemLineMinOrder(itemLineId);
        if (count == null) {
            count = BigDecimal.ONE;
        }
        boolean flagt=count.toString().contains(".");
        if(flagt){
            throw new FindContractException(ResultStatusConstant.NO_DECIMAL_INPUT_ALLOWED);
        }
        if (ObjectUtil.isNotEmpty(minOrderNum)) {
            int flag = minOrderNum.compareTo(count);
            if (flag >= 0) {
                count = minOrderNum;
            }
        }
        BigDecimal num = favoritesDao.selectFavorites(itemLineId,userId);
            if (num == null) {
                favoritesDao.addFavorites(userId, itemId, itemLineId, spuId, status, count);
            } else {
                num = num.add(count);
                int flags = num.compareTo(new BigDecimal(99999));
                if (flags > 0) {
                    throw new FavoritesException(ResultStatusConstant.ORDER_NUMBER_MAXIMUM);
                }
                favoritesDao.addFavoritesCount(num, itemLineId);
            }
        int number = getFavoritesCountByUserId(userId);
        return number;
    }

    @Override
    public void removeFavorites(Integer id, String userId) {
        favoritesDao.removeFavorites(id, userId);
    }

    @Override
    public int getFavoritesCountByUserId(String userId) {
        return favoritesDao.selectFavoritesCountByUserId(userId);
    }


}
