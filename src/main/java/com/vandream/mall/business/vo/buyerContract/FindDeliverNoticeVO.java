package com.vandream.mall.business.vo.buyerContract;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/29 16:10
 */
@Data
public class FindDeliverNoticeVO extends BaseVO {

    private String id;
    private String code;
    private String itemName;
    private Long noticeDate;
    private Long expectedDate;
    private String contact;
    private String contactPhone;
    private String isRead;
    private int goodsNum;

    @Override
    public String toString() {
        return "FindDeliverNoticeVO{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", itemName='" + itemName + '\'' +
                ", noticeDate='" + noticeDate + '\'' +
                ", expectedDate='" + expectedDate + '\'' +
                ", contact='" + contact + '\'' +
                ", contactPhtone='" + contactPhone + '\'' +
                ", istRead='" + isRead + '\'' +
                '}';
    }
}
