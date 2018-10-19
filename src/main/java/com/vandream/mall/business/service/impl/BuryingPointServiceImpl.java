package com.vandream.mall.business.service.impl;

import com.vandream.mall.business.dao.BuryingPointRecordDao;
import com.vandream.mall.business.dto.BuryingPointRecordDTO;
import com.vandream.mall.business.service.BuryingPointService;
import com.vandream.mall.business.service.RedisService;
import com.vandream.mall.commons.utils.DateUtils;
import com.vandream.mall.commons.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 *
 * @author yu yingxing
 * @date 2018/6/28
 * @time 17:20
 * @time 17:20
 * @description 埋点记录impl
 */
@Service(value = "buryingPointService")
public class BuryingPointServiceImpl implements BuryingPointService {

    private final static Logger logger = LoggerFactory.getLogger(BuryingPointServiceImpl.class);

    private final static String BURYING_POINT_KEY_MAP = "BURYING_POINT_KEY_MAP";

    private final static String FILTER_REGEX = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】'；：”“’。，、？  ]";

    @Autowired
    private RedisService redisService;
    @Autowired
    private BuryingPointRecordDao buryingPointRecordDao;

    @Override
    public Boolean record(String bpn) {
        Boolean result = true;
        if (StringUtil.isNotBlank(bpn)){
            try{

                bpn = StringUtil.removePattern(bpn,FILTER_REGEX);
                redisService.hincr(BURYING_POINT_KEY_MAP,bpn,1);

            }catch(Exception e ){
                logger.error("埋点记录失败：{}",e.getMessage(),e);
                result = false;
            }
        }
        return result;
    }


    @Transactional(rollbackFor = Exception.class)
    public void cycleRecordToSqlHandler()throws Exception{

        Date cur = new Date();
        String dayStr = DateUtils.formatDate(cur,"yyyy-MM-dd");
        logger.info("============开始，埋点日统计入库，当前时间是{}",DateUtils.formatDateTime(cur));


        Map<String,Integer> map = redisService.hmget(BURYING_POINT_KEY_MAP,Integer.class);

        List<BuryingPointRecordDTO> list = new ArrayList<BuryingPointRecordDTO>();
        if(null != map && map.size() > 0){
            for(Map.Entry<String,Integer> entry : map.entrySet()){
                BuryingPointRecordDTO brd = new BuryingPointRecordDTO();
                brd.setBuryingPointKey(entry.getKey());
                brd.setTodayClickCount(entry.getValue());
                brd.setClickCountDate(dayStr);
                brd.setCreateTime(cur);
                list.add(brd);
                map.put(entry.getKey(),0);
            }
            buryingPointRecordDao.batchInsertRecord(list);

            //数据库insert成功才会删除。如果插入没有成功，则不会走到置零这里就会失败。
            //redisService.del(BURYING_POINT_KEY_MAP);
            redisService.hmset(BURYING_POINT_KEY_MAP,map);
        }
        logger.info("============完成！埋点每日统计入库，本次入库数目：{}",list.size());
    }
}