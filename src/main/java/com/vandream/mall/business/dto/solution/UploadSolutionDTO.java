package com.vandream.mall.business.dto.solution;

import com.vandream.mall.business.dto.BaseDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.index.PathBasedRedisIndexDefinition;

/**
 * @author dingjie
 * @date 2018/6/5
 * @time 14:20
 * Description:
 */
@Data
@Setter
@Getter
public class UploadSolutionDTO extends BaseDTO {
    /**
     * 来源类别
     */
    private String fromType;
    /**
     * 登录账号ID
     */
        private String loginUserId;
    /**
     * 登录账号名称
     */
    private String loginUserName;
    /**
     * 派发单主键
     */
    private String solutionId;
    /**
     * 供方解决方案主键ID
     */
    private String solutionSupplierId;

}
