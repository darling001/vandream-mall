package com.vandream.mall.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.vandream.mall.business.dao.SnapshotDAO;
import com.vandream.mall.business.dao.UserLevelDAO;
import com.vandream.mall.business.dto.item.ImageDTO;
import com.vandream.mall.business.dto.item.ItemDescDTO;
import com.vandream.mall.business.dto.item.ItemDetailDTO;
import com.vandream.mall.business.dto.mallCart.GoodsParams;
import com.vandream.mall.business.dto.snapshot.ContractSnapshotDTO;
import com.vandream.mall.business.dto.snapshot.ItemSnapshotDTO;
import com.vandream.mall.business.dto.snapshot.ItemSnapshotVersionDTO;
import com.vandream.mall.business.execption.BusinessException;
import com.vandream.mall.business.execption.ItemDetailException;
import com.vandream.mall.business.execption.SystemException;
import com.vandream.mall.business.service.SnapshotService;
import com.vandream.mall.business.service.ValueSetService;
import com.vandream.mall.business.vo.ItemSnapshotVO;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.utils.JSONUtil;
import com.vandream.mall.commons.utils.ObjectUtil;
import com.vandream.mall.commons.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created with IntelliJ IDEA
 * 快照服务类
 *
 * @author : liguoqing
 * @date : 2018/7/24
 * Time: 16:34
 * Description:
 */

@Service("snapshotService")
public class SnapshotServiceImpl implements SnapshotService {
    private static final Logger logger = LoggerFactory.getLogger(SnapshotServiceImpl.class);

    //商品图片
    private static final String ITEM_IAMGE = "cmcItem";

    //商品描述图片
    private static final String ITEM_DESC_IMAGE = "cmcItemDesc";

    @Resource
    private SnapshotDAO snapshotDao;
    @Resource
    private UserLevelDAO userLevelDAO;
    @Autowired
    private ValueSetService valueSetService;


    /**
     * 获取当前商品行最新版本
     *
     * @param itemLineList
     * @return
     */
    public List<ItemSnapshotVersionDTO> findCurrentItemLineVersionList(Set<String> itemLineList) {
        return snapshotDao.findCurrentItemLineVersionList(itemLineList);
    }


    @Override
    public Map<String, Object> getItemLineMap(Set<String> itemLineList) {
        List<ItemSnapshotVersionDTO> itemLineVersionDTOS = findCurrentItemLineVersionList(itemLineList);
        Map itemMap = new HashMap();
        itemLineVersionDTOS.forEach(itemLineVersionDTO -> {
            itemMap.put(itemLineVersionDTO.getItemLineId(), itemLineVersionDTO.getItemLineVersion());
        });
        return itemMap;
    }

    /**
     * 获取商品快照
     *
     * @param salesContractHeadId
     * @param itemLineId
     * @param version
     * @return
     * @throws BusinessException
     */
    @Override
    public ItemSnapshotVO getItemSnapshot(String salesContractHeadId, String itemLineId, String version) throws BusinessException {
        //入参校验
        if (ObjectUtil.isEmpty(salesContractHeadId)) {
            logger.info("销售订单id为空值", salesContractHeadId);
            throw new BusinessException(ResultStatusConstant.SOM_SALES_CONTRACT_HEAD_ID_IS_NULL);
        }
        //入参校验
        if (ObjectUtil.isEmpty(itemLineId) || ObjectUtil.isEmpty(version)) {
            logger.info("商品itemLineId 为空", itemLineId);
            throw new BusinessException(ResultStatusConstant.BUSINESS_ID_IS_EMPTY);
        }
        //查询快照基本信息
        ItemSnapshotDTO itemSnapshotDTO = snapshotDao.getItemSnapshotDetailInfo(itemLineId, version);
        if (ObjectUtil.isEmpty(itemSnapshotDTO)) {
            logger.info("获取商品快照为空", itemSnapshotDTO);
            throw new BusinessException(ResultStatusConstant.ITEM_SNAPSHOT_DETAIL_EMPTY);
        }

        //查询图片列表
        List<ImageDTO> imageDTOList = snapshotDao.findPictureList(itemSnapshotDTO.getItemId());

        //获取商品标题
        String itemShortName = itemSnapshotDTO.getItemShortName();
        //优先展示商品标题
        if (StringUtil.isNotBlank(itemShortName)) {
            itemSnapshotDTO.setItemName(itemShortName);
        }
        //查询商品订单信息
        ContractSnapshotDTO contractLineInfo = snapshotDao.getContractLineInfo(salesContractHeadId, itemLineId);
        BigDecimal itemLinePrice = null;
        Date orderDate = null;
        if (ObjectUtil.isNotEmpty(contractLineInfo)) {
            itemLinePrice = contractLineInfo.getPrice();
            orderDate = contractLineInfo.getCreateDate();
        }

        //商品计量单位中英文映射关系
        Map<String, String> itemUnitTypeMap = valueSetService.getItemUnitTypeNameMap();
        String primaryUnitCode = itemSnapshotDTO.getPrimaryUnitCode();
        String unit = itemUnitTypeMap.get(primaryUnitCode);

        //获取快照中的规格参数
        String specContent = itemSnapshotDTO.getSpecContents();
        List<GoodsParams> specContentList = null;
        if (StringUtil.isNotBlank(specContent)) {
            specContentList = JSONUtil.toList(specContent, GoodsParams.class);
        }

        //获取快照中的商品描述
        String descContents = itemSnapshotDTO.getDescContents();
        List<ItemDescDTO> itemDescDTOList = null;
        if (StringUtil.isNotBlank(descContents)) {
            itemDescDTOList = JSONUtil.toList(descContents, ItemDescDTO.class);
        }
        //设置返回前台的商品图片列表
        ArrayList<ImageDTO> imageList = new ArrayList<>();

        //设置返回前台的商品描述图片列表
        ArrayList<ImageDTO> itemDescImageList = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(imageDTOList)) {
            //遍历
            imageDTOList.forEach(imageDTO -> {
                if (ITEM_DESC_IMAGE.equals(imageDTO.getBillType())) {
                    //是商品描述图片
                    itemDescImageList.add(imageDTO);
                } else if (ITEM_IAMGE.equals(imageDTO.getBillType())) {
                    //是商品图片
                    imageList.add(imageDTO);
                }
            });
        }
        //查询商品描述列表
        ItemSnapshotVO itemSnapshotVO = null;
        try {
            //将查询出的数据库转换为返回前台对象
            itemSnapshotVO = ObjectUtil.transfer(itemSnapshotDTO, ItemSnapshotVO.class);
        } catch (SystemException e) {
            logger.info("商品快照数据转换异常" + JSONUtil.toJson(itemSnapshotDTO));
            throw new BusinessException(ResultStatusConstant.PARAM_FORMAT_FAILURE);
        }
        //设置商品图片列表
        itemSnapshotVO.setImageList(imageList);
        //设置商品行价格
        itemSnapshotVO.setItemLinePrice(itemLinePrice);
        //设置商品计量单位
        itemSnapshotVO.setUnit(unit);
        //设置订单日期
        itemSnapshotVO.setOrderDate(orderDate);
        //设置商品规格参数
        itemSnapshotVO.setSpecContentList(specContentList);
        ItemDetailDTO descDetail = new ItemDetailDTO();
        //设置商品详情描述列表
        descDetail.setItemDescDTOList(itemDescDTOList);
        //设置商品描述图片列表
        descDetail.setImageDTOList(itemDescImageList);
        //设置商品描述
        itemSnapshotVO.setItemDesc(descDetail);
        return itemSnapshotVO;
    }

}
