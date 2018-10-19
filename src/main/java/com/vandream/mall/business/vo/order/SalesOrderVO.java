package com.vandream.mall.business.vo.order;

import com.vandream.mall.business.dto.order.OrderSOMItemLineDTO;
import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author dingjie
 * @date 2018/7/10
 * @time 10:54
 * Description:
 */
@Data
@Getter
@Setter
public class SalesOrderVO extends BaseVO{

        @NotBlank(message = "用户id不能为空")
        private String userId;
        @NotNull(message = "商品列表数据不能为空")
        private List itemLineList;
        @NotBlank(message = "需方企业id不能为空")
        private String customerId;
        @NotBlank(message ="省编码不能为空")
        private String provinceCode;
        @NotBlank(message = "省名称不能为空")
        private String provinceName;
        @NotBlank(message ="市编码不能为空")
        private String cityCode;
        @NotBlank(message = "市名称不能为空")
        private String cityName;
        @NotBlank(message = "区县编码不能为空")
        private String countyCode;
        @NotBlank(message = "区县名称不能为空")
        private String countyName;
        @NotBlank(message = "详细地址不能为空")
        private String address;
        @NotBlank(message = "收货人不能为空")
        private String receiver;
        @NotBlank(message = "收货人联系方式不能为空")
        private String receiverMobile;
        @NotNull(message = "交付截止期限不能为空")
        private Long deliveryEndDate;
}
