package com.vandream.mall.business.dao.homepage;

import com.vandream.mall.business.dto.homepage.HelpAndSolutionDTO;
import com.vandream.mall.business.dto.homepage.InformationDetailDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/4/11
 * @time : 20:35
 * Description:
 */
@Mapper
public interface HelpAndSolutionDAO {

    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    List<HelpAndSolutionDTO> findHelpAndSolutionList(Integer type);

    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    HelpAndSolutionDTO getInformationDetail(Integer id);

    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    List<InformationDetailDTO> findInformationDetailList(Integer id);
}
