package com.vandream.mall.business.vo.jigsaw;

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
public class CutResultData implements Serializable {
    private static final long serialVersionUID = 2027982024434943660L;
    /**
     * 拖动目标图，大图
     */
    private String tarImg;
    /**
     * 被拖动的图
     */
    private String drawImg;

    /**
     * 裁剪左上角Y坐标
     */
    private Integer pointY;
    /**
     * 裁剪右上角X坐标。
     */
    private Integer pointX;
    /**
     * 原始图片宽度
     */
    private Integer srcWidth;
    /**
     * 原始图片高度
     */
    private Integer srcHeight;
}
