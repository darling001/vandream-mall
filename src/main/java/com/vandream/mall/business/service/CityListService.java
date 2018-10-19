package com.vandream.mall.business.service;


import com.vandream.mall.business.execption.AddOrderException;
import com.vandream.mall.business.vo.CityListVO;

import java.util.List;

public interface CityListService {

    /**
     *
     * @return
     */
    List<CityListVO> getCityListService() throws AddOrderException;
}
