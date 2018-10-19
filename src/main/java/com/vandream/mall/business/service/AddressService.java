package com.vandream.mall.business.service;

import com.vandream.mall.business.dto.AddressDTO;
import com.vandream.mall.business.execption.AddressException;
import com.vandream.mall.business.vo.AddressVO;

import java.util.List;
import java.util.Map;

/**
 * @author liuyuhong
 * @date 2018/3/19
 * @time 17:10
 * @description
 */
public interface AddressService {
    /**
     * 获取地址列表
     * @param companyId
     * @return
     * @throws Exception
     */
    List<AddressVO> getAddressListByUserId(String companyId) throws Exception;

    /**
     * 获取单条地址
     * @param companyId
     * @param addressId
     * @return
     * @throws Exception
     */
    AddressVO getAddressById(String companyId, String addressId) throws Exception;

    /**
     * 修改地址
     * @param userId
     * @param companyId
     * @param addressId
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
     * @param countryName
     * @param countryCode
     * @return
     * @throws Exception
     */
    Boolean modifyAddress(String userId, String companyId, String addressId, String provinceCode, String cityCode, String areaCode,String provinceName,String cityName, String areaName, String address, String postCode, String contacts, String contactNumber, String isDefault, String countryName, String countryCode) throws Exception;

    /**
     * 删除地址
     * @param userId
     * @param companyId
     * @param addressId
     * @return
     * @throws AddressException
     */
    Boolean deleteAddress(String userId, String companyId, String addressId) throws AddressException;

    /**
     * 设置默认地址
     * @param userId
     * @param companyId
     * @param addressId
     * @return
     * @throws Exception
     */
    Boolean setDefaultAddress(String userId, String companyId, String addressId) throws Exception;

    /**
     * 添加地址
     * @param userId
     * @param companyId
     * @param addressId
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
     * @param countryName
     * @param countryCode
     * @return
     * @throws Exception
     */
    String addOrderAddress(String userId, String companyId, String addressId, String provinceCode, String cityCode, String areaCode,String provinceName,String cityName, String areaName, String address, String postCode, String contacts, String contactNumber, String isDefault, String countryName, String countryCode) throws Exception;
}
