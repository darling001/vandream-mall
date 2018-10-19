package com.vandream.mall.business.vo.buyerContract;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/4/10 13:56
 */
@Data
@Setter
@Getter
public class FindPaymentVO extends BaseVO{
    private Long uploadTime;
    private String attachmentName;
    private String attachmentPath;
}
