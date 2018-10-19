package com.vandream.mall.business.service.impl;


import com.vandream.mall.business.dao.homepage.AdvertisementDAO;
import com.vandream.mall.business.dto.homepage.AdvertisementDTO;
import com.vandream.mall.business.execption.AdvertisementException;
import com.vandream.mall.business.service.AdvertisementService;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/5
 * @time : 17:05
 * Description:
 * 广告业务实现类
 */
@Service(value = "advertisementService")
public class AdvertisementServiceImpl implements AdvertisementService {

    private static final Logger logger = LoggerFactory.getLogger(AdvertisementServiceImpl.class);

    @Autowired
    private AdvertisementDAO advertisementDAO;

    @Override
    public List<AdvertisementDTO> getAdvertisementList(@NotBlank(message = "版块id不能为空！") String sectionId) throws
            AdvertisementException {

        try {
            return advertisementDAO.getAdvertisementList(sectionId);
        } catch (Exception e) {
            logger.info("数据库查询出错！，{}", sectionId);
            throw new AdvertisementException(ResultStatusConstant.ADVERTISEMENT_ACCESS_FAIL);
        }

    }
}
