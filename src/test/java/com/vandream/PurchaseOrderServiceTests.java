package com.vandream;

import com.alibaba.fastjson.JSON;
import com.vandream.mall.business.service.PurchaseOrderService;
import com.vandream.mall.business.vo.base.DataListVO;
import com.vandream.mall.business.vo.delivery.DeliveryVO;
import com.vandream.mall.business.vo.purchase.ConfirmOrderVO;
import com.vandream.mall.business.vo.purchase.OrderInfoVO;
import com.vandream.mall.business.vo.purchase.PurchaseOrderListVO;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Li Jie
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PurchaseOrderServiceTests {
    @Resource
    private PurchaseOrderService purchaseOrderService;


    @Test
    public void findOrderList() throws InvocationTargetException {
        DataListVO<PurchaseOrderListVO> orderList = purchaseOrderService.findOrderList("6cb59d7071c32d3d681c8794c6f2aa6c",
                "80222b24e837892217094e144b4e41c8",null, "", "", null, null, 15,
                1);
        Assert.assertNotNull(orderList);
        Assert.assertNotNull(orderList.getList());
        System.out.println(JSON.toJSONString(orderList));
    }

    @Test
    public void findInvoiceList() throws InvocationTargetException {
        List<DeliveryVO> invoiceList = purchaseOrderService.findInvoiceList
                ("1", "d041156878c28f26f2247714620a6e71");
        System.out.println("invoiceList = " + invoiceList);
        Assert.assertNotNull(invoiceList);
        Assert.assertFalse(invoiceList.isEmpty());
    }
    @Test
    public void getOrderInfo() throws InvocationTargetException {
        OrderInfoVO orderInfo = purchaseOrderService.getOrderInfo("1",
                "dcbeb12837751bdbc51d3d1496450d15");
        Assert.assertNotNull(orderInfo);
    }

    @Test
    public void confirmOrder() throws InvocationTargetException {
        ConfirmOrderVO confirmOrderVO = new ConfirmOrderVO();
        confirmOrderVO.setUserId("09fa359388df351fb4624bcc23beb95b");
        confirmOrderVO.setUserName("测试确认订单");
        confirmOrderVO.setPurchaseContractHeadId("0032975e30c8ee37e35f0d1b46ad7ba3");
        confirmOrderVO.setOperatorType("25");
        confirmOrderVO.setRecordMark("");
        try {
            String s = purchaseOrderService.confirmOrder(confirmOrderVO);
            System.out.println("s = " + s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
