package com.vandream.mall.business.vo.search;

import com.alibaba.fastjson.annotation.JSONField;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Li Jie
 */
@Data
@Getter
@Setter
public class SearchItemPictureVO implements Serializable {

    private static final long serialVersionUID = -229078093954576247L;

    public static final String BILL_TYPE_BRAND = "cmcBrand";
    public static final String BILL_TYPE_SPU = "cmcSPU";
    public static final String BILL_TYPE_ITEM = "cmcItem";
    public static final String BILL_TYPE_ITEM_DESC = "cmcItemDesc";

    /**
     * 商品图片有效状态
     */
    public static final String PIC_STATUS = "40";
    /**
     * 图片id
     */
    @JSONField(name = "PICTURE_ID")
    private String pictureId;
    /**
     * 单据号
     */
    @JSONField(name = "BILL_NO")
    private String billNo;
    /**
     * 单据类型
     */
    @JSONField(name = "BILL_TYPE")
    private String billType;
    /**
     * 图片名称
     */
    @JSONField(name = "PIC_NAME")
    private String picName;
    /**
     * 图片类型:图片文件格式
     */
    @JSONField(name = "PIC_TYPE")
    private String picType;
    /**
     * 文件存储路径
     */
    @JSONField(name = "FILE_PATH")
    private String filePath;
    /**
     * 图片大小(byte)
     */
    @JSONField(name = "PIC_SIZE")
    private BigDecimal picSize;
    /**
     * 原尺寸图片存储名称
     */
    @JSONField(name = "PIC_STORAGE_NAME")
    private String picStorageName;
    /**
     * 原尺寸图片外部文件系统ID
     */
    @JSONField(name = "PIC_EX_FILEID")
    private String picExFileId;
    /**
     * 小尺寸图片存储名称
     */
    @JSONField(name = "SMALL_SIZE_NAME")
    private String smallSizeName;
    /**
     * 小尺寸图片外部文件系统ID
     */
    @JSONField(name = "SMALL_SIZE_EX_FILE_ID")
    private String smallSizeExFileId;
    /**
     * 中尺寸图片存储名称
     */
    @JSONField(name = "MIDDLE_SIZE_NAME")
    private String middleSizeName;
    /**
     * 中尺寸图片外部文件系统ID
     */
    @JSONField(name = "MIDDLE_SIZE_EX_FILE_ID")
    private String middleSizeExFileId;
    /**
     * 图片状态
     */
    @JSONField(name = "PIC_STATUS")
    private String picStatus;

    /**
     * 图片序号
     */
    @JSONField(name = "PIC_ORDER")
    private int picOrder;
}
