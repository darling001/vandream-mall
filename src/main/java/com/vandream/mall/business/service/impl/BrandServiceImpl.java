package com.vandream.mall.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vandream.mall.business.dao.homepage.BrandDAO;
import com.vandream.mall.business.dto.homepage.BrandDTO;
import com.vandream.mall.business.execption.BusinessException;
import com.vandream.mall.business.service.BrandService;
import com.vandream.mall.business.vo.base.DataListVO;
import com.vandream.mall.business.vo.homepage.BrandDetailVO;
import com.vandream.mall.business.vo.homepage.BrandListVO;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.utils.JSONUtil;
import com.vandream.mall.commons.utils.ObjectUtil;
import com.vandream.mall.commons.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/4/10
 * @time : 18:29
 * Description:
 */
@Service(value = "brandService")
public class BrandServiceImpl implements BrandService {
    private static final Logger logger = LoggerFactory.getLogger(BrandServiceImpl.class);
    private static final String WINDOWS_LINE_BREAK = "\r\n";
    private static final String UNIX_LINE_BREAK = "\n";
    private static final String MAC_OS_LINE_BREAK = "\r";
    @Resource
    private BrandDAO brandDAO;

    @Override
    public DataListVO<BrandListVO> findBrandList(Integer pageSize, Integer pageNo) throws InvocationTargetException {
        if (null == pageNo || pageNo == 0) {
            pageNo = 1;
        }
        if (null == pageSize || pageSize == 0) {
            pageSize = 10;
        }

        PageHelper.startPage(pageNo, pageSize);
        List<BrandDTO> brandDTOList = null;
        try {
            brandDTOList = brandDAO.findBrandList();
            //获取分页信息对象
            PageInfo<BrandDTO> pageInfo = new PageInfo<>(brandDTOList);
            //DTO转VO
            List<BrandListVO> brandVOList = ObjectUtil.transfer(brandDTOList, BrandListVO.class);

            DataListVO dataListVO = new DataListVO(pageInfo);
            dataListVO.setList(brandVOList);

            return dataListVO;
        } catch (Exception e) {
            logger.info("brandDTOList={}", JSONUtil.toJson(brandDTOList));
            throw new BusinessException(ResultStatusConstant.DATA_READ_FAIL);
        }
    }


    @Override
    public BrandDetailVO getBrandDetailById(String brandId) throws InvocationTargetException {
        if (StringUtil.isBlank(brandId)) {
            logger.info("brandId={}", brandId);
            throw new BusinessException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }

        BrandDTO brandDetail = null;
        try {
            brandDetail = brandDAO.getBrandDetailById(brandId);
            //替换换行符，将品牌故事中的\r\n,\n,\r替换为<br>
            replaceLineBreak(brandDetail);
            return ObjectUtil.transfer(brandDetail, BrandDetailVO.class);
        } catch (Exception e) {
            logger.info("brandDetail={}", brandDetail);
            throw new BusinessException(ResultStatusConstant.DATA_READ_FAIL);
        }
    }

    /**
     * 处理换行符，将字符串中的\r\n,\n,\r替换为<br>，解析html
     *
     * @param brandDetail
     */
    public void replaceLineBreak(BrandDTO brandDetail) {

        //需要处理的字段，brandStoryNotes品牌故事详情
        String brandStoryNotes = brandDetail.getBrandStoryNotes();
        if (StringUtil.isBlank(brandStoryNotes)) {
            return;
        }
        if (brandStoryNotes.contains(WINDOWS_LINE_BREAK)) {
            brandDetail.setBrandStoryNotes(brandStoryNotes.replace(WINDOWS_LINE_BREAK, "<br>"));
        } else if (brandStoryNotes.contains(UNIX_LINE_BREAK)) {
            brandDetail.setBrandStoryNotes(brandStoryNotes.replace(UNIX_LINE_BREAK, "<br>"));
        } else if (brandStoryNotes.contains(MAC_OS_LINE_BREAK)) {
            brandDetail.setBrandStoryNotes(brandStoryNotes.replace(MAC_OS_LINE_BREAK, "<br>"));
        }
    }
}
