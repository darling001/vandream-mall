package com.vandream.mall.business.vo.jigsaw;

import com.vandream.mall.business.vo.base.BaseVO;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Li Jie
 */
@Data
@Getter
@Setter
public class JigsawVO extends BaseVO {
    private static final long serialVersionUID = 4253005192573917114L;
    /**
     * 拖动目标图，大图
     */
    @NotBlank
    private String tarImg;
    /**
     * 被拖动的图
     */
    @NotBlank
    private String drawImg;

    /**
     * 裁剪左上角Y坐标
     */
    @NotNull
    private Integer pointY;
    /**
     * 裁剪右上角X坐标。
     */
    private Integer pointX;
    /**
     * 原始图片宽度
     */
    @NotNull
    private Integer srcWidth;

    /**
     * 原始图片高度
     */
    @NotNull
    private Integer srcHeight;
}
