package com.vandream.mall.business.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dingjie
 * @date 2018/3/20
 * @time 17:33
 * Description:
 */
@Data
@Getter
@Setter
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class BaseDTO implements Serializable {
    private static final long serialVersionUID = 2691085131592070710L;
    /**
     * 操作人id
     */
    private String operatorUserId;
    /**
     * 操作人名称
     */
    private String operatorUserName;
}
