package com.vandream.mall.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.vandream.mall.business.constant.BusinessType;
import com.vandream.mall.business.dao.CompanyDAO;
import com.vandream.mall.business.domain.Attachment;
import com.vandream.mall.business.dto.*;
import com.vandream.mall.business.dto.authentication.CompanyDTO;
import com.vandream.mall.business.dto.authentication.CompanyTypeDTO;
import com.vandream.mall.business.dto.authentication.CustomerDTO;
import com.vandream.mall.business.dto.authentication.SupplierDTO;
import com.vandream.mall.business.execption.BusinessException;
import com.vandream.mall.business.execption.CompanyException;
import com.vandream.mall.business.execption.SystemException;
import com.vandream.mall.business.service.AccountService;
import com.vandream.mall.business.service.CompanyService;
import com.vandream.mall.business.vo.authentication.CompanyTypeVO;
import com.vandream.mall.business.vo.authentication.CompanyVO;
import com.vandream.mall.business.vo.authentication.CustomerVO;
import com.vandream.mall.business.vo.authentication.SupplierVO;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.service.ApiExecutorBxService;
import com.vandream.mall.commons.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/20
 * @time : 13:41
 * Description:
 * 企业类别服务实现类
 */
@Service(value = "companyService")
public class CompanyServiceImpl implements CompanyService {

    /**
     * 老版认证类型
     **/
    private static final String OLD_VERSION = "0";
    /**
     * 新版认证类型
     **/
    private static final String NEW_VERSION = "1";
    /**
     * 认证响应成功
     **/
    private static final String SUCCESS = "成功";
    private static final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);
    @Autowired
    private CompanyDAO companyDAO;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ApiExecutorBxService apiExecutorBxService;

    @Override
    public List<CompanyTypeVO> getCompanyTypeList(String customerType) throws CompanyException {

        if (customerType == null || "".equals(customerType)) {
            throw new CompanyException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }

        List<CompanyTypeDTO> companyTypeList = null;

        try {
            companyTypeList = companyDAO.getCompanyTypeList(customerType);
            if (null == companyTypeList || companyTypeList.size() <= 0) {
                logger.debug("企业类别列表为空！companyTypeList={}", companyTypeList);
            }
            List<CompanyTypeVO> companyTypeVOList = ObjectUtil.transfer(companyTypeList, CompanyTypeVO.class);
            return companyTypeVOList;

        } catch (Exception e) {
            logger.debug("数据库查询失败，{}", customerType, e);
            throw new CompanyException(ResultStatusConstant.FAIL_TO_GET_COMPANY_TYPE);
        }

    }

    @Override
    public String customerAuthentication(CustomerVO customerVO) throws BusinessException {
        //参数校验
        if (null == customerVO) {
            logger.info("需方认证信息customerVO={}", customerVO);
            throw new CompanyException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }

        String userId = customerVO.getUserId();
        String userName = customerVO.getUserName();
        String customerType = customerVO.getCustomerType();
        String companyName = customerVO.getCompanyName();
        String contacts = customerVO.getContacts();
        String contactNumber = customerVO.getContactNumber();
        String position = customerVO.getPosition();
        String certificateType = customerVO.getCertificateType();
        String creditCode = customerVO.getCreditCode();

        //校验需方是否三证合一及统一社会信用代码
        validateInputParams(userId, userName, companyName, contacts, contactNumber, position, certificateType,
                creditCode);
        if (StringUtil.isBlank(customerType)) {
            logger.info("需方企业类型customerType={}", customerType);
            throw new CompanyException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        //手机号校验
        if (!RegexUtil.isChinaMobilePhone(customerVO.getContactNumber())) {
            logger.info("手机号contactNumber={}", customerVO.getContactNumber());
            throw new CompanyException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        //图片附件校验
        List<Attachment> attachmentList = customerVO.getAttachmentList();
        if (attachmentList == null || attachmentList.size() == 0) {
            logger.info("附件列表attachmentList={}", attachmentList);
            throw new CompanyException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        for (Attachment attachment : attachmentList) {
            if (StringUtil.isBlank(attachment.getAttachmentName()) || StringUtil.isBlank(attachment.getAttachmentPath())
                    || StringUtil.isBlank(attachment.getAttachmentType()) || null==attachment.getFileSize()
             || StringUtil.isBlank(attachment.getFileType())) {
                logger.info("附件元素参数不合法");
                throw new CompanyException(ResultStatusConstant.INPUT_PARAM_ERROR);
            }
        }


        //VO转DTO
        CustomerDTO customerDTO = null;
        try {
            customerDTO = ObjectUtil.transfer(customerVO, CustomerDTO.class);
        } catch (Exception e) {
            logger.info("VO转换为DTO，customerDTO={}", customerDTO);
            e.printStackTrace();
            throw new CompanyException(ResultStatusConstant.INTERNAL_INVOCATION_FAILURE);
        }
        customerDTO.setAccountName(customerVO.getUserName());
        //来源类别，商城10
        customerDTO.setFromType("10");
        //业务类型，固定值4.18更新
        customerDTO.setBusinessType(BusinessType.CRM_COMPANY_AUTH);
        //当前登录用户id
        customerDTO.setOperatorUserId(customerVO.getUserId());
        //当前登录用户名称
        customerDTO.setOperatorUserName(customerVO.getUserName());

        //调用宝信API
        String customerAuthenticationResponse = null;
        try {
            customerAuthenticationResponse = apiExecutorBxService.customerAuthentication(customerDTO);
        } catch (Exception e) {
            logger.info("宝信需方认证接口响应customerAuthenticationResponse={}", customerAuthenticationResponse);
            throw new CompanyException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
        }

        if (StringUtil.isBlank(customerAuthenticationResponse)) {
            logger.info("第三方接口需方认证响应customerAuthenticationResponse={}", customerAuthenticationResponse);
            throw new CompanyException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
        }
        //解析宝信响应参数
        BxApiResult bxApiResult = JSON.parseObject(customerAuthenticationResponse, BxApiResult.class);

        if (bxApiResult.getStatus() == 1) {
            return SUCCESS;
        } else {
            BusinessException businessException = new BusinessException(ResultStatusConstant
                    .REMOTE_INTERFACE_CALL_FAILURE);
            businessException.setMessage(bxApiResult.getMessage());
            throw businessException;
        }


    }


    @Override
    public String supplierAuthentication(SupplierVO supplierVO) throws BusinessException {
        //参数校验
        if (supplierVO == null) {
            logger.info("供方信息supplierVO={}", supplierVO);
            throw new CompanyException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }

        String userId = supplierVO.getUserId();
        String userName = supplierVO.getUserName();
        String supplierType = supplierVO.getSupplierType();
        String companyName = supplierVO.getCompanyName();
        String contacts = supplierVO.getContacts();
        String contactNumber = supplierVO.getContactNumber();
        String position = supplierVO.getPosition();
        String certificateType = supplierVO.getCertificateType();
        String creditCode = supplierVO.getCreditCode();
        String businessArea = supplierVO.getBusinessArea();
        String businessCategory = supplierVO.getBusinessCategory();
        String mainProduct = supplierVO.getMainProduct();
        String brand = supplierVO.getBrand();

        //参数校验
        validateInputParams(userId, userName, companyName, contacts, contactNumber, position, certificateType,
                creditCode);
        if (StringUtil.isBlank(supplierType)) {
            logger.info("供方类型supplierType={}", supplierType);
            throw new CompanyException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        //校验手机号
        if (!RegexUtil.isChinaMobilePhone(supplierVO.getContactNumber())) {
            logger.info("供方认证手机号contactNumber={}", supplierVO.getContactNumber());
            throw new CompanyException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        //校验图片附件列表
        List<Attachment> attachmentList = supplierVO.getAttachmentList();
        if (attachmentList == null || attachmentList.size() == 0) {
            logger.info("供方附件列表attachmentList={}", JSONUtil.toJson(attachmentList));
            throw new CompanyException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        for (Attachment attachment : attachmentList) {
            if (StringUtil.isBlank(attachment.getAttachmentName()) || StringUtil.isBlank(attachment.getAttachmentPath())
                    || StringUtil.isBlank(attachment.getAttachmentType()) ||null==attachment.getFileSize()
             || StringUtil.isBlank(attachment.getFileType())) {
                logger.info("附件元素参数不合法");
                throw new CompanyException(ResultStatusConstant.INPUT_PARAM_ERROR);
            }
        }
        if (StringUtil.isBlank(businessArea) || StringUtil.isBlank(businessCategory) || StringUtil.isBlank(mainProduct)
                || StringUtil.isBlank(brand)) {
            logger.info("经营区域businessArea={},经营品类businessCategpory={},主营产品mainProduct={},品牌brand={}", businessArea,
                    businessCategory, mainProduct, brand);
            throw new CompanyException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }

        //VO转DTO
        SupplierDTO supplierDTO = null;
        try {
            supplierDTO = ObjectUtil.transfer(supplierVO, SupplierDTO.class);
        } catch (Exception e) {
            logger.info("VO转换为DTO，supplierDTO={}", supplierDTO);
            throw new CompanyException(ResultStatusConstant.INTERNAL_INVOCATION_FAILURE);
        }
        supplierDTO.setAccountName(supplierVO.getUserName());
        //来源类别，商城10
        supplierDTO.setFromType("10");
        //业务类型，固定值4.18更新
        supplierDTO.setBusinessType(BusinessType.CRM_COMPANY_AUTH);
        //当前登录用户id
        supplierDTO.setOperatorUserId(supplierVO.getUserId());
        //当前登录用户名称
        supplierDTO.setOperatorUserName(supplierVO.getUserName());
        //调用宝信API
        String supplierAuthenticationResponse = null;
        try {
            supplierAuthenticationResponse = apiExecutorBxService.supplierAuthentication(supplierDTO);
        } catch (Exception e) {
            throw new CompanyException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
        }

        if (StringUtil.isBlank(supplierAuthenticationResponse)) {
            logger.info("第三方接口供方响应supplierAuthenticationResponse={}", supplierAuthenticationResponse);
            throw new CompanyException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
        }
        //解析宝信响应参数
        BxApiResult bxApiResult = JSON.parseObject(supplierAuthenticationResponse, BxApiResult.class);

        if (bxApiResult.getStatus() == 1) {
            return SUCCESS;
        } else {
            logger.info("返回参数Status={}", bxApiResult.getStatus());
            BusinessException businessException = new BusinessException(ResultStatusConstant
                    .REMOTE_INTERFACE_CALL_FAILURE);
            businessException.setMessage(bxApiResult.getMessage());
            throw businessException;
        }

    }

    @Override
    public CompanyVO getCompanyInfoByUserId(String userId, String companyId) throws CompanyException {
        if (StringUtil.isBlank(userId)) {
            throw new CompanyException(ResultStatusConstant.USERID_CAN_NOT_NULL);
        }
        if (StringUtil.isBlank(companyId)) {
            throw new CompanyException(ResultStatusConstant.BUSINESS_ID_IS_EMPTY);
        }
        CompanyVO companyVO = null;
        CompanyDTO company = null;
        CompanyDTO companyDTO = companyDAO.getCompanyInfo(userId);
        if (ObjectUtils.isEmpty(companyDTO)) {
            throw new CompanyException(ResultStatusConstant.DATA_READ_FAIL);
        }
        if (StringUtils.isBlank(companyDTO.getCustomerId())){
            company = companyDAO.getCompanyInfoByCompanyIAndSupplierId(companyDTO.getSupplierId());
        }else{
            company = companyDAO.getCompanyInfoByCompanyIAndCustomerId(companyDTO.getCustomerId());
        }
        if (ObjectUtils.isEmpty(company)) {
            throw new CompanyException(ResultStatusConstant.DATA_READ_FAIL);
        }
        try {
            companyVO = ObjectUtil.transfer(company, CompanyVO.class);
        } catch (SystemException e) {
            logger.error("获取企业认证信息DTO转换为VO类失败，{}", company, e);
            throw new CompanyException(ResultStatusConstant.PARAM_FORMAT_FAILURE);
        }
        return companyVO;
    }

    /**
     * 校验是否三证合一及统一社会信用代码
     *
     * @param certificateType 证书类型 0:老版,1:新版
     * @param creditCode      18位统一信用代码或15位营业执照编号
     * @throws CompanyException
     */
    private void validateInputParams(String userId, String userName, String companyName, String contacts, String
            contactNumber, String position, String certificateType, String creditCode) throws
            CompanyException {
        if (StringUtil.isBlank(userId) ||StringUtil.isBlank(userName)|| StringUtil.isBlank(companyName) ||
                StringUtil.isBlank(contacts) || StringUtil.isBlank(contactNumber) ||
                StringUtil.isBlank(position)) {
            logger.info("userId={},companyName={},contacts={},contactNumber={},position={}", userId,
                    companyName, contacts, contactNumber, position);
            throw new CompanyException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        if (OLD_VERSION.equals(certificateType)) {
            logger.info("老版三证，0 ，certificateType={}", certificateType);
            if (StringUtil.isBlank(creditCode) || creditCode.length() != 15) {
                logger.info("15位营业执照号，creditCode={}", creditCode);
                throw new CompanyException(ResultStatusConstant.INPUT_PARAM_ERROR);
            }
        } else if (NEW_VERSION.equals(certificateType)) {
            logger.info("新版三证合一，1 ，certificateType={}", certificateType);
            if (StringUtil.isBlank(creditCode) || creditCode.length() != 18) {
                logger.info("18位统一社会信用代码，creditCode={}", creditCode);
                throw new CompanyException(ResultStatusConstant.INPUT_PARAM_ERROR);
            }
        } else {
            logger.info("是否为三证合一，certificateType={}", certificateType);
            throw new CompanyException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
    }
}
