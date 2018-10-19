package com.vandream.mall.business.dao.delivery;

import com.vandream.mall.business.dto.delivery.DeliverySubLineDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA
 *
 * @author : liguoqing
 * @date : 2018/4/11
 * Time: 11:09
 * Description:
 */
@Component
public interface DeliveryNoticeLineDAO {
    /*返回发货通知单详情*/
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    DeliverySubLineDTO selectNoticeInfo(Map noticeMap);
}
