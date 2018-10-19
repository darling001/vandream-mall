package com.vandream;

import com.alibaba.fastjson.JSON;
import com.vandream.mall.business.service.ComparatorService;
import com.vandream.mall.business.vo.ComparatorVO;
import com.vandream.mall.business.vo.search.SearchItemAggVO;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
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
public class ComparatorServiceTests {
    @Resource
    private ComparatorService comparatorService;
    @Test
    public void findSpuItemList() throws InvocationTargetException {
        List<SearchItemAggVO> spuItemList = comparatorService.findSpuItemList("b2bfe0aab6bbcdfa4d40a8b7c59b9d11");

        System.out.println(JSON.toJSONString(spuItemList));
        Assert.assertNotNull(spuItemList);

    }
    @Test
    public void findListItemInfo() throws InvocationTargetException {
        List<Object> list = new ArrayList<>();
        list.add("3ee06dbebc60d07de7a55623a858f352");
        ComparatorVO comparatorVO = comparatorService.findListItemInfo(list,"");
        System.out.println(JSON.toJSONString(comparatorVO));
        Assert.assertNotNull(comparatorVO);
    }
}
