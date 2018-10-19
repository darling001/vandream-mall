package com.vandream;

import com.vandream.mall.business.dao.SnapshotDAO;
import com.vandream.mall.business.dto.snapshot.ItemSnapshotDTO;
import com.vandream.mall.business.execption.BusinessException;
import com.vandream.mall.business.service.SnapshotService;
import com.vandream.mall.business.vo.ItemSnapshotVO;
import com.vandream.mall.commons.utils.JSONUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA
 *
 * @author : liguoqing
 * @date : 2018/7/30
 * Time: 20:07
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SnapshotListTest {
    @Resource
    private SnapshotService snapshotService;
    @Autowired
    private SnapshotDAO snapshotDAO;

    /**
     * dao层：测试获取商品快照基本信息
     */
    @Test
    public void testGetItemSnapshotDetailInfo() {
        ItemSnapshotDTO itemSnapshotDetailInfo = snapshotDAO.getItemSnapshotDetailInfo("6b6a5645e808f78175e8e69ab9814859", "3");
        System.out.println("JSONUtil.toJson(itemSnapshotDetailInfo) = " + JSONUtil.toJson(itemSnapshotDetailInfo));

    }

    /**
     * service层：测试获取商品快照
     * @throws BusinessException
     */
    @Test
    public void getItemSnapshotDetail() throws BusinessException {

        String salesContractHeadId = "e7b9d82ae2ddb43850f8e85fd4182d82";
        String itemLineId ="6b6a5645e808f78175e8e69ab9814859";
        String version = "6";
        ItemSnapshotVO itemSnapshotVO = snapshotService.getItemSnapshot(salesContractHeadId, itemLineId, version);
        System.out.println(JSONUtil.toJson(itemSnapshotVO));
    }
}
