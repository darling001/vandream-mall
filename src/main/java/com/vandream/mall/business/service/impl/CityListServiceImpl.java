package com.vandream.mall.business.service.impl;

import com.vandream.mall.business.dao.CityListDAO;
import com.vandream.mall.business.execption.AddOrderException;
import com.vandream.mall.business.service.CityListService;
import com.vandream.mall.business.vo.CityListVO;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/5 20:06
 */
@Service(value = "cityListService")
public class CityListServiceImpl implements CityListService {
    private static final Logger logger = LoggerFactory.getLogger(CityListService.class);

    @Autowired
    private CityListDAO cityListMapper;

    @Override
    public List<CityListVO> getCityListService() throws AddOrderException {

        List<CityListVO> cityResults = cityListMapper.getCity();
        List<CityListVO> returnResults = new ArrayList<>();
        try {
            for (CityListVO cityListEntity : cityResults) {
                if (cityListEntity.getId() < 100 && cityListEntity.getId() >= 0) {
                    returnResults.add(cityListEntity);
                }
            }

            for(CityListVO cityResult:returnResults){
                List<CityListVO> cityListResult = new ArrayList<>();
                for(CityListVO cityListEntity:cityResults){
                    if(cityListEntity.getId() <= 10000&&cityListEntity.getId() >= 999&&cityListEntity.getPid().intValue() == cityResult.getId().intValue()){
                     cityListResult.add(cityListEntity);
                    }
                }
                cityResult.setCityListEntityList(cityListResult);
            }

            for(CityListVO provinceResult:returnResults){
               for(CityListVO AreaResult:provinceResult.getCityListEntityList()){
                   List<CityListVO> cityListResult = new ArrayList<>();
                   for(CityListVO cityListEntity:cityResults) {
                       if (cityListEntity.getId() <= 1000000 && cityListEntity.getId() >= 99999 && cityListEntity.getPid().intValue() == AreaResult.getId().intValue()) {
                           cityListResult.add(cityListEntity);
                       }
                   }
                   AreaResult.setCityListEntityList(cityListResult);
               }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("================》获取省市区列表失败");
            throw new AddOrderException(ResultStatusConstant.CITY_LIST_ERROR);
        }
        return returnResults;
    }
}
