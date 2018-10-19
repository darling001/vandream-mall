package com.vandream.mall.business.execption;


import com.vandream.mall.commons.constant.ResultStatusConstant;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/14
 * @time : 16:27
 * Description:
 */
public class AdvertisementException extends BusinessException {

    private static final long serialVersionUID = -7088480193465424402L;

    public AdvertisementException(ResultStatusConstant resultStatusConstant) {
        super.setStatus(resultStatusConstant.getStatus());
        super.setMessage(resultStatusConstant.getDesc());
        super.setCode(resultStatusConstant.getCode());
    }
}
