package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.item.ImageDTO;
import com.vandream.mall.business.dto.item.ItemDescDTO;
import com.vandream.mall.business.dto.snapshot.ItemSnapshotDTO;
import com.vandream.mall.business.dto.snapshot.ItemSnapshotVersionDTO;
import com.vandream.mall.business.dto.snapshot.ContractSnapshotDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA
 *
 * @author : liguoqing
 * @date : 2018/7/24
 * Time: 10:50
 * Description:
 */
@Component
public interface SnapshotDAO {
  
    /**
     * 订单快照版本列表信息
     * @param ids
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<ContractSnapshotDTO>      findContractSnapshotList(@Param("ids")  Set<String> ids);
    
    /**
     * 购物车快照版本列表信息
     * @param userId
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    List<ItemSnapshotVersionDTO>      findCartSnapshotList(@Param("userId") String  userId);
    
    /**
     * 购物车快照详情
     * @param 
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_PRODUCT)
    ItemSnapshotVersionDTO getCartSnapshot(@Param("itemId") String itemId, @Param("areaCode") String areaCode);

    /**
     * 批量获取当前商品版本
     * @param itemlineIds
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_PRODUCT)
    List<ItemSnapshotVersionDTO> findCurrentItemLineVersionList(@Param("itemlineIds") Set<String> itemlineIds);

    /*获取商品快照*/
    @DataSourceTarget(DataSourceKey.DATABASE_PRODUCT)
    ItemSnapshotDTO getItemSnapshotDetailInfo(@Param("itemLineId") String itemLineId, @Param("version") String version);

    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    ContractSnapshotDTO getContractLineInfo(@Param("salesContractHeadId") String salesContractHeadId, @Param("itemLineId") String itemLineId);
    
    /*获取商品图片*/
    @DataSourceTarget(DataSourceKey.DATABASE_PRODUCT)
    List<ImageDTO> findPictureList(@Param("itemId") String itemId);
    
    /*获取商品描叙列表*/
    @DataSourceTarget(DataSourceKey.DATABASE_PRODUCT)
    List<ItemDescDTO> findItemDescList(@Param("itemId") String itemId);
    
    
    
    
}
