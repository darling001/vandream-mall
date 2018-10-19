package com.vandream.mall.business.service;

import com.vandream.mall.business.dto.item.AreaDTO;
import com.vandream.mall.business.vo.AreaVO;

import java.util.List;
import java.util.Map;

/**
 * @author dingjie
 * @date 2018/3/21
 * @time 15:17
 * Description:
 */
public interface AreaService {
   public List<AreaVO> getArea();

   /**
    * 获取区域模板编码和其对应的区域对象
    * @return
    */
   Map<String, AreaDTO> getAreaRangeMap();
}
