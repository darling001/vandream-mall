package com.vandream.mall.business.vo.buyerContract;

import com.vandream.mall.business.vo.base.BaseVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/28 19:42
 */
public class FindContractListVO extends BaseVO {
    private int pageNo;
    private int totalSize;
    private List<ContractListVO> contractList = new ArrayList<>();

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<ContractListVO> getContractList() {
        return contractList;
    }

    public void setContractList(List<ContractListVO> contractList) {
        this.contractList = contractList;
    }
}
