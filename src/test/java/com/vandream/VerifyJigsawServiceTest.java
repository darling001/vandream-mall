package com.vandream;

import com.vandream.mall.business.service.VerifyJigsawService;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Li Jie
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class VerifyJigsawServiceTest {
    @Resource
    private VerifyJigsawService verifyJigsawService;

    @Test
    public void getJigsaw() throws Exception {
        verifyJigsawService.getJigsaw("12312341234");
    }
}
