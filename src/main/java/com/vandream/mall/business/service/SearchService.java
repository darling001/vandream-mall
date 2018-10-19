package com.vandream.mall.business.service;

import com.vandream.mall.business.vo.search.SearchResultVO;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Li Jie
 */
public interface SearchService {
    /**
     * 商品搜索服务
     * @param keyWord
     * @param bandId
     * @param categoryId
     * @param type 搜索类型
     * @param spec 技术参数
     * @param orderBy
     * @param sortBy
     * @param pageNum
     * @param pageSize
     * @return
     * @throws InvocationTargetException
     */
    SearchResultVO search(String keyWord, String bandId, String categoryId, String type, String
            spec, String orderBy, String sortBy, Integer pageNum, Integer pageSize,String userId) ;
}
