package com.vandream.mall.business.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vandream.mall.business.dao.PublishDao;
import com.vandream.mall.business.dto.publish.Advertisement;
import com.vandream.mall.business.dto.publish.ImageInfo;
import com.vandream.mall.business.dto.publish.PublishCodeRedis;
import com.vandream.mall.business.dto.publish.PublishContentInfo;
import com.vandream.mall.business.execption.PublishException;
import com.vandream.mall.business.service.PublishService;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.utils.JSONUtil;
import com.vandream.mall.commons.utils.ObjectUtil;
import com.vandream.mall.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author dingjie
 * @date 2018/10/10
 * @time 9:28
 * Description:
 */
@Service("publishService")
public class PublishServiceImpl implements PublishService {

    @Resource
    private RedisTemplate redisTemplate;
    @Autowired
    private PublishDao publishDao;
    @Override
    public List<Advertisement> previewAdvContent(List<String> publishCodeList) throws PublishException {
            if(ObjectUtil.isEmpty(publishCodeList)){
                throw new PublishException(ResultStatusConstant.INPUT_PARAM_ERROR);
            }
             List<Advertisement> advertisements = publishDao.queryPublishListByCode(publishCodeList);
            if(ObjectUtil.isNotEmpty(advertisements)){
                for (Advertisement advertisement:advertisements) {
                    String publishKey="cms_publish_"+advertisement.getAdvertisementCode();
                    List<ImageInfo> imageInfoList =new ArrayList<>();
                    //查看redis中是否含当前key
                    if(redisTemplate.hasKey(publishKey)){
                        //获取publish信息转成json并循环读取图片地址与描述信息
                        Object publishContent= redisTemplate.opsForValue().get(publishKey);
                        if(ObjectUtil.isNotEmpty(publishContent)){
                            PublishContentInfo publishContentInfo =JSON.parseObject(publishContent.toString(),
                                    PublishContentInfo.class);
                            List<PublishCodeRedis> publishCodeRedisList = publishContentInfo.getList();
                            if(ObjectUtil.isNotEmpty(publishCodeRedisList)){
                                for (int i=0;i<publishCodeRedisList.size();i++){
                                    ImageInfo imageInfo=new ImageInfo();
                                    PublishCodeRedis publishCodeRedis = publishCodeRedisList.get(i);
                                    String imageUrl = publishCodeRedis.getImageUrl();
                                    String desc = publishCodeRedis.getDesc();
                                    if(StringUtil.isNotBlank(imageUrl)){
                                        imageInfo.setImageUrl(imageUrl);
                                    }
                                    if(StringUtil.isNotBlank(desc)){
                                        imageInfo.setDescription(desc);
                                    }
                                    imageInfoList.add(imageInfo);
                                }
                            }
                        }
                    }
                    advertisement.setList(imageInfoList);
                }
            }
        return advertisements;
    }
}
