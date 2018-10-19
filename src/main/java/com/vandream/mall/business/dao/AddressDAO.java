package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.AddressDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author liuyuhong
 * @date 2018/3/19
 * @time 17:13
 * @description
 */
@Component
public interface AddressDAO {
    /**
     * 获取地址列表
     * @param companyId
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
    List<AddressDTO> getAddressListByUserId(@Param("companyId") String companyId);

    /**
     * 获取单条地址
     * @param companyId
     * @param addressId
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
    AddressDTO getAddressById(@Param("companyId") String companyId, @Param("addressId") String addressId);

}
