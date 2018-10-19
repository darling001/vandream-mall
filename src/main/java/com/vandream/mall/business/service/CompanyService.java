package com.vandream.mall.business.service;

import com.vandream.mall.business.execption.BusinessException;
import com.vandream.mall.business.execption.CompanyException;
import com.vandream.mall.business.execption.SystemException;
import com.vandream.mall.business.vo.authentication.CompanyTypeVO;
import com.vandream.mall.business.vo.authentication.CompanyVO;
import com.vandream.mall.business.vo.authentication.CustomerVO;
import com.vandream.mall.business.vo.authentication.SupplierVO;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/20
 * @time : 13:39
 * Description:
 * 企业服务层
 */
public interface CompanyService {

    /**
     * 获取企业类别列表
     *
     * @param customerType 用户类别
     * @return
     * @throws CompanyException
     */
    List<CompanyTypeVO> getCompanyTypeList(String customerType) throws CompanyException;

    /**
     * 需方认证
     *
     * @return
     * @throws CompanyException
     */
    String customerAuthentication(CustomerVO customerVO) throws Exception;

    /**
     * 供方认证
     *
     * @return
     * @throws CompanyException
     */
    String supplierAuthentication(SupplierVO supplierVO) throws BusinessException, SystemException;

    /**
     * 获取企业认证信息
     *
     * @param userId    用户账号id--对应数据库的account_id
     * @param companyId 企业id
     * @return 企业认证信息详情页面
     * @throws CompanyException
     */
    public CompanyVO getCompanyInfoByUserId(String userId, String companyId) throws CompanyException;
}
