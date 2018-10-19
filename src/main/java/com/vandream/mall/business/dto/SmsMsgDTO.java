package com.vandream.mall.business.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author dingjie
 * @date 2018/3/20
 * @time 20:45
 * Description:
 */
@Data
@Setter
@Getter
public class SmsMsgDTO extends BaseDTO {
    /**
     * 手机号
     */
    private String phoneNumbers;
    /**
     * 短信内容参数
     */
    private Map params;
    /**
     * 短信模版ID
     */
    private String templateCode;
    /**
     * 外部流水扩展字段
     */
    private String outId;
    /**
     * 上行短信扩展码
     */
    private String smsUpExtendCode;
}
