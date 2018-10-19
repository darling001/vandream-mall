package com.vandream.mall.business.service;

import com.vandream.mall.business.vo.base.DataListVO;
import com.vandream.mall.business.vo.homepage.BrandDetailVO;
import com.vandream.mall.business.vo.homepage.BrandListVO;

import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/4/10
 * @time : 18:27
 * Description:
 */
public interface BrandService {
    /**
     * 获取品牌列表
     * @return
     */
    DataListVO<BrandListVO> findBrandList(Integer pageSize, Integer pageNo) throws InvocationTargetException;

    /**
     * 根据id获取品牌详情
     * @param brandId
     * @return
     */
    BrandDetailVO getBrandDetailById(String brandId) throws InvocationTargetException;
}
