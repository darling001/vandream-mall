package com.vandream;

import com.alibaba.fastjson.JSON;
import com.vandream.mall.business.service.SearchService;
import com.vandream.mall.business.vo.search.SearchResultVO;
import com.vandream.mall.commons.utils.JSONUtil;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Li Jie
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchServiceTest {
    @Value("${spring.profiles.active}")
    private String environment;
    @Autowired
    private SearchService searchService;

    @Test
    public void testSearch() throws Exception {
        String spec1 = "{\"供货周期\":\"40\"}";
        SearchResultVO searchResultVO = searchService.search("", null, "imp1e4a68a9c4a1a048533c8b4d2b3fc", "",
                null, null, null, 1, 15,"");
        System.out.println(JSONUtil.toJson(searchResultVO));

    }

    @Test
    public void groupByCategory() {
        String keyWord = "冰箱";
        //BoolQueryBuilder mainSearchQuery = searchService.getCommonSearchQuery(null);
        //searchService.groupByCategory(mainSearchQuery);

    }

    @Test
    public void getResultCategoryAgg() {
        String keyWord = "冰箱";
        //BoolQueryBuilder mainSearchQuery = searchService.getCommonSearchQuery(null);
        //List<SearchCategoryAggVO> resultCategoryAgg = searchService.getCategoryAgg
        // (mainSearchQuery);
        //System.out.println(JSON.toJSONString(resultCategoryAgg));

    }

    @Test
    public void getSpecContentsAgg() {

    }

    @Test
    public void testJson() {
        String json = "{\"name\":[\"value1\",\"value2\"],\"name2\":[]}";
        HashMap hashMap = JSON.parseObject(json, new HashMap<String, List<String>>().getClass());
        System.out.println(JSONUtil.toJson(hashMap));
    }

    @Test
    public void testEnv() {
        System.out.println(environment);
    }

}
