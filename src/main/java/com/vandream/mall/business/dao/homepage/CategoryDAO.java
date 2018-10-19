package com.vandream.mall.business.dao.homepage;

import com.vandream.mall.business.dto.homepage.CategoryDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/5
 * @time : 13:01
 * Description:
 * 类目Mapper
 */
@Component
public interface CategoryDAO {
    /**
     * 获取类目列表
     *
     * @param categoryId 类目id
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    List<CategoryDTO> getCategoryList(@Param("categoryId") String categoryId);

    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    List<CategoryDTO> queryCategoryList();
}
