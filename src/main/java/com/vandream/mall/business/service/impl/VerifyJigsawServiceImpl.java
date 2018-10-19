package com.vandream.mall.business.service.impl;

import com.vandream.mall.business.execption.BusinessException;
import com.vandream.mall.business.execption.SystemException;
import com.vandream.mall.business.service.VerifyJigsawService;
import com.vandream.mall.business.vo.jigsaw.CutResultData;
import com.vandream.mall.business.vo.jigsaw.JigsawVO;
import com.vandream.mall.commons.constant.RedisKeyConstant;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.utils.ObjectUtil;
import com.vandream.mall.commons.utils.StringUtil;
import com.vandream.mall.commons.utils.ValidatorUtils;
import com.vandream.mall.commons.utils.VerifyUtil;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Li Jie
 */
@Service("verifyJigsawService")
public class VerifyJigsawServiceImpl implements VerifyJigsawService {
    private static final Logger logger = LoggerFactory.getLogger(VerifyJigsawServiceImpl.class);
    /**
     * 容差像素点
     */
    private static final Integer POINT_TOLERANCE = 22;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public JigsawVO getJigsaw(String phoneNumber) throws InvocationTargetException {
        if (StringUtil.isBlank(phoneNumber)) {
            throw new BusinessException(ResultStatusConstant.ACCOUNT_PHONE_EMPTY);
        }
        try {
            //获取拖动图形验证码 图片及坐标数据
            InputStream stream = getClass().getResourceAsStream("/verifyJigsaw" + getRandomNum() + ".png");
            CutResultData cutResultData = VerifyUtil.drawImgVerify(stream);
            JigsawVO jigsawVO = ObjectUtil.transfer(cutResultData, JigsawVO.class);
            Map<String, Object> validation = ValidatorUtils.validation(jigsawVO);
            if (validation.size() > 0) {
                throw new BusinessException(ResultStatusConstant.DATA_READ_FAIL);
            }
            // X 轴坐标存入redis
            redisTemplate.opsForValue().set(phoneNumber + RedisKeyConstant.DRAW_IMG_POINT_X,
                    jigsawVO.getPointX(), 3, TimeUnit.MINUTES);
            //X 轴坐标不返回前端
            jigsawVO.setPointX(null);
            return jigsawVO;
        } catch (Exception e) {
            logger.error("{}", e.getMessage(), e);
            throw new SystemException();
        }
    }

    public int getRandomNum() {
        Random random = new Random();
        int num = random.nextInt(3) + 1;
        return num;
    }

    @Override
    public boolean verifyJigsaw(String phoneNumber, Integer pointX) throws
            InvocationTargetException {
        if (StringUtil.isBlank(phoneNumber)) {
            throw new BusinessException(ResultStatusConstant.ACCOUNT_PHONE_EMPTY);
        }
        if (pointX == null || pointX == 0) {
            throw new BusinessException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        //从redis 取回原始X坐标切点
        Integer srcPointX = (Integer) redisTemplate.opsForValue().get(phoneNumber + RedisKeyConstant
                .DRAW_IMG_POINT_X);
        if (srcPointX == null || srcPointX == 0) {
            throw new BusinessException(ResultStatusConstant.DATA_READ_FAIL);
        }
        if (verifyPoint(pointX, srcPointX)) {
            return true;
        } else {
            throw new BusinessException(ResultStatusConstant.VERIFY_JIGSAW_FAILED);
        }

    }

    private boolean verifyPoint(Integer point, Integer basePoint) {
        Integer min = basePoint - POINT_TOLERANCE;
        Integer max = basePoint + POINT_TOLERANCE;
        if (point >= min && point <= max) {
            return true;
        }
        return false;
    }
}
