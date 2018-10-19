package com.vandream.mall.business.vo.delivery;

import com.github.pagehelper.PageInfo;
import com.vandream.mall.business.dto.delivery.notice.DeliveryNoticeListDTO;
import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/4/3
 * Time: 15:53
 * Description: 发货管理列表
 */
@Getter
@Setter
@Data
public class DeliveryListVO extends BaseVO{

    private Long totalSize;

    private Integer pageSize;

    private Integer pageNo;

    private List<DeliveryNoticeListDTO> noticeList;

    public DeliveryListVO(PageInfo pageInfo) {
        this.totalSize = pageInfo.getTotal();
        this.pageNo = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
    }

    public DeliveryListVO(Integer PageNo){
        this.pageNo = PageNo;
        this.totalSize = 0L;
        this.pageSize = 0;
        this.noticeList = new ArrayList<>();
    }
}
