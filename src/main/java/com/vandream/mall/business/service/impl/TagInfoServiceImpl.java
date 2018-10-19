package com.vandream.mall.business.service.impl;

import com.google.common.base.Preconditions;
import com.vandream.mall.business.dao.homepage.TagInfoDAO;
import com.vandream.mall.business.dto.homepage.TagInfoDTO;
import com.vandream.mall.business.execption.SectionException;
import com.vandream.mall.business.service.TagInfoService;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.utils.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/31
 * @time : 9:47
 * Description:
 */
@Service(value = "tagInfoService")
public class TagInfoServiceImpl implements TagInfoService {
    private static final Logger logger = LoggerFactory.getLogger(TagInfoServiceImpl.class);

    @Autowired
    private TagInfoDAO tagInfoDAO;

    @Override
    public List<TagInfoDTO> getTagInfoList(Integer id) throws SectionException {
        if (null==id){
            logger.info("id={}",id);
            throw new SectionException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        List<TagInfoDTO> tagInfoDTOList = null;
        try {
            //根据pid获取叶子节点
            tagInfoDTOList = tagInfoDAO.queryTagInfoTreeList(id);
            for (TagInfoDTO tagInfoDTO : tagInfoDTOList) {
                getTreeTagData(tagInfoDTO);
            }
        } catch (Exception e) {
            throw new SectionException(ResultStatusConstant.SECTION_ACCESS_FAIL);
        }
        return tagInfoDTOList;
    }

    public void getTreeTagData(TagInfoDTO tagInfoDTO) {
        Preconditions.checkNotNull(tagInfoDTO);
        Preconditions.checkNotNull(tagInfoDTO.getAttrJson());
        //将查询出来的attrInfo转为Map
        Map<String, Object> attr = JSONUtil.toMap(tagInfoDTO.getAttrJson());
        tagInfoDTO.setAttr(attr);

        //根据子节点id,获取行数据
        List<TagInfoDTO> childList = null;
        if (tagInfoDTO.getChildTagInfoIds() != null) {
            childList = tagInfoDAO.queryTagInfoChildList(tagInfoDTO.getChildTagInfoIds());
        }
        attr.put("child", childList);
        if (childList != null) {
            for (TagInfoDTO child : childList) {
                getTreeTagData(child);
            }
        }
        tagInfoDTO.setAttrJson(null);
        tagInfoDTO.setChildTagInfoIds(null);

    }
}
