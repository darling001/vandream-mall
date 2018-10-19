package com.vandream.mall.business.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Li Jie
 */
@Data
@Getter
@Setter
public class BxApiResult implements Serializable {
    private static final long serialVersionUID = 2324209656925830312L;
    public static final String  DO_SUCCESS = "1";
    public static final String  DO_FAILURE = "0";
    /**
     * 执行结果 0-失败1-成功
     */
    private Integer status;
    /**
     * 异常代码
     */
    private String tipsCode;
    /**
     * 结果描述
     */
    private String message;
    /**
     * 业务主键ID
     */
    private String pkId;
    /**
     * 外部平台返回的请求ID
     */
    private String outRequestId;
    /**
     * 发送回执ID  阿里云短信：可根据该ID查询具体的发送状态
     */
    private String outBizId;

}
