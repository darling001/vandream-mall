package com.vandream;

import com.vandream.mall.business.dto.AddressDTO;
import com.vandream.mall.business.execption.AddressException;
import com.vandream.mall.business.service.AddressService;
import com.vandream.mall.business.service.AreaService;
import com.vandream.mall.business.service.RedisService;
import com.vandream.mall.business.vo.AddressVO;
import com.vandream.mall.business.vo.AreaVO;
import com.vandream.mall.commons.utils.JSONUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * @author liuyuhong
 * @date 2018/3/19
 * @time 20:57
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AddressDAOTest {

    @Autowired
    private AddressService addressService;

    @Autowired
    private RedisService redisService;

    /**
     * 新增/编辑收货地址测试
     * @throws AddressException
     */
    @Test
    public void testModifyAddress() throws Exception {
//        Boolean flag = addressService.modifyAddress("0", "1", "17", "11", "2", "3", "上城区瑞丰", "11111", "aaa", "13131313131", "1",
//                "丁","123453","0");
//        System.out.println("========================================flag:"+flag);

    }

    /**
     *设置默认地址
     */
    @Test
    public void testSetDefaultAddress() throws Exception {
        Boolean Boolean = addressService.setDefaultAddress("0", "1", "17");
    }

    /**
     * 获取单条收货地址测试
     * @throws AddressException
     */
    @Test
    public void testGetAddressById() throws Exception {
        AddressVO addressVO = addressService.getAddressById("0", "11");
        System.out.println(addressVO);
    }

    /**
     * 获取收货地址列表测试
     * @throws AddressException
     */
    @Test
    public void testGetAddressListByUserId() throws Exception {
        List<AddressVO> addressList = addressService.getAddressListByUserId("1");
        System.out.println(addressList);
    }

    /**
     * 删除收货地址
     * @throws AddressException
     */
    @Test
    public void testDeleteAddress() throws AddressException {
        Boolean flag = addressService.deleteAddress("0", "TestCompany01", "10");
        System.out.println(flag);
    }
}
