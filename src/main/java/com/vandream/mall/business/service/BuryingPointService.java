package com.vandream.mall.business.service;

import com.vandream.mall.business.vo.subAccount.SubAccountVO;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created with IntelliJ IDEA
 *
 * @author yu yingxing
 * @date 2018/6/28
 * @time 17:15
 * @description 埋点记录API
 */
public interface BuryingPointService {

    /**
     * 埋点记录
     * @param bpn 来源 buryingPointName
     * @throws Exception
     */
    Boolean record(String bpn);

    /**
     * 将一个周期内的记录从redis中取出放入到sql中
     * @throws Exception
     */
    void cycleRecordToSqlHandler() throws Exception;




}
