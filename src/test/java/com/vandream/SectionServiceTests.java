package com.vandream;

import com.alibaba.fastjson.JSON;
import com.vandream.mall.business.dto.homepage.SectionDTO;
import com.vandream.mall.business.execption.SectionException;
import com.vandream.mall.business.service.SectionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/12
 * @time : 19:23
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SectionServiceTests {
    @Autowired
    private SectionService sectionServiceImpl;

    @Test
    public void getCmsBySectionId() throws SectionException {
        SectionDTO sectionDTO = sectionServiceImpl.getCmsBySectionId("1");
        System.out.println("JSON.toJSONString(SectionEntity) = " + JSON.toJSONString(sectionDTO));
    }
}
