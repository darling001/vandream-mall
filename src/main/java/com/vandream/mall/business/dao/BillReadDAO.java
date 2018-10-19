package com.vandream.mall.business.dao;

import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/5/22
 * Time: 19:44
 * Description:
 */
@Component
public interface BillReadDAO {

    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    Integer selectBillReadCount(@Param("userId") String userId, @Param("deliveryHeadId") String deliveryHeadId);

}
