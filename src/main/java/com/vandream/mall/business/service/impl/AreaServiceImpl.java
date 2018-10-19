package com.vandream.mall.business.service.impl;

import com.vandream.mall.business.dao.AreaDao;
import com.vandream.mall.business.dto.item.AreaDTO;
import com.vandream.mall.business.service.AreaService;
import com.vandream.mall.business.service.RedisService;
import com.vandream.mall.business.vo.AreaVO;
import com.vandream.mall.commons.utils.JSONUtil;
import com.vandream.mall.commons.utils.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dingjie
 * @date 2018/3/21
 * @time 15:18
 * Description:
 */
@Service("areaService")
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;
    @Autowired
    private RedisService redisService;

    @Override
    public List<AreaVO> getArea() {
        //查询时先检查是否在redis中已存入
        String areaStr = redisService.get("provinces");
        List<AreaVO> areaVOList = new ArrayList<AreaVO>();
        if (null != areaStr && !"".equals(areaStr)) {
            areaVOList = JSONUtil.toList(areaStr, AreaVO.class);
            return areaVOList;
        }
        areaVOList = areaDao.getAreaList();
        if (null != areaVOList && areaVOList.size() > 0) {
            redisService.set("provinces", areaVOList);
        }
        return areaVOList;
    }

    /**
     * * 获取区域模板编码和其对应的区域对象
     * @return
     */
    @Override
    public Map<String, AreaDTO> getAreaRangeMap() {
        //获取区域对象列表
        List<AreaDTO> areaRangeMapList = areaDao.findAreaRangeList();
        if (ObjectUtil.isNotEmpty(areaRangeMapList)) {
            Map<String, AreaDTO> areaRangeMap = new HashMap<>(areaRangeMapList.size());
            for (AreaDTO areaDTO : areaRangeMapList) {
                areaRangeMap.put(areaDTO.getAreaCode(), areaDTO);
            }
            return areaRangeMap;
        }
        return new HashMap<>(1);
    }

}
