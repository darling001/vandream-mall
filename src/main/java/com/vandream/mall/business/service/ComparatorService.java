package com.vandream.mall.business.service;

import com.vandream.mall.business.vo.ComparatorVO;
import com.vandream.mall.business.vo.search.SearchItemAggVO;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author Li Jie
 */
public interface ComparatorService {
    List<SearchItemAggVO> findSpuItemList(String spuId) throws InvocationTargetException;
    ComparatorVO findListItemInfo(Object itemIdList,String userId) throws InvocationTargetException;
}
