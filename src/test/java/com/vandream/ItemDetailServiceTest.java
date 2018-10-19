package com.vandream;

import com.vandream.mall.business.dto.item.ItemDetailDTO;
import com.vandream.mall.business.dto.item.ItemInfoDTO;
import com.vandream.mall.business.dto.mallCart.GoodsParams;
import com.vandream.mall.business.execption.ItemDetailException;
import com.vandream.mall.business.service.ElasticsearchService;
import com.vandream.mall.business.service.ItemDetailService;

import com.vandream.mall.commons.utils.JSONUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

/**
 * @author liuyuhong
 * @date 2018/3/12
 * @time 10:07
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ItemDetailServiceTest {
    @Autowired
    private ItemDetailService itemDetailService;
    @Autowired
    private ElasticsearchService elasticsearchService;

    @Test
    public void test1() {
        Map<String, Object> map = elasticsearchService.searchDataById("gms", "item_agg", "imp32e13f5de377af19a6b3d40594ea6", null);
    }

    /**
     * 测试获取商品基本信息
     */
    @Test
    public void testGetItemBaseInfo() throws ItemDetailException {
        ItemInfoDTO itemBaseInfo = itemDetailService.getItemBaseInfo("", "84083a2263683425c26c6f760e06c828", "");
        System.out.println(JSONUtil.toJSON(itemBaseInfo,ItemInfoDTO.class));
    }

    /**
     * 测试获取商品技术参数
     */
    @Test
    public void testGetSkuAttributeList() throws ItemDetailException {
        List<GoodsParams> skuAttributeList = itemDetailService.getSkuAttributeList("itemid7");
        System.out.println(skuAttributeList);
    }

    /**
     * 测试获取商品详细描述信息
     */
    @Test
    public void testGetItemInfoDetail() throws ItemDetailException {
        ItemDetailDTO itemDetail = itemDetailService.getItemInfoDetail("impheadimpheadimp000000000000213");
        System.out.println(itemDetail);
    }

}
