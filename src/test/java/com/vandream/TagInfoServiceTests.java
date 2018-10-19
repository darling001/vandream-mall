package com.vandream;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/30
 * @time : 13:54
 * Description:
 */

import com.google.common.base.Preconditions;
import com.vandream.mall.business.dao.homepage.TagInfoDAO;
import com.vandream.mall.business.dto.homepage.TagInfoDTO;
import com.vandream.mall.business.execption.SectionException;
import com.vandream.mall.business.service.TagInfoService;
import com.vandream.mall.commons.utils.JSONUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TagInfoServiceTests {

    @Resource
    private TagInfoDAO tagInfoDAO;
    @Autowired
    private TagInfoService tagInfoServiceImpl;

    //Service Test
    @Test
    public void getTagInfoList() throws SectionException {

        List<TagInfoDTO> tagInfoList = tagInfoServiceImpl.getTagInfoList(0);
        System.out.println(JSONUtil.toJson(tagInfoList));

        // List<TagInfoDTO> tagInfoList = tagInfoServiceImpl.getTagInfoList();
        //System.out.println("JSONUtil.toJson(tagInfoList) = " + JSONUtil.toJson(tagInfoList));
    }

    //DAO Test
    @Test
    public void testTag1() {
        //根据id查出root节点对象
        //TagInfoDTO tagInfoModelDTO = tagInfoDAO.queryTagInfoModel(16);
        //根据父id查出root节点响应的子节点
        List<TagInfoDTO> tagInfoDTOList = tagInfoDAO.queryTagInfoTreeList(16);
        for (TagInfoDTO tagInfoDTO : tagInfoDTOList) {
           /* System.out.println("tagInfo = " + tagInfo.getChildTagInfoIds());
            List<TagInfo> childList = tagInfoDao.queryTagInfoChildList(tagInfo.getChildTagInfoIds());
            tagInfo.setTagInfoList(childList);
            System.out.println("childList = " + JSONUtil.toJson(tagInfo));*/
            getTreeTagData(tagInfoDTO);
            System.out.println("JSONUtil.toJson(list) = " + JSONUtil.toJson(tagInfoDTOList));
        }

    }

    //DAO Test
//    @Test
//    public void testTag() {
//        List<TagInfoDTO> list = tagInfoDAO.queryTagInfoTreeList(16);
//        for (TagInfoDTO tagInfoDTO : list) {
//           /* System.out.println("tagInfo = " + tagInfo.getChildTagInfoIds());
//            List<TagInfo> childList = tagInfoDao.queryTagInfoChildList(tagInfo.getChildTagInfoIds());
//            tagInfo.setTagInfoList(childList);
//            System.out.println("childList = " + JSONUtil.toJson(tagInfo));*/
//            getTreeTagData(tagInfoDTO);
//            System.out.println("JSONUtil.toJson(list) = " + JSONUtil.toJson(list));
//        }
//    }
//
//
    public void getTreeTagData(TagInfoDTO tagInfoDTO) {
        Preconditions.checkNotNull(tagInfoDTO);
        Preconditions.checkNotNull(tagInfoDTO.getAttrJson());

        Map<String, Object> attr = JSONUtil.toMap(tagInfoDTO.getAttrJson());
        tagInfoDTO.setAttr(attr);

        List<TagInfoDTO> childList = null;
        if (tagInfoDTO.getChildTagInfoIds() != null) {
            childList = tagInfoDAO.queryTagInfoChildList(tagInfoDTO.getChildTagInfoIds());
        }
        tagInfoDTO.getAttr().put("child", childList);
        if (childList != null) {
            for (TagInfoDTO child : childList) {
                getTreeTagData(child);
            }
        }
        tagInfoDTO.setAttrJson(null);
        tagInfoDTO.setChildTagInfoIds(null);
    }

}
