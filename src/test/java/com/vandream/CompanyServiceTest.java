package com.vandream;

import com.alibaba.fastjson.JSON;
import com.vandream.mall.business.domain.Attachment;
import com.vandream.mall.business.dto.LoginDTO;
import com.vandream.mall.business.execption.BusinessException;
import com.vandream.mall.business.execption.CompanyException;
import com.vandream.mall.business.execption.SystemException;
import com.vandream.mall.business.execption.UserException;
import com.vandream.mall.business.service.AccountService;
import com.vandream.mall.business.service.CompanyService;
import com.vandream.mall.business.vo.authentication.CustomerVO;
import com.vandream.mall.business.vo.authentication.SupplierVO;
import org.elasticsearch.cluster.ClusterState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/20
 * @time : 14:10
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CompanyServiceTest {
    @Autowired
    private CompanyService companyServiceImpl;


    @Test
    public void getCompanyTypeList() throws CompanyException {

        String customerType = "A44";
        System.out.println("customerType = " + JSON.toJSONString(companyServiceImpl.getCompanyTypeList(customerType)));
    }

    @Test
    public void customerAuthentication() throws Exception {
        CustomerVO customerVO = new CustomerVO();
        customerVO.setUserId("e67fda766109db322540848488665470");
        customerVO.setUserName("测试需方资质认证");
        customerVO.setCompanyName("2");
        customerVO.setCustomerType("1");
        customerVO.setContacts("1");
        customerVO.setContactNumber("13577777777");
        customerVO.setPosition("1");
        customerVO.setCertificateType("1");
        customerVO.setCreditCode("111111111111111111");
        List<Attachment> attachments = new ArrayList<>();
        Attachment attachment = new Attachment();
        attachment.setAttachmentName("1");
        attachment.setAttachmentPath("1");
        attachment.setAttachmentType("1");
//        attachment.setFileSize("1");
        attachment.setFileType("1");
        attachments.add(attachment);
        customerVO.setAttachmentList(attachments);
        companyServiceImpl.customerAuthentication(customerVO);
    }
    @Test
    public void supplierAuthentication() throws BusinessException, SystemException {
        SupplierVO supplierVO = new SupplierVO();
        supplierVO.setUserId("e67fda766109db322540848488665470");
        supplierVO.setUserName("测试供方资质认证");
        supplierVO.setCompanyName("2");
        supplierVO.setSupplierType("1");
        supplierVO.setBusinessArea("1");
        supplierVO.setBusinessCategory("1");
        supplierVO.setMainProduct("1");
        supplierVO.setContacts("1");
        supplierVO.setContactNumber("13577777777");
        supplierVO.setPosition("1");
        supplierVO.setBrand("1");
        supplierVO.setCertificateType("1");
        supplierVO.setCreditCode("111111111111111111");
        List<Attachment> attachments = new ArrayList<>();
        Attachment attachment = new Attachment();
        attachment.setAttachmentName("1");
        attachment.setAttachmentPath("1");
        attachment.setAttachmentType("1");
//        attachment.setFileSize("1");
        attachment.setFileType("1");
        attachments.add(attachment);
        supplierVO.setAttachmentList(attachments);
        companyServiceImpl.supplierAuthentication(supplierVO);
    }

    @Test
    public void getCompanyInfoByUserId() throws CompanyException {
        String userId = "40c5165a220a4c1d0bd6729ef7b2defe";
        String companyId = "71652e0e1d39e7dfa8cfb42406dd1b13";
        System.out.println("userId" + JSON.toJSONString(companyServiceImpl.getCompanyInfoByUserId(userId,companyId)));
    }


}
