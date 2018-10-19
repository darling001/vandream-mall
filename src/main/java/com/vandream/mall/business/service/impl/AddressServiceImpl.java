package com.vandream.mall.business.service.impl;

import com.vandream.mall.business.dao.AddressDAO;
import com.vandream.mall.business.dto.AddressDTO;
import com.vandream.mall.business.dto.BxApiResult;
import com.vandream.mall.business.dto.DefaultAddressDTO;
import com.vandream.mall.business.execption.AddressException;
import com.vandream.mall.business.execption.DemandException;
import com.vandream.mall.business.service.AddressService;
import com.vandream.mall.business.vo.AddressVO;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.service.ApiExecutorBxService;
import com.vandream.mall.commons.utils.JSONUtil;
import com.vandream.mall.commons.utils.ObjectUtil;
import com.vandream.mall.commons.utils.RegexUtil;
import com.vandream.mall.commons.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liuyuhong
 * @date 2018/3/19
 * @time 17:11
 * @description
 */
@Service(value = "addressService")
public class AddressServiceImpl implements AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Autowired
    private AddressDAO addressDAO;

    @Autowired
    private ApiExecutorBxService apiExecutorBxService;

    /**
     * 获取用户收货地址列表
     *
     * @param companyId
     * @return
     * @throws AddressException
     */
    @Override
    public List<AddressVO> getAddressListByUserId(String companyId) throws Exception {
        //入参校验
        if (StringUtil.isBlank(companyId)) {
            logger.info("公司id为空！", companyId);
            throw new AddressException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        //根据公司id获取地址列表
        List<AddressDTO> addressDTOList = addressDAO.getAddressListByUserId(companyId);
        List<AddressVO> addressVOList = new ArrayList<>();
        //将地址列表DTO转换为VO列表传给前台
        if (ObjectUtil.isNotEmpty(addressDTOList)) {
            addressVOList = ObjectUtil.transfer(addressDTOList, AddressVO.class);
        }
        return addressVOList;
    }

    /**
     * 获取单条收货地址
     *
     * @param companyId
     * @param addressId
     * @return
     * @throws AddressException
     */

    @Override
    public AddressVO getAddressById(String companyId, String addressId) throws Exception {
        //入参校验
        if (StringUtil.isBlank(companyId) || StringUtil.isBlank(addressId)) {
            logger.info("获取单条收货地址参数不能为空！", companyId, addressId);
            throw new AddressException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        //获取单条地址
        AddressDTO addressDTO = addressDAO.getAddressById(companyId, addressId);
        if (ObjectUtil.isEmpty(addressDTO)) {
            throw new AddressException(ResultStatusConstant.GET_ADDRESS_ERROR);
        }
        //将获取的地址DTO对象转换为VO对象
        AddressVO addressVO = ObjectUtil.transfer(addressDTO, AddressVO.class);
        return addressVO;
    }

    /**
     * 新增/编辑收货地址
     *
     * @param userId
     * @param companyId
     * @param provinceCode
     * @param cityCode
     * @param areaCode
     * @param provinceName
     * @param cityName
     * @param areaName
     * @param address
     * @param postCode
     * @param contacts
     * @param contactNumber
     * @param isDefault
     * @return
     * @throws AddressException
     */
    @Override
    public Boolean modifyAddress(String userId, String companyId, String addressId, String provinceCode, String cityCode, String areaCode,
                                 String provinceName, String cityName, String areaName, String address, String postCode, String contacts,
                                 String contactNumber, String isDefault, String countryName, String countryCode) throws Exception {
        if (StringUtil.isBlank(userId) || StringUtil.isBlank(companyId) || StringUtil.isBlank(provinceCode) || StringUtil.isBlank(cityCode) || StringUtil.isBlank(areaName)
                || StringUtil.isBlank(areaCode) || StringUtil.isBlank(provinceName) || StringUtil.isBlank(cityName)
                || StringUtil.isBlank(address) || StringUtil.isBlank(contacts) || StringUtil.isBlank(contactNumber)) {
            throw new AddressException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        // 验证手机号格式
        if (!RegexUtil.isChinaMobilePhone(contactNumber)) {
            throw new AddressException(ResultStatusConstant.ACCOUNT_PHONE_FORMAT_ERROR);
        }
        // 详细地址不得超过100个字符
        if (address.length() > 100) {
            throw new AddressException(ResultStatusConstant.ADDRESS_LENGTH_TOO_LONG);
        }
        // 收货人姓名不得超过40个字符
        if (contacts.length() > 40) {
            throw new AddressException(ResultStatusConstant.CONTACT_NAME_LENGTH_TOO_LONG);
        }
        //邮政编码不得超过10个字符
        if (postCode.length() > 10) {
            throw new AddressException(ResultStatusConstant.POST_CODE_LENGTH_TOO_LONG);
        }
        boolean flag = false;
        try {
            String addr = addOrderAddress(userId, companyId, addressId, provinceCode, cityCode, areaCode, provinceName,
                    cityName, areaName, address, postCode, contacts, contactNumber, isDefault, countryName, countryCode);
            flag = isFlag(flag, addr);
        } catch (Exception e) {
            throw new AddressException(ResultStatusConstant.MODIFY_OR_INSERT_FAILED);
        }
        return flag;
    }

    /**
     * 订单添加地址公用方法
     **/
    @Override
    public String addOrderAddress(String userId, String companyId, String addressId, String provinceCode, String cityCode,
                                  String areaCode, String provinceName, String cityName, String areaName, String address, String postCode,
                                  String contacts, String contactNumber, String isDefault, String countryName, String countryCode) throws Exception {
        String addr = "";
        int pageSize = 20;
        List<AddressDTO> addressList = addressDAO.getAddressListByUserId(companyId);
        AddressDTO addressDTO = new AddressDTO();
        if (StringUtil.isNotBlank(addressId)) {
            /** 修改地址 **/
            addressDTO.setCompanySiteId(addressId);
            addressDTO.setOperatorType("U");
        } else {
            if (addressList.size() == pageSize) {
                throw new AddressException(ResultStatusConstant.ADD_ADDRESS_EXCEEDS_LIMIT);
            }
            /** 新增地址 **/
            addressDTO.setOperatorType("N");
        }
        addressDTO.setCompanyId(companyId);
        addressDTO.setCompanySiteId(addressId);
        addressDTO.setSiteType("20");
        addressDTO.setSiteCountryCode(countryCode);
        addressDTO.setSiteCountryName(countryName);
        addressDTO.setSiteRegionCode(provinceCode);
        addressDTO.setSiteRegionName(provinceName);
        addressDTO.setSiteCityCode(cityCode);
        addressDTO.setSiteCityName(cityName);
        addressDTO.setSiteCountyCode(areaCode);
        addressDTO.setSiteCountyName(areaName);
        addressDTO.setSiteAddress(address);
        addressDTO.setSiteReceiver(contacts);
        addressDTO.setSiteReceiverMobile(contactNumber);
        addressDTO.setSitePostal(postCode);
        addressDTO.setSiteStatus("40");
        addressDTO.setOperatorUserId(userId);
        addressDTO.setOperatorUserName(contacts);
        addressDTO.setIsDefault(isDefault);
        //调用宝信修改地址api
        addr = apiExecutorBxService.modifyAddress(addressDTO);
        return addr;
    }

    /**
     * 调用第三方接口
     *
     * @param flag
     * @param addr
     * @return
     * @throws DemandException
     */
    private boolean isFlag(boolean flag, String addr) throws AddressException {
        if (StringUtil.isBlank(addr)) {
            /** 调用宝信接口失败 **/
            throw new AddressException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
        }
        BxApiResult bxApiResult = JSONUtil.toBean(addr, BxApiResult.class);
        if (ObjectUtil.isNotEmpty(bxApiResult)) {
            Integer status = bxApiResult.getStatus();
            if (1 == status) {
                flag = true;
            } else {
                throw new AddressException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
            }
        }
        return flag;
    }

    /**
     * 删除收货地址
     *
     * @param userId
     * @param companyId
     * @param addressId
     * @return
     */
    @Override
    public Boolean deleteAddress(String userId, String companyId, String addressId) throws AddressException {
        //入参校验
        if (StringUtil.isBlank(userId) || StringUtil.isBlank(companyId) || StringUtil.isBlank(addressId)) {
            logger.info("删除收货地址参数不能为空！");
            throw new AddressException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        AddressDTO addressDTO = new AddressDTO();
        //获取要修改的单条地址信息
        AddressDTO address = addressDAO.getAddressById(companyId, addressId);
        if (ObjectUtil.isEmpty(address)) {
            logger.info("获取地址信息异常！");
            throw new AddressException(ResultStatusConstant.GET_ADDRESS_ERROR);
        }
        addressDTO.setCompanyId(companyId);
        addressDTO.setCompanySiteId(addressId);
        boolean flag = false;
        try {
            String addr = apiExecutorBxService.deleteAddress(addressDTO);
            flag = isFlag(flag, addr);
        } catch (Exception e) {
            /** 删除地址失败**/
            throw new AddressException(ResultStatusConstant.DELETE_ADDRESS_FAILED);
        }
        return flag;
    }

    /**
     * 设置默认收货地址
     *
     * @param userId
     * @param companyId
     * @param addressId
     * @return
     */
    @Override
    public Boolean setDefaultAddress(String userId, String companyId, String addressId) throws Exception {
        if (StringUtil.isBlank(userId) || StringUtil.isBlank(companyId)) {
            logger.info("获取单条收货地址参数不能为空！");
            throw new AddressException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        AddressDTO addressDTO = addressDAO.getAddressById(companyId, addressId);
        addressDTO.setSiteType("20");
        addressDTO.setCompanyId(companyId);
        addressDTO.setCompanySiteId(addressId);
        addressDTO.setOperatorUserId(userId);
        /* 传入指定参数给宝信 */
        DefaultAddressDTO defaultAddressDTO = ObjectUtil.transfer(addressDTO, DefaultAddressDTO.class);
        boolean flag = false;
        try {
            String addr = apiExecutorBxService.setDefaultAddress(defaultAddressDTO);
            flag = isFlag(flag, addr);
        } catch (Exception e) {
            /** 设置默认地址失败**/
            throw new AddressException(ResultStatusConstant.SET_DEFAULT_ADDRESS_FAILED);
        }
        return flag;
    }

}



