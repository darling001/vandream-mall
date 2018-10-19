package com.vandream.mall.business.dao;

import com.vandream.mall.business.vo.buyerContract.FindDeliverInfoVO;
import com.vandream.mall.business.vo.buyerContract.FindDeliverNoticeVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/29 16:29
 */
@Component
public interface FindDeliverDAO {

    List<FindDeliverNoticeVO> findDeliverNoticeList(@Param("userId") String userId,
                                                    @Param("companyId") String companyId,
                                                    @Param("contractId") String contractId);

    List<FindDeliverInfoVO> findDeliverInfoList(@Param("userId") String userId,
                                                @Param("companyId") String companyId,
                                                @Param("contractId") String contractId);
}
