package com.vandream;

import com.alibaba.fastjson.JSON;
import com.vandream.mall.business.execption.SalesReservationException;
import com.vandream.mall.business.service.SalesReservationService;
import com.vandream.mall.business.vo.SalesListVO;
import com.vandream.mall.business.vo.SalesReservationInfoVO;
import com.vandream.mall.business.vo.SalesReservationRequestVO;
import com.vandream.mall.business.vo.SalesReservationVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/3/29
 * Time: 15:07
 * Description: 锁货通知
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SalesReservationServiceTest {

    @Autowired
    private SalesReservationService salesReservationService;

    @Test
    public void getSalesReservationInfo() throws SalesReservationException {
        String supplierId = "2b48a70cb349f9dc6a6d34ba1ac25af4";
        String userId = "1";
        String salesReservationId = "0eb20d63e78d4ff9b6702853c0630863";
        SalesReservationInfoVO salesReservationInfoVO =  new SalesReservationInfoVO();
        salesReservationInfoVO.setSupplierId(supplierId);
        salesReservationInfoVO.setSalesReservationId(salesReservationId);
        salesReservationInfoVO.setUserId(userId);
        SalesReservationVO salesReservationVO = salesReservationService.getSalesReservationInfo(salesReservationInfoVO);
        System.out.println("customerType = " + JSON.toJSONString(salesReservationVO));
    }

    @Test
    public void findSalesReservationList() throws SalesReservationException {
        String userId = "1";
        String supplierId = "2a3b2597e1f0354d7126cd2a211e1ffe";
        String keyword = "";
        String dateTime = "2018-03-27 12:30:45";//1522125045000
        String dateTime1 = "2018-03-29 12:30:45";//1522297845000
        String dateTime2 = "2018-03-31 12:30:45";//1522470645000
        Long notifyStartTime = null;
        Long notifyEndTime = null;
        Integer pageSize = 10;
        Integer pageNo = 1;
        SalesReservationRequestVO salesReservationRequestVO = new SalesReservationRequestVO();
        salesReservationRequestVO.setSupplierId(supplierId);
        salesReservationRequestVO.setKeyword(keyword);
        salesReservationRequestVO.setPageNo(pageNo);
        salesReservationRequestVO.setPageSize(pageSize);
        salesReservationRequestVO.setUserId(userId);
        salesReservationRequestVO.setNotifyEndTime(notifyEndTime);
        salesReservationRequestVO.setNotifyStartTime(notifyStartTime);
        SalesListVO salesListVO = salesReservationService.findSalesReservationList(salesReservationRequestVO);
        System.out.println("customerType = " + JSON.toJSONString(salesListVO));
    }
}
