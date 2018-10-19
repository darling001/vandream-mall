package com.vandream.mall.business.service.impl;


import com.vandream.mall.business.dto.BxApiResult;
import com.vandream.mall.business.execption.FindContractException;
import com.vandream.mall.business.service.ConfirmationContractService;
import com.vandream.mall.business.vo.buyerContract.ConfirmationVO;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.service.ApiExecutorBxService;
import com.vandream.mall.commons.utils.JSONUtil;
import com.vandream.mall.commons.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/30 13:16
 */
@Service("confirmationContractService")
public class ConfirmationContractServiceImpl implements ConfirmationContractService {

    private static final Logger logger = LoggerFactory.getLogger(FindContractServiceImpl.class);

    @Autowired
    private ApiExecutorBxService apiExecutorBxService;


    @Override
    public String confirmationContract(String userId, String userName, String contractId, String operatorType) throws Exception {

        if(StringUtil.isBlank(userId) || StringUtil.isBlank(userName)){
            logger.error("参数为空");
            throw new FindContractException(ResultStatusConstant.CONTRACT_PARAMETERS_INTRODUCE_INTO_NULL);
        }
        ConfirmationVO confirmationVO = new ConfirmationVO();
        confirmationVO.setAccountId(userId);
        confirmationVO.setAccountName(userName);
        confirmationVO.setOperatorType(operatorType);
        confirmationVO.setOperatorUserId(userId);
        confirmationVO.setSalesContractHeadId(contractId);
        confirmationVO.setOperatorUserName(userName);
        //宝信接口
            String confirmationContractReturnResult =  apiExecutorBxService.confirmationContract(confirmationVO);
            if(StringUtil.isNotBlank(confirmationContractReturnResult)){
                BxApiResult bxApiResult = JSONUtil.toBean(confirmationContractReturnResult, BxApiResult.class);
                        if (0 == bxApiResult.getStatus()) {
                    logger.info("调用接口返回数据错误");
                    FindContractException findContractException = new FindContractException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
                    findContractException.setMessage(bxApiResult.getMessage());
                    throw findContractException;
                }
            }
        return "提交成功";
    }
}
