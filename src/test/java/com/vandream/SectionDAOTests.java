package com.vandream;

import com.alibaba.fastjson.JSON;
import com.vandream.mall.business.dao.homepage.SectionDAO;
import com.vandream.mall.business.dto.homepage.SectionDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/6
 * @time : 21:48
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SectionDAOTests {

    @Autowired
    private SectionDAO sectionDAO;

    @Test
    public void getSubSection() {

        List<SectionDTO> cmsBySections = sectionDAO.getSectionByParentId(0);
        System.out.println(JSON.toJSONString(cmsBySections));
    }
}
