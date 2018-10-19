package com.vandream.mall.business.dao.homepage;

import com.vandream.mall.business.dto.homepage.BrandDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/4/10
 * @time : 17:58
 * Description:
 */
@Mapper
public interface BrandDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_PRODUCT)
    List<BrandDTO> findBrandList();

    @DataSourceTarget(DataSourceKey.DATABASE_PRODUCT)
    BrandDTO getBrandDetailById(String brandId);
}
