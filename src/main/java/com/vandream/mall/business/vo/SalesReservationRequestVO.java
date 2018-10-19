package com.vandream.mall.business.vo;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/4/4
 * Time: 17:57
 * Description:
 */
@Setter
@Getter
@Data
public class SalesReservationRequestVO extends BaseVO{

    /** 用户id **/
    @NotBlank(message = "用户id为空")
    private String userId;

    /** 供方id **/
    @NotBlank(message = "供方id不能为空")
    private String supplierId;

    /** 搜索关键词 **/
    private String keyword;

    /** 通知起始时间 **/
    private Long notifyStartTime;

    /** 通知结束时间 **/
    private Long notifyEndTime;

    /** 单页记录条数 **/
    private Integer pageSize;

    /** 当前页码数 **/
    private Integer pageNo;

}
