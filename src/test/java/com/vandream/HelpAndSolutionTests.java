package com.vandream;

import com.alibaba.fastjson.JSON;
import com.vandream.mall.business.dao.homepage.HelpAndSolutionDAO;
import com.vandream.mall.business.dto.homepage.HelpAndSolutionDTO;
import com.vandream.mall.business.dto.homepage.InformationDetailDTO;
import com.vandream.mall.business.service.HelpAndSolutionService;
import com.vandream.mall.business.vo.base.DataListVO;
import com.vandream.mall.business.vo.homepage.HelpAndSolutionDetailVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/4/11
 * @time : 20:54
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class HelpAndSolutionTests {
    @Resource
    private HelpAndSolutionDAO helpAndSolutionDAO;
    @Resource
    private HelpAndSolutionService helpAndSolutionService;

    //Service Test
    @Test
    public void findHelpAndSolutionList1() throws InvocationTargetException {
        DataListVO helpAndSolutionList = helpAndSolutionService.findHelpAndSolutionList(0, 0, 1);
        System.out.println(JSON.toJSONString(helpAndSolutionList));
    }

    @Test
    public void getInformationDetail1() throws InvocationTargetException {
        HelpAndSolutionDetailVO informationDetail = helpAndSolutionService.getInformationDetail(1);
        System.out.println(JSON.toJSONString(informationDetail));
    }

    //DAO test
    @Test
    public void findHelpAndSolutionList() {
        List<HelpAndSolutionDTO> helpAndSolutionList = helpAndSolutionDAO.findHelpAndSolutionList(1);
        System.out.println(JSON.toJSONString(helpAndSolutionList));

    }

    @Test
    public void getInformationDetail() {
        HelpAndSolutionDTO informationDetail = helpAndSolutionDAO.getInformationDetail(1);
        System.out.println(JSON.toJSONString(informationDetail));
    }

    @Test
    public void findInformationDetailList() {
        List<InformationDetailDTO> informationDetailList = helpAndSolutionDAO.findInformationDetailList(1);
        System.out.println(JSON.toJSONString(informationDetailList));
    }
}