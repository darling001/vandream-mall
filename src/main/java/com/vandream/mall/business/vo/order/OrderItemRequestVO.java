package com.vandream.mall.business.vo.order;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;

/**
 * Created with IntelliJ IDEA
 *订单商品匹配查询
 * @author : liguoqing
 * @date : 2018/7/10
 * Time: 9:45
 * Description:
 */
@Data
public class OrderItemRequestVO extends BaseVO {
    /*商品id列表*/
    private Object itemList;
    /*省编码*/
    private String provinceCode;
    /*省名称*/
    private String provinceName;
    /*市编码*/
    private String cityCode;
    /*市名称*/
    private String cityName;
    /*区县编码*/
    private String countyCode;
    /*市名称*/
    private String countyName;
    /*当前用户userId*/
    private String  userId;
    
    
    
    
}
