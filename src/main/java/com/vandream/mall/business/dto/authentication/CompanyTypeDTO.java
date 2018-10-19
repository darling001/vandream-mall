package com.vandream.mall.business.dto.authentication;

import com.vandream.mall.business.dto.BaseDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/20
 * @time : 17:20
 * Description:
 */
@Data
@Setter
@Getter
public class CompanyTypeDTO extends BaseDTO implements Serializable{

    private static final long serialVersionUID = -5587812175323539531L;
    /**供需方类别**/
    private String valueCode;
    /**企业类型名称**/
    private String valueName;
}
