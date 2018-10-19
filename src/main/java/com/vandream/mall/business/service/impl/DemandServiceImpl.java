package com.vandream.mall.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vandream.mall.business.constant.AttachmentType;
import com.vandream.mall.business.constant.BusinessType;
import com.vandream.mall.business.dao.AttachmentDAO;
import com.vandream.mall.business.dao.DemandHeadDAO;
import com.vandream.mall.business.dao.DemandLineDAO;
import com.vandream.mall.business.dao.SolutionHeadDAO;
import com.vandream.mall.business.domain.Attachment;
import com.vandream.mall.business.dto.BxApiResult;
import com.vandream.mall.business.dto.aus.AttachmentDTO;
import com.vandream.mall.business.dto.demand.*;
import com.vandream.mall.business.execption.DemandException;
import com.vandream.mall.business.execption.SmsMsgException;
import com.vandream.mall.business.service.AddressService;
import com.vandream.mall.business.service.DemandService;
import com.vandream.mall.business.vo.AddressVO;
import com.vandream.mall.business.vo.demand.*;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.service.ApiExecutorBxService;
import com.vandream.mall.commons.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author dingjie
 * @date 2018/3/28
 * @time 19:48
 * Description:
 */
@Service("demandService")
public class DemandServiceImpl implements DemandService {
    private static final Logger logger = LoggerFactory.getLogger(DemandServiceImpl.class);
    @Autowired
    private AddressService addressService;
    @Autowired
    private ApiExecutorBxService apiExecutorBxService;
    @Autowired
    private DemandHeadDAO demandHeadDAO;
    @Autowired
    private SolutionHeadDAO solutionHeadDAO;
    @Autowired
    private DemandLineDAO demandLineDAO;
    @Autowired
    private AttachmentDAO attachmentDAO;
    /* 需求类型10:简要;20:详细 */
    private final String DEMAND_TYPE = "20";
    private final String DEMAND_TO_BE_CONFIRMED = "35";
    private final String DEMAND_QUERY_ALL = "all";

    @Override
    public Boolean addDemandInfo(DemandHeadVO demandHeadVO) throws DemandException {
        Map<String, Object> validation = ValidatorUtils.validation(demandHeadVO);
        if (ObjectUtil.isNotEmpty(validation)) {
            DemandException demandException = new DemandException(ResultStatusConstant.INPUT_PARAM_ERROR);
            demandException.setMessage(JSON.toJSONString(validation));
            throw demandException;
        }
        if (!RegexUtil.isChinaMobilePhone(demandHeadVO.getDemandPhone())) {
            throw new DemandException(ResultStatusConstant.SMS_MESSAGE_PHONE_NOT_MATCH);
        }
        DemandHeadLineDTO demandHeadLineDTO = null;
        try {
            /* 将入参VO对象转换为DTO对象 */
            demandHeadLineDTO = ObjectUtil.transfer(demandHeadVO, DemandHeadLineDTO.class);
            demandHeadLineDTO.setDeliveryPeriodStart(DateUtils.formatDate(
                    new java.util.Date(demandHeadVO.getDateTimeStart()), "yyyyMMdd"));
            demandHeadLineDTO.setDeliveryPeriodEnd(DateUtils.formatDate(
                    new java.util.Date(demandHeadVO.getDateTimeEnd()), "yyyyMMdd"));
            String demandType = demandHeadVO.getDemandType();
            //根据需求类型获取三种需求信息
            if (StringUtil.isNotBlank(demandType)) {
                //简要需求(包含文本需求和附件需求)
                String attachName = demandHeadVO.getAttachmentName();
                if (StringUtil.isNotBlank(attachName)) {
                    //存储附件需求
                    Attachment attachment = new Attachment();
                    attachment.setAttachmentName(demandHeadVO.getAttachmentName());
                    attachment.setAttachmentPath(demandHeadVO.getAttachmentPath());
                    attachment.setAttachmentType(AttachmentType.PSD_DEMAND_FILE);
                    attachment.setFileSize(new BigDecimal(demandHeadVO.getFileSize()));
                    attachment.setFileType(demandHeadVO.getFileType());
                    List<Attachment> attachmentList = new ArrayList<Attachment>();
                    attachmentList.add(attachment);
                    demandHeadLineDTO.setAttachmentList(attachmentList);
                } else {
                    //存储文本需求
                    demandHeadLineDTO.setAttachmentList(null);
                }
                if (demandType.equals(DEMAND_TYPE)) {
                    //存储详细需求
                    Object demandDis = demandHeadVO.getDemandDiscuss();
                    if (ObjectUtil.isEmpty(demandDis)) {
                        throw new DemandException(ResultStatusConstant.DEMAND_DISCUSS_PARAM_CAN_NOT_NULL);
                    }
                    String demandDiscuss = JSON.toJSONString(demandDis);
                    List<DemandLineVO> demandLineVOList = JSONUtil.toList(demandDiscuss, DemandLineVO.class);
                    List<SubLineDTO> demandLineDTOList = null;
                    demandLineDTOList = ObjectUtil.transfer(demandLineVOList, SubLineDTO.class);
                    demandHeadLineDTO.setSubLineList(demandLineDTOList);
                }
            }
            //获取地址信息
            String addressId = demandHeadVO.getAddressId();
            if (StringUtil.isNotBlank(addressId)) {
                AddressVO addressVO = addressService.getAddressById(demandHeadVO.getCompanyId(), addressId);
                if (ObjectUtil.isNotEmpty(addressVO)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    String provinceName = addressVO.getProvinceName();
                    String cityName = addressVO.getCityName();
                    String countyName = addressVO.getCountyName();
                    String phone = addressVO.getContactNumber();
                    String userName = addressVO.getContacts();
                    String addressDetail = addressVO.getAddress();
                    stringBuilder.append(addressDetail).append("(" + userName + " 收)").append(phone);
                    demandHeadLineDTO.setCustomerConsigneetName(userName);
                    demandHeadLineDTO.setCustomerConsigneetPhone(phone);
                    demandHeadLineDTO.setSiteRegionCode(addressVO.getProvinceCode());
                    demandHeadLineDTO.setSiteRegionName(provinceName);
                    demandHeadLineDTO.setSiteCityCode(addressVO.getCityCode());
                    demandHeadLineDTO.setSiteCityName(cityName);
                    demandHeadLineDTO.setSiteCountyCode(addressVO.getAreaCode());
                    demandHeadLineDTO.setSiteCountyName(countyName);
                    demandHeadLineDTO.setCustomerSiteArea(stringBuilder.toString());
                    demandHeadLineDTO.setSiteCountryCode(addressVO.getCountryCode());
                    demandHeadLineDTO.setSiteCountryName(addressVO.getCountryName());
                }
            }
            //业务类型
            demandHeadLineDTO.setBusinessType(BusinessType.PSD_DEMAND);
            //需求详述
            demandHeadLineDTO.setDemandDiscuss(demandHeadVO.getDemandRemark());
            //账套code
            demandHeadLineDTO.setBookCode("1000");
            //来源类别(10 商城)
            demandHeadLineDTO.setFromType("10");
            //需求提出人
            demandHeadLineDTO.setOperatorUserId(demandHeadVO.getUserId());
            demandHeadLineDTO.setOperatorUserName(demandHeadVO.getUserName());
            demandHeadLineDTO.setAccountId(demandHeadVO.getUserId());
            demandHeadLineDTO.setAccountName(demandHeadVO.getUserName());
            //调用 api添加需求
            String resultStr = apiExecutorBxService.addDemandInfo(demandHeadLineDTO);
            if (StringUtil.isBlank(resultStr)) {
                //调用宝信接口失败
                throw new DemandException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
            }
            BxApiResult bxApiResult = JSONUtil.toBean(resultStr, BxApiResult.class);
            if (ObjectUtil.isNotEmpty(bxApiResult)) {
                if ("1".equals(bxApiResult.getStatus().toString())) {
                    return true;
                } else {
                    throw new DemandException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
                }
            }

        }catch(DemandException se){
            logger.error("=====DemandException====addDemandInfo={}",se.toString());
            throw se;
        }  catch (Exception e) {
            logger.error("======addDemandInfo={}", e.toString());
            throw new DemandException(ResultStatusConstant.DEMAND_PUBLISH_EXCEPTION);
        }

        return false;
    }

    @Override
    public DemandResponseVO findDemandList(DemandRequestVO demandRequestVO) throws DemandException {
        Map<String, Object> validation = ValidatorUtils.validation(demandRequestVO, null);
        if (ObjectUtil.isNotEmpty(validation)) {
            DemandException demandException = new DemandException(ResultStatusConstant.INPUT_PARAM_ERROR);
            demandException.setMessage(JSON.toJSONString(validation));
            throw demandException;
        }
        DemandResponseVO demandResponseVO = new DemandResponseVO();
        //根据前台关键字与起始时间查询需求订单
        try {
            int pageNo = demandRequestVO.getPageNo();
            int pageSize = demandRequestVO.getPageSize();
            List<DemandBillVO> demandBillVOList = null;
            PageHelper.startPage(pageNo, pageSize);
            String demandStatus = demandRequestVO.getDemandStatus();
            //处理提交结束时间
            Long endDate = demandRequestVO.getSubmitEndTime();
            if (null != endDate && endDate > 0) {
                endDate += (1000 * 60 * 60 * 24);
                demandRequestVO.setSubmitEndTime(endDate);
            }
            //当搜索全部或待确认状态 的需求单时需要单独处理
            if (null != demandStatus && !"".equals(demandStatus)) {
                if (DEMAND_QUERY_ALL.equals(demandRequestVO.getDemandStatus())) {
                    demandRequestVO.setDemandStatus("");
                }
            }
            demandBillVOList = demandHeadDAO.selectDemandSolutionList(demandRequestVO);
            demandResponseVO.setDemandBillVOList(demandBillVOList);
            demandResponseVO.setPageNo(pageNo);
            demandResponseVO.setPageSize(demandBillVOList.size());
            PageInfo<DemandBillVO> pageInfo = new PageInfo<>(demandBillVOList);
            Long totalSize = pageInfo.getTotal();
            demandResponseVO.setTotalSize(totalSize.intValue());
        } catch (Exception e) {
            logger.error("======findDemandList={}", e.toString());
            throw new DemandException(ResultStatusConstant.DEMAND_LIST_QUERY_FAILED);
        }

        return demandResponseVO;
    }

    @Override
    public List<DemandSolutionVO> findSchemeList(String userId, String demandId) throws DemandException {
        if (StringUtil.isBlank(userId) || StringUtil.isBlank(demandId)) {
            throw new DemandException(ResultStatusConstant.DEMAND_SOLUTION_PARAM_CAN_NOT_NULL);
        }
        List<DemandSolutionVO> demandSolutionVOList = null;
        try {
            List<DemandSolutionDTO> demandSolutionList = solutionHeadDAO.getDemandSolutionList(userId, demandId);
            demandSolutionVOList = ObjectUtil.transfer(demandSolutionList, DemandSolutionVO.class);
        } catch (Exception e) {
            logger.error("======findSchemeList={}", e.toString());
            throw new DemandException(ResultStatusConstant.DEMAND_SOLUTION_LIST_QUERY_FAILED);
        }

        return demandSolutionVOList;
    }

    @Override
    public Boolean updateSchemeStatus(DemandStatusVO demandStatusVO) throws DemandException {
        Map<String, Object> validation = ValidatorUtils.validation(demandStatusVO);
        if (ObjectUtil.isNotEmpty(validation)) {
            DemandException demandException = new DemandException(ResultStatusConstant
                    .INPUT_PARAM_ERROR);
            demandException.setMessage(JSON.toJSONString(validation));
            throw demandException;
        }
        DemandStatusDTO demandStatusDTO = new DemandStatusDTO();
        try {
            if (null != demandStatusVO) {
                demandStatusDTO = ObjectUtil.transfer(demandStatusVO, DemandStatusDTO.class);
                demandStatusDTO.setOperatorUserId(demandStatusVO.getUserId());
                demandStatusDTO.setOperatorUserName(demandStatusVO.getUserName());
            }
            if (null != demandStatusDTO) {
                String resultStatus = apiExecutorBxService.updateSchemeStatus(demandStatusDTO);
                if (null != resultStatus && !"".equals(resultStatus)) {
                    BxApiResult bxApiResult = JSONUtil.toBean(resultStatus, BxApiResult.class);
                    if (null != bxApiResult) {
                        if ("1".equals(bxApiResult.getStatus().toString())) {
                            return true;
                        } else {
                            //调用宝信接口失败
                            throw new DemandException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
                        }
                    }
                }

            }
        } catch (Exception e) {
            logger.error("======updateSchemeStatus={}", e.toString());
            throw new DemandException(ResultStatusConstant.DEMAND_SOLUTION_STATUS_UPDATE_FAILED);
        }

        return false;
    }

    @Override
    public DemandDetailVO getDemandInfo(String userId, String demandId) throws DemandException {
        if (StringUtil.isBlank(userId) || StringUtil.isBlank(demandId)) {
            throw new DemandException(ResultStatusConstant.DEMAND_SOLUTION_PARAM_CAN_NOT_NULL);
        }
        DemandRequestVO demandRequestVO = new DemandRequestVO();
        demandRequestVO.setUserId(userId);
        demandRequestVO.setDemandId(demandId);
        DemandDetailVO demandDetailVO = new DemandDetailVO();
        try {
            //获取需求单主表信息
            DemandDetailDTO demandDetailDTO = demandHeadDAO.selectDetailByDemandId(demandRequestVO);
            if (null != demandDetailDTO) {
                //查询需求单对应的附件表信息
                Map<String, Object> map = new HashMap<>();
                map.put("billNo", demandId);
                map.put("businessType", BusinessType.PSD_DEMAND);
                map.put("attachmentType", AttachmentType.PSD_DEMAND_FILE);
                List<AttachmentDTO> attachmentList = attachmentDAO.findByBillNo(map);
                //判断附件是否为空,不为空就把附件名与路径放在demandDetailDTO对应属性上
                if (null != attachmentList && attachmentList.size() > 0) {
                    demandDetailDTO.setFileName(attachmentList.get(0).getFileName());
                    demandDetailDTO.setFilePath(attachmentList.get(0).getFilePath());
                }
                List<DemandLineDetailDTO> demandLineList = demandLineDAO.getDemandLineList(demandRequestVO);
                List<DemandSolutionVO> schemeList = findSchemeList(userId, demandId);
                List<DemandVisitLogVO> visitLogs = demandLineDAO.getdemandVisitLogs(demandRequestVO);
                demandDetailVO = ObjectUtil.transfer(demandDetailDTO, DemandDetailVO.class);
                List<DemandLineDetailDTO> demandLineDTOList=new ArrayList<>();
                if(ObjectUtil.isNotEmpty(demandLineList)){
                    for(DemandLineDetailDTO dl:demandLineList){
                        if(StringUtil.isNotBlank(dl.getItemName())){
                            demandLineDTOList.add(dl);
                        }
                    }
                    demandDetailVO.setDemandLineDetailDTOList(demandLineDTOList);
                }else{
                    demandDetailVO.setDemandLineDetailDTOList(null);
                }
                demandDetailVO.setDemandSolutionVOList(schemeList);
                demandDetailVO.setLogList(visitLogs);
            }
        } catch (Exception e) {
            logger.error("=======getDemandInfo={}",e.toString());
            throw new DemandException(ResultStatusConstant.DEMAND_DETAIL_INFO_QUERY_FAILED);
        }
        return demandDetailVO;
    }
}
