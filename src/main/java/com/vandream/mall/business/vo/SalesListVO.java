package com.vandream.mall.business.vo;

import com.github.pagehelper.PageInfo;
import com.vandream.mall.business.dto.SalesReservationDTO;
import com.vandream.mall.business.vo.base.BaseVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/3/29
 * Time: 23:23
 * Description:
 */
@Getter
@Setter
@Data
public class SalesListVO extends BaseVO implements Serializable{

    private Integer pageNo;

    private long totalSize;

    private List<SalesReservationDTO> stockList;

    private Integer pageSize;

    public SalesListVO(PageInfo pageInfo) {
        this.totalSize = pageInfo.getTotal();
        this.pageNo = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
    }

    public SalesListVO(Integer PageNo){
        this.pageNo = PageNo;
        this.totalSize = 0;
        this.pageSize = 0;
        this.stockList = new ArrayList<>();
    }
}
