package com.vandream.mall.business.vo.delivery;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/4/3
 * Time: 15:57
 * Description: 请求参数VO类
 */
@Setter
@Getter
@Data
public class DeliveryRequestVO extends BaseVO{

    /** 用户id **/
    @NotBlank(message = "用户id为空")
    private String userId;

    /** 供方id **/
    @NotBlank(message = "supplierId不能为空")
    private String supplierId;

    /** 搜索关键词 **/
    private String keyword;

    /** 起始时间 **/
    private Long startTime;

    /** 结束时间 **/
    private Long endTime;

    /** 每页记录条数 **/
    private Integer pageSize = 10;

    /** 当前页 **/
    private Integer pageNo = 1;


}
