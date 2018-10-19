package com.vandream.mall.business.domain;

import com.vandream.mall.business.vo.base.BaseVO;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/21
 * @time : 16:52
 * Description:
 * 企业认证附件对象
 */
@Data
@Setter
@Getter
public class Attachment extends BaseVO implements Serializable {

    private static final long serialVersionUID = -6746631287741026263L;
    /**附件名称**/
    @NotNull
    private String attachmentName;
    /**附件类型**/
    @NotNull
    private String attachmentType;
    /**附件路径**/
    @NotNull
    private String attachmentPath;
    /**文件类型**/
    @NotNull
    private String fileType;
    /**文件大小**/
    @NotNull
    @Min(1)
    private BigDecimal fileSize;
}
