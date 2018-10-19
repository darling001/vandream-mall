package com.vandream;

import com.alibaba.fastjson.JSON;
import com.vandream.mall.business.constant.AttachmentType;
import com.vandream.mall.business.dao.SolutionHeadDAO;
import com.vandream.mall.business.dto.solution.SolutionHeadDTO;
import com.vandream.mall.business.service.SolutionService;
import com.vandream.mall.business.vo.base.DataListVO;
import com.vandream.mall.business.vo.solution.SolutionInfoVO;
import com.vandream.mall.business.vo.solution.SolutionListVO;
import com.vandream.mall.business.vo.solution.UploadAttachmentVO;
import java.lang.reflect.InvocationTargetException;
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
public class SolutionServiceTests {
    @Resource
    private SolutionService solutionService;

    @Resource
    private SolutionHeadDAO solutionHeadDAO;

    @Test
    public void getSolutionInfo() throws InvocationTargetException {
        SolutionInfoVO solutionInfo = solutionService.getSolutionInfo("1",
                "d21467e13b2237dfcd5521cbc606ea3b", "3e4f8c11e87cfcb9c0ecce1c46f0f3e0");
        System.out.println(JSON.toJSONString(solutionInfo));
        Assert.assertNotNull(solutionInfo);
    }

    @Test
    public void findSolutionList() throws InvocationTargetException {
      //  {"userId":"391193e137b027a050dbe09eb3a85e0a","keyword":"","dispatchStartDate":null,
       //         "dispatchEndDate":null,"status":"","pageNo":1,
      //          "supplierId":"2a3b2597e1f0354d7126cd2a211e1ffe"}
        /*DataListVO<SolutionListVO> solutionList = solutionService.findSolutionList("b82c139479865718a4dffa78f5661ecb", "2b48a70cb349f9dc6a6d34ba1ac25af4", "",
                0L, 0L, "30", 15, 2);
        System.out.println(JSON.toJSONString(solutionList));
        Assert.assertNotNull(solutionList);
        Assert.assertNotNull(solutionList.getList());*/
    }
    @Test
    public void generatorSupplierSolutionExcel()throws Exception{
        solutionService.getSolutionPurchaseExcel("1", "1", "09a51b76d70ebb6478f14f5ff67fd227");
    }
    @Test
    public void selectBySolutionId(){
        String solutionId="78edbd069c5e05204e57f56053f42f24";
        String supplierId="10ba756fcbe824d92d83bd17907357c0";
        SolutionHeadDTO solutionHeadDTO = solutionHeadDAO.selectBySolutionId(solutionId,supplierId);
        System.out.println(JSON.toJSONString(solutionHeadDTO));
        Assert.assertNotNull(solutionHeadDTO);
    }
    @Test
    public void uploadAttachment() throws InvocationTargetException {
        UploadAttachmentVO uploadAttachmentVO=new UploadAttachmentVO();
        uploadAttachmentVO.setAttachmentName("Chxxrxx.jpeg");
        uploadAttachmentVO.setAttachmentPath("group1/M00/00/00/Chxxrxx.jpeg");
        uploadAttachmentVO.setAttachmentType(AttachmentType.PSD_SOLUTION_SUPPLIER_FILE);
        uploadAttachmentVO.setFileSize(1545L);
        uploadAttachmentVO.setFileType(".jpeg");
        uploadAttachmentVO.setSolutionId("s545wer78zdx3cews5r");
        uploadAttachmentVO.setUserId("1");
        uploadAttachmentVO.setUserName("111");
        solutionService.uploadAttachment(uploadAttachmentVO);
    }
}
