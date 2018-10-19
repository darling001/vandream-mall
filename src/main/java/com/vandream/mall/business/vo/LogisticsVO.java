package com.vandream.mall.business.vo;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.websocket.server.ServerEndpoint;

/**
 * @author dingjie
 * @date 2018/4/4
 * @time 14:35
 * Description:
 */
@Data
@Getter
@Setter
public class LogisticsVO  extends BaseVO{
    /**
     * 物流时间
     */
    private  Long dateTime;
    /**
     * 物流信息
     */
    private  String logisticInfo;

    @Override
    public String toString() {
        return "LogisticsVO{" +
                "dateTime=" + dateTime +
                ", logisticInfo='" + logisticInfo + '\'' +
                '}';
    }
}
