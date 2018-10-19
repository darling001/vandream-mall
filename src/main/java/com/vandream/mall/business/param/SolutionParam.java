package com.vandream.mall.business.param;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author dingjie
 * @date 2018/6/4
 * @time 17:30
 * Description:
 */
@Data
@Getter
@Setter
public class SolutionParam extends BaseVO {
    @NotBlank(message = "用户id不能为空")
    private String userId;
    @NotBlank(message = "用户企业id不能为空")
    private String supplierId;
    private String keyword;
    private Long dispatchStartDate;
    private Long dispatchEndDate;
    private String status;
    private Integer pageSize=15;
    private Integer pageNo=1;
}
