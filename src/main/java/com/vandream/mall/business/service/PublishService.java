package com.vandream.mall.business.service;


import com.vandream.mall.business.dto.publish.Advertisement;
import com.vandream.mall.business.execption.PublishException;

import java.util.List;

/**
 * @author dingjie
 * @date 2018/10/10
 * @time 9:18
 * Description:
 */
public interface PublishService {
     List<Advertisement> previewAdvContent(List<String> publishCodeList) throws PublishException;
}
