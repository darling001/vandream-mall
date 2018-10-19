package com.vandream.mall.business.vo;

import com.alibaba.fastjson.JSON;
import com.vandream.mall.business.vo.base.BaseVO;
import com.vandream.mall.commons.annotation.FieldAlias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dingjie
 * @date 2018/4/4
 * @time 14:45
 * Description:
 */
@Data
@Getter
@Setter
public class AttachmentVO extends BaseVO {
    /**
     * 附件名称
     */
    @FieldAlias("fileName")
    private String attachmentName;
    /**
     * 附件路径
     */
    @FieldAlias("filePath")
    private String attachmentPath;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
