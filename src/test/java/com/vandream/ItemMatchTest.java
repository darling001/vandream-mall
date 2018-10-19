package com.vandream;

import com.alibaba.fastjson.JSONObject;
import com.vandream.mall.business.dto.item.ItemDetailDTO;
import com.vandream.mall.business.execption.BusinessException;
import com.vandream.mall.business.execption.ItemDetailException;
import com.vandream.mall.business.service.OrderService;
import com.vandream.mall.business.vo.order.ItemVO;
import com.vandream.mall.business.vo.order.OrderItemMatchVO;
import com.vandream.mall.business.vo.order.OrderItemRequestVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ItemMatchTest {
     
    @Autowired
    private OrderService orderService;

    

    /**
     * 测试获取商品匹配列表
     */
    @Test
    public void findCurrentItemLineList() throws BusinessException {
        OrderItemRequestVO  orderItemRequestVO  = new OrderItemRequestVO();
        orderItemRequestVO.setCityCode("3301");
        orderItemRequestVO.setCityName("杭州市");
        orderItemRequestVO.setCountyCode("370202");
        orderItemRequestVO.setCountyName("区");
        orderItemRequestVO.setProvinceCode("370202");
        orderItemRequestVO.setProvinceName("浙江省");
        orderItemRequestVO.setUserId("ccd1741c144cc7d79eec833580152e23");
        List<ItemVO>   items  =  new ArrayList<ItemVO>();
        ItemVO itemVO1 = new ItemVO();
        itemVO1.setItemId("d1c0610664b617c62887b1ffae370561");
        itemVO1.setCount(10);
        ItemVO itemVO4 = new ItemVO();
        itemVO4.setItemId("e879e286b9d588955fb5a68b0d448056");
        itemVO4.setCount(10);
        ItemVO itemVO2 = new ItemVO();
        itemVO2.setItemId("c0b0f1af1d6acad6ba6f731eba3ea217");
        itemVO2.setCount(10);
        ItemVO itemVO3 = new ItemVO();
        itemVO3.setItemId("80ca10e272321e8b36e85a11fd74084f");
        itemVO3.setCount(10);
        ItemVO itemVO5 = new ItemVO();
        itemVO5.setItemId("0586bd7e6018ce9fb414ff8de84767e8");
        itemVO5.setCount(10);
        items.add(itemVO5);
        items.add(itemVO4);
        items.add(itemVO3);
        items.add(itemVO2);
        items.add(itemVO1);
        orderItemRequestVO.setItemList(JSONObject.toJSON(items));
        OrderItemMatchVO orderItemMatchVO  = orderService.findCurrentItemLineList(orderItemRequestVO);
        System.out.println(JSONObject.toJSON(orderItemMatchVO)+"------------------------");
    }

}
