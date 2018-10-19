package com.vandream.mall.business.service;

import com.vandream.mall.business.dto.homepage.CategoryDTO;
import com.vandream.mall.business.execption.CategoryException;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/5
 * @time : 13:09
 * Description:
 * 类目业务接口
 */
public interface CategoryService {
    /**
     * 获取类目列表
     *
     * @param categoryId 类目id
     * @return
     */
    List<CategoryDTO> getCategoryList(String categoryId) throws CategoryException;
    List<CategoryDTO> queryCategoryList()throws CategoryException;
}
