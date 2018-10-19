package com.vandream.mall.business.service.impl;

import com.vandream.mall.business.dao.homepage.CategoryDAO;
import com.vandream.mall.business.dto.homepage.CategoryDTO;
import com.vandream.mall.business.execption.CategoryException;
import com.vandream.mall.business.service.CategoryService;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.utils.ObjectUtil;
import com.vandream.mall.commons.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/5
 * @time : 20:45
 * Description:
 * 类目业务实现类
 */
@Service(value = "categoryService")
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public List<CategoryDTO> queryCategoryList() throws CategoryException {

        List<CategoryDTO> categoryDTOList = categoryDAO.queryCategoryList();
        //返回的类目列表
        List<CategoryDTO> categoryList = new ArrayList<>();
        //递归获取类目列表
        categoryRecursive(categoryDTOList, 0, Integer.valueOf(categoryDTOList.get(0).getCategoryLevel()), categoryList);
        //对sort字段进行排序
        if (ObjectUtil.isNotEmpty(categoryList)) {
            categoryList.sort((CategoryDTO o1, CategoryDTO o2) -> {
                if (o1.getSort() != null && o2.getSort() != null) {
                    return o1.getSort() - o2.getSort();
                }
                return 0;
            });
        }
        return categoryList;
    }

    /**
     * 递归查询类目列表
     *
     * @param categoryDTOList 数据库查出数据
     * @param index           索引
     * @param level           类目等级
     * @param categoryList    返回类目数据列表
     * @return int
     */
    public int categoryRecursive(List<CategoryDTO> categoryDTOList, int index, int level, List<CategoryDTO>
            categoryList) {
        for (; index < categoryDTOList.size(); index++) {
            CategoryDTO categoryDTO = categoryDTOList.get(index);
            if (Integer.valueOf(categoryDTO.getCategoryLevel()) > level) {
                index = categoryRecursive(categoryDTOList, index,
                        level + 1, categoryDTOList.get(index - 1).getCategoryList());
            } else if (Integer.valueOf(categoryDTO.getCategoryLevel()) == level) {
                categoryList.add(categoryDTO);
            } else if (Integer.valueOf(categoryDTO.getCategoryLevel()) < level) {
                break;
            }
        }
        return index - 1;
    }

    @Override
    public List<CategoryDTO> getCategoryList(String categoryId) throws CategoryException {
        logger.info("非必传参数:categoryId,{}", categoryId);
        try {
            return categoryDAO.getCategoryList(categoryId);
        } catch (Exception e) {
            logger.info("数据库查询出错！，{}", categoryId);
            throw new CategoryException(ResultStatusConstant.CATEGORY_ACCESS_FAIL);
        }


//        String categoryInRedis = (String) redisTemplate.opsForHash().get("CATEGORY", "CATEGORYLIST");
//        ArrayList redisCategoryList = JSON.parseObject(categoryInRedis, new ArrayList<CategoryEntity>().getClass());
//        if (categoryInRedis == null) {
//            List categoryList = categoryMapper.getCategoryList(categoryId);
//            redisTemplate.opsForHash().put("CATEGORY", "CATEGORYLIST", JSON.toJSONString(categoryList));
//            return categoryList;
//        }
//        return redisCategoryList;
    }

}
