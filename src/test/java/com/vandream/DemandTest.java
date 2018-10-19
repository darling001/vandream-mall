package com.vandream;

import com.vandream.mall.business.dao.SolutionHeadDAO;
import com.vandream.mall.business.domain.Attachment;
import com.vandream.mall.business.execption.DemandException;
import com.vandream.mall.business.service.DemandService;
import com.vandream.mall.business.vo.demand.*;
import com.vandream.mall.commons.utils.JSONUtil;

import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author dingjie
 * @date 2018/3/29
 * @time 14:43
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DemandTest {

    @Autowired
    private SolutionHeadDAO solutionHeadDAO;
    @Autowired
    private DemandService demandService;

    @Test
    public void addDemandInfo() {
        DemandHeadVO demandHeadVO = new DemandHeadVO();
        demandHeadVO.setUserId("0");
        demandHeadVO.setUserName("test");
        demandHeadVO.setCustomerId("8");
        demandHeadVO.setCustomerCode("1");
        demandHeadVO.setCustomerName("企业名称");
        demandHeadVO.setDemandResume("需求桌子");
        demandHeadVO.setDemandType("10");
        Attachment attachment = new Attachment();
        attachment.setAttachmentName("附件1");
        attachment.setAttachmentType("psdDemand");
        attachment.setAttachmentPath("group1/M00/00/04/wKgF2lq9mt6AK871AABuAPSKcZA914.xls");
        attachment.setFileType(".xls");
//        attachment.setFileSize(new BigDecimal("10"));
//        demandHeadVO.setAttachment(attachment);
//        demandHeadVO.setDeliveryPeriodEnd(1111111111L);
//        demandHeadVO.setDeliveryPeriodStart(2222222222222222L);
        demandHeadVO.setAddressId("1");
        demandHeadVO.setDemandContacts("刘");
        demandHeadVO.setDemandPhone("1313777777777");
        try {
            demandService.addDemandInfo(demandHeadVO);
        } catch (DemandException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testFindDemandList() {
        DemandRequestVO demandRequestVO = new DemandRequestVO();
        demandRequestVO.setUserId("9ab8878f476ab71eb3c933930905c591");
        demandRequestVO.setCompanyId("09da338484eada7acda69363e61e976f");
        demandRequestVO.setPageNo(1);
        demandRequestVO.setPageSize(10);
        try {
            DemandResponseVO demandList = demandService.findDemandList(demandRequestVO);
            System.out.println("==========================demandList = " + JSONUtil.toJSON
                    (demandList, DemandResponseVO.class));
        } catch (DemandException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        try {
            List<DemandSolutionVO> demandServiceSchemeList = demandService.findSchemeList
                    ("1e7a3ea4bd635c5e8a88f7f7ddaa36b6", "f0d3f8d938926e9db559549392fe8b20");
            System.out.println("==================================demandSolutionList.toString() = " + demandServiceSchemeList.get(0).toString());
        } catch (DemandException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateStatus() {
        DemandStatusVO demandStatusVO = new DemandStatusVO();
        demandStatusVO.setUserId("1e7a3ea4bd635c5e8a88f7f7ddaa36b6");
//        demandStatusVO.setUserName("测试员");
        demandStatusVO.setStatus("60");
        demandStatusVO.setSolutionId("19d4f5b9713b76dc4ec0480a918aaa7e");
        try {
            Boolean aBoolean = demandService.updateSchemeStatus(demandStatusVO);
            System.out.println("aBoolean = " + aBoolean);
        } catch (DemandException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getDemandInfo(){
        String userId="0de11d948721bd1bffe3a782926fea1c";
        String demandId="95f52f21b4e65e992552587bb103e633";
        try {
            demandService.getDemandInfo(userId,demandId);
        } catch (DemandException e) {
            e.printStackTrace();
        }
    }
}
