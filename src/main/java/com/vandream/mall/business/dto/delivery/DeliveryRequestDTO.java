package com.vandream.mall.business.dto.delivery;

import com.vandream.mall.business.dto.BaseDTO;
import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
public class DeliveryRequestDTO extends BaseDTO{

    private String userId;

    private String supplierId;

    private String keyword;

    private Long startTime;

    private Long endTime;

    private Integer pageSize;

    private Integer pageNo;


}
