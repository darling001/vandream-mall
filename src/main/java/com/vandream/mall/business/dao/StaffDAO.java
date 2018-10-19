package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.StaffDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import java.util.List;

/**
 * @author Li Jie
 */
public interface StaffDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    StaffDTO selectByPrimaryKey(String objectId);
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<StaffDTO> findListByStaffIdList(List<String> idList);
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    StaffDTO findByStaffId(String staffId);
}