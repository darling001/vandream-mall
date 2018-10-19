package com.vandream.mall.business.vo.authentication;

import com.vandream.mall.business.domain.Attachment;
import com.vandream.mall.business.vo.base.BaseVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/20
 * @time : 20:28
 * Description:
 * 供方VO
 */
@Data
@Setter
@Getter
public class SupplierVO extends BaseVO {

    private static final long serialVersionUID = -6650951757567265300L;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 当前登录用户名称
     */
    private String userName;
    /**
     * 企业名称
     */
    private String companyName;
    /**
     * 企业类别
     */
    private String supplierType;
    /**
     * 企业联系人
     */
    private String contacts;
    /**
     * 联系电话
     */
    private String contactNumber;
    /**
     * 联系人职务
     */
    private String position;
    /**
     * 经营区域
     */
    private String businessArea;
    /**
     * 经营品类
     */
    private String businessCategory;
    /**
     * 主营产品
     */
    private String mainProduct;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 三证合一 0:老版，1：新版
     */
    private String certificateType;
    /**
     * 统一社会信用代码或营业执照
     */
    private String creditCode;
    /**
     * 附件对象列表
     */
    private List<Attachment> attachmentList = new ArrayList<>();



}
