package com.vandream;

import com.alibaba.fastjson.JSON;
import com.vandream.mall.business.dto.delivery.DeliverySubLineDTO;
import com.vandream.mall.business.execption.DeliveryException;
import com.vandream.mall.business.service.DeliveryService;
import com.vandream.mall.business.vo.DeliveryDetailInfoVO;
import com.vandream.mall.business.vo.authentication.CompanyVO;
import com.vandream.mall.business.vo.delivery.*;
import com.vandream.mall.commons.utils.JSONUtil;
import com.vandream.mall.commons.utils.ObjectUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/4/3
 * Time: 20:07
 * Description: 发货管理测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DeliveryTest {

    @Autowired
    private DeliveryService deliveryService;

    /**
     * 获取供方发货通知单详情测试方法
     */
    @Test
    public void getSupplierDeliveryNoticeInfo(){
        String deliveryNoticeId = "022664ed856156d0154cbe0a6599bebf";
        String userId = "1";
        DeliveryNoticeDetailVO deliveryDetailVO = null;
        try {
            deliveryDetailVO = deliveryService.getSupplierDeliveryNoticeInfo(userId,deliveryNoticeId);
            System.out.println("==========================deliveryDetailVO = " + JSONUtil.toJSON
                    (deliveryDetailVO, DeliveryNoticeDetailVO.class));
        } catch (DeliveryException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取发货管理列表测试方法
     */
   @Test
    public void findDeliveryNoticeList(){
        DeliveryRequestVO requestVO = new DeliveryRequestVO();
        requestVO.setKeyword("");
        requestVO.setUserId("b82c139479865718a4dffa78f5661ecb");
        requestVO.setSupplierId("2b48a70cb349f9dc6a6d34ba1ac25af4");
//        requestVO.setPageNo(1);
//        requestVO.setPageSize(15);
        DeliveryListVO deliveryListVO = null;
        try {
            deliveryListVO = deliveryService.findDeliveryNoticeList(requestVO);
        } catch (DeliveryException e) {
            e.printStackTrace();

        }
        System.out.println("==========================deliveryListVO = " + JSONUtil.toJSON
                (deliveryListVO, DeliveryListVO.class));
    }

    /**
     * 获取供方发货单详情测试方法
     */
    @Test
    public void getDeliveryItem(){
        String userId = "1";
        String supplierId = "1";
        String deliveryNoticeId = "336f3728c368608f72f81e19e52431de";
        DeliveryDetailVO deliveryVO = null;
        try {
            deliveryVO = deliveryService.getDeliveryItem(userId,deliveryNoticeId,supplierId);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("==========================deliveryVO = " + JSONUtil.toJSON(deliveryVO, DeliveryDetailVO.class));
    }

    @Test
    public void testDeliveryInfo(){
        String userId = "1";
        String companyId = "1";
        String deliveryHeadId = "5dbab8b33477403233ad498b0921fa55";
        String companyName = "1";
        String userName = "1";
        String deliveryNoticeId = "1";
        DeliveryDetailInfoVO deliveryInfo=null;
        try {
            deliveryInfo = deliveryService.getDeliveryInfo(userId, userName, deliveryHeadId, deliveryNoticeId, companyId, companyName);
            System.out.println("====================================deliveryInfo.toString() = " + deliveryInfo.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("==========================deliveryVO = " + JSONUtil.toJSON(deliveryInfo, DeliveryDetailInfoVO.class));

    }

    @Test
    public void submitDeliveryInfo(){
        String userId = "1";
        String userName = "沈佳庆";
        String transportContacts = "李国清";
        String transportContactsType = "1";
        String transportContactsPhone = "15268541275";
        String transportCompany = "杭萧钢构";
        String transportCode = "SC45432143545415";
        String carShipNo = "浙Aaq520";
        String carContacts = "时峰";
        String carContactsPhone = "12345612311";
        String fromType = "10";
        String fromCode = "Adsfdfd46548548564";
        String fromId = "7c67ec9317cc4730b430cacc85707836";
        String deliveryRemark = "你好！哈哈哈哈哈哈哈";
        List<Map> sublist = new ArrayList<>();
        Map deliverySubLineDTO = new HashMap();
        deliverySubLineDTO.put("fromLineCode","NO18032800070002");
        deliverySubLineDTO.put("fromLineId","28958393075dbd16a23986ea8f7f7c75");
        deliverySubLineDTO.put("deliveryQuantity","100");
        sublist.add(deliverySubLineDTO);
        DeliverySubmitVO deliverySubmitVO = new DeliverySubmitVO();
        deliverySubmitVO.setUserId(userId);
        deliverySubmitVO.setUserName(userName);
        deliverySubmitVO.setCarContacts(carContacts);
        deliverySubmitVO.setCarContactsPhone(carContactsPhone);
        deliverySubmitVO.setDeliveryRemark(deliveryRemark);
        deliverySubmitVO.setFromCode(fromCode);
        deliverySubmitVO.setFromId(fromId);
        deliverySubmitVO.setSubList(sublist);
      //  deliverySubmitVO.setFromType(fromType);
        deliverySubmitVO.setTransportContactsPhone(transportContactsPhone);
        deliverySubmitVO.setTransportType(transportContactsType);
        deliverySubmitVO.setTransportCode(transportCode);
        deliverySubmitVO.setCarShipNo(carShipNo);
        deliverySubmitVO.setTransportContacts(transportContacts);
        deliverySubmitVO.setTransportCompany(transportCompany);
        String deliverySubmit = null;
        try {
            deliverySubmit = deliveryService.submitDeliveryInfo(deliverySubmitVO);
            System.out.println("====================================deliverySubmit.toString() = " + deliverySubmit.toString());
        } catch (DeliveryException e) {
            e.printStackTrace();
            System.out.println("==========================deliveryVO = " + JSONUtil.toJSON(deliverySubmit, DeliverySubmitVO.class));
        }
    }
    @Test
    public void findDeliveryList(){
        String userId = "b82c139479865718a4dffa78f5661ecb";
        String deliveryNoticeId = "44f6866605c2844d77e9f053cebfb890";
        List<DeliveryInfoListVO> deliveryList = null;
        try {
            deliveryList = deliveryService.findDeliveryList(userId,deliveryNoticeId);
            System.out.println("====================================deliveryList.toString() = " + deliveryList.toString());
        } catch (DeliveryException e) {
            System.out.println("==========================deliveryList = " + JSONUtil.toJSON(deliveryList, DeliveryInfoListVO.class));
            e.printStackTrace();
        }
    }

}
