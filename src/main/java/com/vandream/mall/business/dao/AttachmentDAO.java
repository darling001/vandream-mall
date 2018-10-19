package com.vandream.mall.business.dao;


import com.vandream.mall.business.dto.aus.AttachmentDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * @author Li Jie
 */
@Component
public interface AttachmentDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    AttachmentDTO selectByPrimaryKey(String attachmentId);

    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<AttachmentDTO> findByBillNo(Map<String, Object> attachParaMap);

    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<AttachmentDTO> findList(String attachmentType,String businessType, List<String> billNoList);

}