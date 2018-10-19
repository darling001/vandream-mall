package com.vandream.mall.business.vo.base;

import com.github.pagehelper.PageInfo;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Li Jie
 */
@Data
@Getter
@Setter
public class DataListVO<T> extends BaseVO {
    private static final long serialVersionUID = -9074934846146457222L;
    private Integer pageNo;
    private Integer pageSize;
    private Long totalSize;
    private List<T> list;

    public DataListVO(PageInfo pageInfo) {
        this.totalSize = pageInfo.getTotal();
        this.pageNo = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
    }

}
