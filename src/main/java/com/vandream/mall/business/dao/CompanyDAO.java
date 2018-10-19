package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.authentication.CompanyDTO;
import com.vandream.mall.business.dto.authentication.CompanyTypeDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/19
 * @time : 17:58
 * Description:
 */
@Component
public interface CompanyDAO {

    /**
     * 获取企业类别列表
     *
     * @param customerType
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<CompanyTypeDTO> getCompanyTypeList(@Param("customerType") String customerType);

    /**
     * 获取企业认证信息(需方)
     * @param customerId
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
    CompanyDTO getCompanyInfoByCompanyIAndCustomerId(@Param("customerId") String customerId);

    /**
     * 获取企业认证信息(供方)
     * @param supplierId
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
    CompanyDTO getCompanyInfoByCompanyIAndSupplierId(@Param("supplierId") String supplierId);

    /**
     * 判断企业角色：供方、需方、两者都是
     * @param userId
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
    CompanyDTO getCompanyInfo(@Param("userId") String userId);
}
