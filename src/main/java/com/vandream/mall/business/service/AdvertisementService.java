package com.vandream.mall.business.service;

import com.vandream.mall.business.dto.homepage.AdvertisementDTO;
import com.vandream.mall.business.execption.AdvertisementException;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/5
 * @time : 13:10
 * Description:
 * 广告业务接口
 */
public interface AdvertisementService {

    /**
     * 获取广告列表
     * @param sectionId 版块id
     * @return
     */
    List<AdvertisementDTO> getAdvertisementList(String sectionId) throws AdvertisementException;
}
