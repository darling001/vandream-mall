package com.vandream.mall.business.dao.homepage;

import com.vandream.mall.business.dto.homepage.SectionDTO;
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
 * @time : 13:09
 * Description:
 * 版块Mapper
 */
@Component
public interface SectionDAO {

    /**
     * 获取版块对应CMS信息
     * @param pid 父id
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    List<SectionDTO> getSectionByParentId(@Param("pid") Integer pid);
}
