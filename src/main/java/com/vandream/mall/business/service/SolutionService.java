package com.vandream.mall.business.service;

import com.vandream.mall.business.execption.SolutionException;
import com.vandream.mall.business.param.SolutionParam;
import com.vandream.mall.business.vo.base.DataListVO;
import com.vandream.mall.business.vo.solution.SolutionInfoVO;
import com.vandream.mall.business.vo.solution.SolutionListVO;
import com.vandream.mall.business.vo.solution.UploadAttachmentVO;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author Li Jie
 */
public interface SolutionService {
    /**
     * 获取供方派发单列表
     * @return
     */
    DataListVO<SolutionListVO> findSolutionList(SolutionParam solutionParam) throws SolutionException;

    /**
     * 获取单条派发单信息
     *
     * @param userId
     * @param solutionSupplierId
     * @param supplierId
     * @return
     * @throws InvocationTargetException
     */
    SolutionInfoVO getSolutionInfo(String userId, String solutionSupplierId, String supplierId) throws
            InvocationTargetException;

    /**
     *
     * @param uploadAttachmentVO
     * @return
     * @throws InvocationTargetException
     */
    String uploadAttachment(UploadAttachmentVO uploadAttachmentVO) throws InvocationTargetException;

    /**
     * 生成采购需求清单Excel
     * @param userId
     * @param solutionId
     * @return
     * @throws Exception
     */
    Map<String, String> getSolutionPurchaseExcel(String userId, String supplierId, String solutionId)
            throws
            Exception;
}
