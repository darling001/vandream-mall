package com.vandream.mall.business.dao.homepage;

import com.vandream.mall.business.dto.homepage.AdvertisementDTO;
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
 * 广告Mapper
 */
@Component
public interface AdvertisementDAO {
    /**
     * 获取广告列表
     *
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    List<AdvertisementDTO> getAdvertisementList(@Param("sectionId") String sectionId);
}
