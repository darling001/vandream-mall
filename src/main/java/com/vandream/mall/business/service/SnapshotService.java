package com.vandream.mall.business.service;

import com.vandream.mall.business.execption.BusinessException;
import com.vandream.mall.business.vo.ItemSnapshotVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA
 *快照信息处理
 * @author : liguoqing
 * @date : 2018/7/24
 * Time: 16:19
 * Description:
 */
public interface SnapshotService {

    

    /**
     * 获取商品行最新版本map数据
     * @param itemLineList
     * @return
     */
    Map<String,Object> getItemLineMap(Set<String> itemLineList);

    /**
     * 获取商品快照
     * @param salesContractHeadId
     * @param itemLineId
     * @param version
     * @return
     * @throws BusinessException
     */
    ItemSnapshotVO getItemSnapshot(String salesContractHeadId,String itemLineId,String  version) throws BusinessException;
}
