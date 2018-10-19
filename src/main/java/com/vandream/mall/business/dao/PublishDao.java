package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.publish.Advertisement;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dingjie
 * @date 2018/10/10
 * @time 9:40
 * Description:
 */
@Component
public interface PublishDao {

    List<Advertisement> queryPublishListByCode(@Param("publishCodeList") List<String> publishCodeList);
}
