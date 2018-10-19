package com.vandream.mall.business.vo.subAccount;

import com.github.pagehelper.PageInfo;
import com.vandream.mall.business.dto.subAccount.SubAccountListDTO;
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
 * @date : 2018/5/25
 * Time: 14:11
 * Description:
 */
@Data
@Getter
@Setter
public class SubAccountsVO extends BaseVO {

    /** 主账户id **/
    private String parentAccountId;

    /** 总记录数 **/
    private Long totalSize;

    /** 当前页码 **/
    private Integer pageNo;

    /** 当前页数 **/
    private Integer pageSize;

    /** 子账户信息列表 **/
    private List<SubAccountListDTO> subAccountList;

    public SubAccountsVO(){

    }

    public SubAccountsVO(PageInfo pageInfo){
        totalSize = pageInfo.getTotal();
        pageNo = pageInfo.getPageNum();
        pageSize = pageInfo.getPageSize();
    }

    public SubAccountsVO(Integer PageNo) {
        this.pageNo = PageNo;
        this.totalSize = 0L;
        this.pageSize = 0;
        this.subAccountList = new ArrayList<SubAccountListDTO>();
    }
}
