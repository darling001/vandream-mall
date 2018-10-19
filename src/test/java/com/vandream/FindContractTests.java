package com.vandream;

import com.vandream.mall.business.dao.*;
import com.vandream.mall.business.dto.publish.Advertisement;
import com.vandream.mall.business.execption.FindContractException;
import com.vandream.mall.business.execption.PublishException;
import com.vandream.mall.business.execption.SystemException;
import com.vandream.mall.business.service.*;
import com.vandream.mall.business.vo.buyerContract.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.vandream.mall.commons.utils.ObjectUtil;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/29 10:50
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FindContractTests {

    @Resource
    private FindContractService findContractService;

    @Resource
    private FindDeliverService findDeliverService;

    @Resource
    private FindLogisticsService findLogisticsService;

    @Resource
    private ConfirmationContractService confirmationContractService;

    @Test
    public void test(){
        String userId = "fa98f0e961cf0d7d0557a65bc563ada7";
        String companyId = "264572e6413c21a759a9f1f8a152e2ba";
        String keyword = "";
        Long contractStartDate =null;
        Long contractEndDate = null;
        int pageSize = 10;
        int pageNo = 1;
        String contractStatus = "";
        String paymentStatus = "";
        String DeliveryStatus = null;
        FindContractListVO contractListVOS = null;
        try {
            contractListVOS = findContractService.findContractList(userId, companyId, keyword, contractStartDate, contractEndDate, pageSize, pageNo, contractStatus, paymentStatus, DeliveryStatus);
        } catch (FindContractException e) {
            e.printStackTrace();
        }
        System.out.println("contractListVOS.toString() = " + contractListVOS.toString());
    }

    @Test
    public void findinfo(){
        String userId = "1";
        String companyId = "0";
        String contractId = "45e9e5f586088e5d7574bc57273af39f";
        List<FindDeliverInfoVO> findDeliverInfoVOList = null;
        try {
            findDeliverInfoVOList = findDeliverService.findDeliverInfoList(userId,companyId,contractId);
        } catch (FindContractException e) {
            e.printStackTrace();
        }
        System.out.println("findDeliverInfoVOList = " + findDeliverInfoVOList);
    }

    @Test
    public void findNotice(){
        String userId = "40c5165a220a4c1d0bd6729ef7b2defe";
        String companyId = "0";
        String contractId = "4bf1d0b437279e93b8123f53d9e5f8a4";

        List<FindDeliverNoticeVO> findDeliverNoticeVOS = null;
        try {
            findDeliverNoticeVOS = findDeliverService.findDeliverNoticeList(userId,companyId,contractId);
        } catch (FindContractException e) {
            e.printStackTrace();
        }
        System.out.println("findDeliverNoticeVOS = " + findDeliverNoticeVOS);
    }


    @Test
    public void findLogistics() {
        String userId = "1";
        String deliverInfold = "091d40e4226f594a2bbf4d54153e217f";
        /*FindContractListVO findLogisticsList(@Param("userId") String userId,
                @Param("deliverInfold") String deliverInfold);*/
        FindLogisticsListVO findLogisticsListVO = null;
        try {
            findLogisticsListVO = findLogisticsService.findLogisticsList(userId, deliverInfold);
        } catch (FindContractException e) {
            e.printStackTrace();
        }
        System.out.println("findContractListVO = " + findLogisticsListVO);
    }

    @Test
    public void contract(){
        String userId = "2";
        String username = "测试公司2";
        String contractId = "2231";
        String operatorType = "25";
        try {
            confirmationContractService.confirmationContract(userId, username,contractId ,operatorType);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Resource
    private ContractInfoDAO contractInfoDAO;
    
    @Test
    public void contractItemInfo() throws FindContractException, SystemException {
        String contractId = "d55fde2ada3c75d2037c9334c4fe4001";
        //ContractInfoVO contractInfoTest = findContractService.getContractInfo(null,contractId);
        List<ContractInfoitemListVO> iteminfo = contractInfoDAO.getContractInfoItem(contractId);
        List<AttachmentListVO> contractInfo = contractInfoDAO.getContractList(contractId);
        System.out.println("contractInfo = " + contractInfo);
        List<AttachmentListVO> attachmentInfo = contractInfoDAO.getAttachment(contractId);
        ContractInfoVO contractInfoVO = contractInfoDAO.getContractInfo("zhangsan",contractId);
        ContractInfoVO contractIdentifyInfo  =contractInfoDAO.getContractIdentifyPeople(contractId);
        BigDecimal paymentTotalAmount = contractInfoDAO.getTotalAmount(contractId);
        ContractInfoVO contractInfoTotal = findContractService.getContractInfo("2",contractId);
        System.out.println("attachmentInfo = " + attachmentInfo + contractInfoVO);
        System.out.println("iteminfo = " + iteminfo + contractInfo + attachmentInfo);
    }

    @Test
    public void billingInformation(){
        String contractId = "0da5dcf9bd94a547fdaec392ec39a0b2";
        List<BillingInformationVO> iteminfo = contractInfoDAO.getBillingInformation(contractId);
        System.out.println("iteminfo = " + iteminfo);
    }


    @Resource
    private DeliveryInfoDAO deliveryInfoDAO;

    @Test
    public void getDeliveryInfo(){
        String userId = "11";
        String deliveryId = "37268090556b48ae9699a128400a780b";

        DeliveryInfoVO deliveryInfoVO = deliveryInfoDAO.getDeliveryInfo(userId,deliveryId);
        System.out.println("deliveryInfoVO = " + deliveryInfoVO);
    }

    @Resource
    private DeliveryNoticeInfoDAO deliveryNoticeInfoDAO;



    @Test
    public void getDeliveryNoticeInfo() throws FindContractException {
        String userId = "11";
        String deliveryNoticeId = "d1febcc7f6ffeb7774fd9ab18b23a643";

        DeliveryNoticeInfoVO deliveryNoticeInfoVO = deliveryNoticeInfoDAO.getDeliveryNoticeInfo(userId,deliveryNoticeId);
        DeliveryNoticeInfoVO deliveryNoticeInfoVO2 = findContractService.getDeliveryNoticeInfo(userId,deliveryNoticeId);
        System.out.println("deliveryNoticeInfoVO = " + deliveryNoticeInfoVO);
        System.out.println("deliveryNoticeInfoVO2 = " + deliveryNoticeInfoVO2);


    }


    @Test
    public void findPaymentHistory() throws FindContractException {
        String userId = "11";
        String contractId = "aab26f4c33bd119f5d3b0051056d5370";

        FindPaymentHistoryVO findPaymentHistoryVO =findContractService.findPaymentHistory(userId,contractId);
        System.out.println(findPaymentHistoryVO);

    }
    @Test
    public  void testExportPdf(){

        String userId="fa98f0e961cf0d7d0557a65bc563ada7";
        String contractId="b08f6c4dac478d6cfb146882dd973e1e";
        //b08f6c4dac478d6cfb146882dd973e1e
        try {
            Map<String, String> map = findContractService.exportContractPdf(userId, contractId);
            if(ObjectUtil.isNotEmpty(map)){
                System.out.println("==========="+map.toString());
            }
        } catch (FindContractException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        }
    }


}
