package com.vandream.mall.business.dao.homepage;


import com.vandream.mall.business.dto.homepage.TagInfoDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wangchengli & ShiFeng
 * @version 1.0
 * @date 2018-03-30
 */
@Repository
public interface TagInfoDAO {

    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    List<TagInfoDTO> queryTagInfoTreeList(@Param("id") Integer id);
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    List<TagInfoDTO> queryTagInfoChildList(@Param("childTagInfoIds") List<Integer> childTagInfoIds);


}
