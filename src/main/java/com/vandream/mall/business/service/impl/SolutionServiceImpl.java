package com.vandream.mall.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vandream.mall.business.constant.AttachmentType;
import com.vandream.mall.business.constant.BusinessType;
import com.vandream.mall.business.dao.*;
import com.vandream.mall.business.domain.AccountDTO;
import com.vandream.mall.business.dto.BxApiResult;
import com.vandream.mall.business.dto.StaffDTO;
import com.vandream.mall.business.dto.SupplierContactsDTO;
import com.vandream.mall.business.dto.aus.AttachmentDTO;
import com.vandream.mall.business.dto.demand.DemandHeadDTO;
import com.vandream.mall.business.dto.solution.*;
import com.vandream.mall.business.execption.BusinessException;
import com.vandream.mall.business.execption.SolutionException;
import com.vandream.mall.business.execption.SystemException;
import com.vandream.mall.business.param.SolutionParam;
import com.vandream.mall.business.service.SolutionService;
import com.vandream.mall.business.vo.base.DataListVO;
import com.vandream.mall.business.vo.solution.SolutionInfoVO;
import com.vandream.mall.business.vo.solution.SolutionListVO;
import com.vandream.mall.business.vo.solution.SolutonAttachmentVO;
import com.vandream.mall.business.vo.solution.UploadAttachmentVO;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.service.ApiExecutorBxService;
import com.vandream.mall.commons.service.StorageService;
import com.vandream.mall.commons.utils.DateUtils;
import com.vandream.mall.commons.utils.ObjectUtil;
import com.vandream.mall.commons.utils.StringUtil;
import com.vandream.mall.commons.utils.ValidatorUtils;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;
import javax.annotation.Resource;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Li Jie
 */
@Service("solutionService")
public class SolutionServiceImpl implements SolutionService {
    private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    private static final String VALUE_SET_CODE_UNIT_TYPE = "tlerp.ausbs.unitType";
    /**
     * 解决方案待上传
     */
    private static final String SOLUTION_STATUS_TO_UPLOAD = "10";
    @Resource
    private SolutionHeadDAO solutionHeadDAO;
    @Resource
    private SolutionDemandDAO solutionDemandDAO;
    @Resource
    private SolutionSupplierDAO solutionSupplierDAO;
    @Resource
    private SolutionItemDAO solutionItemDAO;
    @Resource
    private SolutionItemLineDAO solutionItemLineDAO;
    @Resource
    private DemandHeadDAO demandHeadDAO;
    @Resource
    private DemandLineDAO demandLineDAO;
    @Resource
    private StaffDAO staffDAO;
    @Resource
    private AttachmentDAO attachmentDAO;
    @Resource
    private ApiExecutorBxService apiExecutorBxService;
    @Resource
    private StorageService storageService;
    @Resource
    private AccountDAO accountDAO;
    @Resource
    private SupplierContactsDAO supplierContactsDAO;

    @Override
    public DataListVO<SolutionListVO> findSolutionList(SolutionParam solutionParam) throws
            SolutionException {
        Map<String, Object> validation = ValidatorUtils.validation(solutionParam, null);
        if (ObjectUtil.isNotEmpty(validation)) {
            SolutionException solutionException = new SolutionException(ResultStatusConstant.INPUT_PARAM_ERROR);
            solutionException.setMessage(JSON.toJSONString(validation));
            throw solutionException;
        }

        Map<String, Object> paraMap = new HashMap<>(5);
        paraMap.put("status", solutionParam.getStatus());
        paraMap.put("keyword", solutionParam.getKeyword());
        paraMap.put("dispatchStartDate", solutionParam.getDispatchStartDate());
        Long dispatchEndDate = solutionParam.getDispatchEndDate();
        if (ObjectUtil.isNotEmpty(dispatchEndDate)) {
            paraMap.put("dispatchEndDate", dispatchEndDate + (1000 * 60 * 60 * 24));
        }
        paraMap.put("supplierId", solutionParam.getSupplierId());

        PageHelper.startPage(solutionParam.getPageNo(), solutionParam.getPageSize());
        List<SolutionHeadDTO> solutionList = solutionHeadDAO.findSolutionSupplierList(paraMap);
        PageInfo<SolutionHeadDTO> pageInfo = new PageInfo<>(solutionList);

        List<String> purchaserIdList = new ArrayList<>(solutionList.size());
        List<String> solutionSupplierIdList = new ArrayList<>(solutionList.size());
        for (SolutionHeadDTO solutionHeadDTO : solutionList) {
            purchaserIdList.add(solutionHeadDTO.getPurchaseManagerId());
                solutionSupplierIdList.add(solutionHeadDTO.getSolutionSupplierId());
        }
        if (ObjectUtil.isEmpty(purchaserIdList)) {
            return null;
        }
        if (ObjectUtil.isEmpty(solutionSupplierIdList)) {
            return null;
        }
        //取回staff 信息列表
        List<StaffDTO> listByStaffIdList = staffDAO.findListByStaffIdList(purchaserIdList);
        //
        Map<String, StaffDTO> staffMap = new HashMap<>(listByStaffIdList.size());
        for (StaffDTO staffDTO : listByStaffIdList) {
            staffMap.put(staffDTO.getStaffId(), staffDTO);
        }
        //取回 供方解决方案 attachment 信息列表
        List<AttachmentDTO> attachmentList = attachmentDAO.findList(AttachmentType
                .PSD_SOLUTION_SUPPLIER_FILE, BusinessType
                .PSD_SOLUTION_SUPPLIER, solutionSupplierIdList);
        Map<String, AttachmentDTO> attachmentMap = new HashMap<>(attachmentList.size());
        for (AttachmentDTO attachmentDTO : attachmentList) {
            attachmentMap.put(attachmentDTO.getBillNo(), attachmentDTO);
        }
        List<SolutionListVO> voList = null;
        try {
            voList = ObjectUtil.transfer(solutionList, SolutionListVO.class);
        } catch (Exception e) {
            logger.error("====findSolutionList" + e.getMessage());
            throw new SolutionException(ResultStatusConstant.PARAM_FORMAT_FAILURE);
        }
        for (SolutionListVO solutionListVO : voList) {
            StaffDTO staffDTO = staffMap.get(solutionListVO.getPurchaseManagerId());
            AttachmentDTO attachmentDTO = attachmentMap.get(solutionListVO.getSolutionSupplierId());
            solutionListVO.apply(staffDTO);
            solutionListVO.apply(attachmentDTO);
            //重写派发单状态
            String solutionStatus = solutionListVO.getStatus();
            solutionListVO.setStatus(solutionStatus);
        }
        DataListVO<SolutionListVO> dataListVO = new DataListVO<>(pageInfo);
        dataListVO.setList(voList);
        logger.debug("dataListVO: \n{}", dataListVO);
        return dataListVO;
    }

    @Override
    public SolutionInfoVO getSolutionInfo(String userId, String solutionSupplierId, String supplierId)
            throws
            InvocationTargetException {
        //校验参数
        if (StringUtil.isBlank(userId)) {
            throw new BusinessException(ResultStatusConstant.USERID_CAN_NOT_NULL);
        }
        if (StringUtil.isBlank(solutionSupplierId)) {
            throw new BusinessException(ResultStatusConstant.BUSINESS_ID_IS_EMPTY);
        }
        if (StringUtil.isBlank(supplierId)) {
            throw new BusinessException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }

        // SolutionHead表数据查询校验

        SolutionHeadDTO solutionHeadDTO = null;
        try {
            solutionHeadDTO = solutionHeadDAO.selectBySupplierSolutionId(solutionSupplierId, supplierId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException();
        }
        if (solutionHeadDTO == null) {
            throw new BusinessException(ResultStatusConstant.DATA_READ_FAIL);
        }
        SolutionInfoVO solutionInfoVO = null;
        try {
            solutionInfoVO = ObjectUtil.transfer(solutionHeadDTO, SolutionInfoVO.class);
        } catch (Exception e) {
            logger.error("{}", e.getMessage(), e);
            throw new SystemException();
        }

        List<SolutionDemandDTO> solutionDemandList = solutionDemandDAO.findSolutionSupplierItemInfoList
                (solutionSupplierId, supplierId);
        //是否标品为空
        Boolean isNullStandardFlag=false;
        //item 信息列表
        List<Map<String, Object>> itemList = new ArrayList<>();
        Boolean hasStandardGoods = false;
        for (SolutionDemandDTO solutionDemandDTO : solutionDemandList) {
            Map<String, Object> itemMap = new HashMap<>(5);
            itemMap.put("itemName", solutionDemandDTO.getItemName());
            itemMap.put("brandName", solutionDemandDTO.getBrand());
            //psd_solution_demand
            itemMap.put("demandRemark", solutionDemandDTO.getDemandRemark());
            //psd_item_line
            itemMap.put("quantity", solutionDemandDTO.getQuantity());
            itemMap.put("unit", solutionDemandDTO.getUnitTypeName());
            itemMap.put("solutionSupplierId", solutionDemandDTO.getSolutionSupplierId());
            String standardFlag = solutionDemandDTO.getStandardFlag();
            if (ObjectUtil.isEmpty(standardFlag)) {
                isNullStandardFlag=true;
                standardFlag="N";
            }
            if ("N".equalsIgnoreCase(standardFlag)) {
                solutionInfoVO.setStandardFlag(standardFlag);
            }
            itemMap.put("standardFlag", standardFlag);
            if(!isNullStandardFlag){
                itemList.add(itemMap);
                }

        }
        solutionInfoVO.setSolutionSupplierId(solutionSupplierId);
        solutionInfoVO.setItemList(itemList);
        //需求附件信息
        //取回 attachment 信息列表
        Map<String, Object> attachParaMap = new HashMap<>(3);
        attachParaMap.put("businessType", BusinessType.PSD_DEMAND);
        attachParaMap.put("billNo", solutionHeadDTO.getDemandId());
        List<AttachmentDTO> demandAttachment = attachmentDAO.findByBillNo(attachParaMap);

        if (ObjectUtil.isNotEmpty(demandAttachment)) {
            solutionInfoVO.setDemandAttachmentName(demandAttachment.get(0).getFileName());
            solutionInfoVO.setDemandAttachmentPath(demandAttachment.get(0).getFilePath());
        }

        List<SolutonAttachmentVO> solutonAttachmentVOList = new ArrayList();
        //判断是否需要展示 解决方案附件集合
        if (ObjectUtil.isNotEmpty(hasStandardGoods) && !hasStandardGoods) {
            if (!SOLUTION_STATUS_TO_UPLOAD.equals(solutionHeadDTO.getSolutionStatus())) {
                attachParaMap.put("billNo", solutionSupplierId);
                attachParaMap.put("businessType", BusinessType.PSD_SOLUTION_SUPPLIER);
                attachParaMap.put("attachmentType", AttachmentType.PSD_SOLUTION_SUPPLIER_FILE);
                List<AttachmentDTO> solutionAttachment = attachmentDAO.findByBillNo(attachParaMap);
                if (ObjectUtil.isNotEmpty(solutionAttachment)) {
                    for (int i = 0; i < solutionAttachment.size(); i++) {
                        AttachmentDTO ad = solutionAttachment.get(i);
                        SolutonAttachmentVO sa = new SolutonAttachmentVO();
                        sa.setCreateDate(ad.getCreateDate().getTime());
                        sa.setSolutionAttachmentName(ad.getFileName());
                        sa.setSolutionAttachmentPath(ad.getFilePath());
                        if(i==0){
                            sa.setStatus(solutionHeadDTO.getSolutionStatus());
                        }
                        solutonAttachmentVOList.add(sa);
                    }
                }
            }

        }

        if (StringUtil.isNotBlank(solutionInfoVO.getSolutionContactId())) {
            StaffDTO solutionContact = staffDAO.findByStaffId(solutionInfoVO.getSolutionContactId
                    ());
            if (ObjectUtil.isNotEmpty(solutionContact)) {
                solutionInfoVO.setSolutionContact(solutionContact.getUserName());
                solutionInfoVO.setSolutionPhone(solutionContact.getTelephone());
            }

        }
        //招采人员信息查询
        StaffDTO purchaser = staffDAO.findByStaffId(solutionHeadDTO.getPurchaseManagerId());
        solutionInfoVO.apply(purchaser);
        //收货地址
        DemandHeadDTO demandHeadDTO = demandHeadDAO.selectByPrimaryKey(solutionHeadDTO
                .getDemandId());
        if (demandHeadDTO == null) {
            throw new BusinessException(ResultStatusConstant.DATA_READ_FAIL);
        }
        solutionInfoVO.setDeliveryStartDate(demandHeadDTO.getDeliveryPeriodStart());
        solutionInfoVO.setDeliveryEndDate(demandHeadDTO.getDeliveryPeriodEnd());

        StringBuilder demandAddress = new StringBuilder();
        demandAddress.append(demandHeadDTO.getSiteRegionName());
        demandAddress.append(demandHeadDTO.getSiteCityName());
        demandAddress.append(demandHeadDTO.getSiteCountyName());
        solutionInfoVO.setSolutonAttachmentVOS(solutonAttachmentVOList);
        solutionInfoVO.setAddress(demandAddress.toString());
        logger.debug("solutionInfoVO: \n{}", solutionInfoVO);
        return solutionInfoVO;
    }

    @Override
    public String uploadAttachment(UploadAttachmentVO uploadAttachmentVO) throws
            InvocationTargetException {

        Map<String, Object> validation = ValidatorUtils.validation(uploadAttachmentVO);
        if (ObjectUtil.isNotEmpty(validation)) {

            BusinessException businessException = new BusinessException(ResultStatusConstant
                    .INPUT_PARAM_ERROR);
            businessException.setMessage(JSON.toJSONString(validation));
            throw businessException;
        }
        AccountDTO accountDTO = accountDAO.selectByPrimaryKey(uploadAttachmentVO.getUserId());
        SupplierContactsDTO supplierContactsDTO = supplierContactsDAO.findBySupplierId(accountDTO
                .getSupplierId());
        //附件对象
        UploadAttachmentDTO uploadAttachmentDTO = null;
        //解决方案
        UploadSolutionDTO uploadSolutionDTO = null;
        uploadSolutionDTO = ObjectUtil.transfer(uploadAttachmentVO,
                UploadSolutionDTO.class);
        uploadSolutionDTO.setLoginUserId(uploadAttachmentVO.getUserId());
        uploadAttachmentDTO = ObjectUtil.transfer(uploadAttachmentVO,
                UploadAttachmentDTO.class);
        uploadAttachmentDTO.setOperatorUserId(uploadAttachmentVO.getUserId());
        uploadSolutionDTO.setLoginUserName(uploadAttachmentVO.getUserName());
        if (ObjectUtil.isNotEmpty(supplierContactsDTO)) {
            uploadAttachmentDTO.setOperatorUserName(supplierContactsDTO.getContactName());
            uploadAttachmentDTO.setAccountName(supplierContactsDTO.getContactName());
            uploadAttachmentDTO.setSupplierPhone(supplierContactsDTO.getContactMobile());
            uploadSolutionDTO.setLoginUserName(supplierContactsDTO.getContactName());
        } else {
            if (ObjectUtil.isNotEmpty(accountDTO)) {
                if (ObjectUtil.isNotEmpty(accountDTO.getAccountName())) {
                    uploadAttachmentDTO.setOperatorUserName(accountDTO.getAccountName());
                    uploadAttachmentDTO.setAccountName(accountDTO.getAccountName());
                    uploadSolutionDTO.setLoginUserName(accountDTO.getAccountName());
                }
            }
        }

        uploadAttachmentDTO.setBusinessType(BusinessType.PSD_SOLUTION_SUPPLIER);

        //商城来源类别为10
        uploadAttachmentDTO.setFromType("10");
        uploadSolutionDTO.setFromType("10");
        List<Map<String, Object>> attachmentList = new ArrayList<>();
        Map<String, Object> attachmentMap = new HashMap<>(5);
        attachmentMap.put("attachmentName", uploadAttachmentVO.getAttachmentName());
        attachmentMap.put("attachmentType", AttachmentType.PSD_SOLUTION_SUPPLIER_FILE);
        attachmentMap.put("attachmentPath", uploadAttachmentVO.getAttachmentPath());
        attachmentMap.put("fileType", uploadAttachmentVO.getFileType());
        attachmentMap.put("fileSize", uploadAttachmentVO.getFileSize());
        attachmentList.add(attachmentMap);
        uploadAttachmentDTO.setAttachmentList(attachmentList);
        logger.debug(JSON.toJSONString(uploadAttachmentDTO));
        //调用宝信接口
        String resultJson = null;
        try {
            resultJson = apiExecutorBxService.uploadAttachment(uploadAttachmentDTO);
        } catch (Exception e) {
            throw new BusinessException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
        }
        BxApiResult bxApiResults = JSON.parseObject(resultJson, BxApiResult.class);
        if (bxApiResults.getStatus() == 1) {
            //附件上传成功后调用解决方案递交接口
            BxApiResult ApiResults = new BxApiResult();
            try {
                String retsultStr = apiExecutorBxService.supplierSubmit(uploadSolutionDTO);

                ApiResults = JSON.parseObject(retsultStr, BxApiResult.class);

            } catch (Exception e) {
                throw new BusinessException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
            }
            if (ApiResults.getStatus() == 1) {
                return "上传成功!";
            } else {
                throw new BusinessException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
            }
        } else {
            throw new BusinessException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
        }
    }

    @Override
    public Map<String, String> getSolutionPurchaseExcel(String userId, String supplierId, String
            solutionSupplierId) throws
            Exception {
        // SolutionHead表数据查询校验
        SolutionHeadDTO solutionHeadDTO = solutionHeadDAO.selectBySupplierSolutionId(solutionSupplierId,
                supplierId);
        if (solutionHeadDTO == null) {
            throw new BusinessException(ResultStatusConstant.DATA_READ_FAIL);
        }
        //通过 solutionId supplierId 拿到 SOLUTION_SUPPLIER_ID 列表 psd_solution_supplier
        List<SolutionSupplierDTO> solutionSupplierList = solutionSupplierDAO
                .findSolutionSupplierList(solutionSupplierId, supplierId);
        List<SolutionDemandDTO> itemInfoList = solutionDemandDAO.findSolutionSupplierItemInfoList
                (solutionSupplierId, supplierId);


        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(solutionHeadDTO.getSolutionCode());
        //设置列宽 256x+184   x为宽度
        sheet.setColumnWidth(0, 256 * 3 + 184);
        sheet.setColumnWidth(1, 256 * 10 + 184);
        sheet.setColumnWidth(2, 256 * 20 + 184);
        sheet.setColumnWidth(3, 256 * 10 + 184);
        sheet.setColumnWidth(4, 256 * 20 + 184);
        sheet.setColumnWidth(5, 256 * 10 + 184);
        sheet.setColumnWidth(6, 256 * 10 + 184);
        //合并单元格
        //参数说明：1：开始行 2：结束行  3：开始列 4：结束列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 6));
        Row firstRow = sheet.createRow(0);
        //firstRow.setHeight((short) 26);

        CellStyle firstCellStyle = workbook.createCellStyle();
        firstCellStyle.setAlignment(HorizontalAlignment.CENTER);
        firstCellStyle.setBorderTop(BorderStyle.THIN);
        firstCellStyle.setBorderLeft(BorderStyle.THIN);
        firstCellStyle.setBorderRight(BorderStyle.THIN);
        firstCellStyle.setBorderBottom(BorderStyle.THIN);
        Cell firstCell = firstRow.createCell(1);
        firstCell.setCellValue("采购需求清单");
        firstCell.setCellStyle(firstCellStyle);
        firstRow.createCell(2).setCellStyle(firstCellStyle);
        firstRow.createCell(3).setCellStyle(firstCellStyle);
        firstRow.createCell(4).setCellStyle(firstCellStyle);
        firstRow.createCell(5).setCellStyle(firstCellStyle);
        firstRow.createCell(6).setCellStyle(firstCellStyle);


        Iterator<Cell> firstCellIterator = firstRow.cellIterator();
        while (firstCellIterator.hasNext()) {

            Cell next = firstCellIterator.next();
            //next.getStringCellValue()
            next.setCellStyle(firstCellStyle);
        }

        firstCell.setCellStyle(firstCellStyle);

        //派发单号行
        Row codeRow = sheet.createRow(1);
        //列表title
        CellStyle codeCellStyle = workbook.createCellStyle();
        codeCellStyle.setAlignment(HorizontalAlignment.CENTER);
        codeCellStyle.setBorderTop(BorderStyle.THIN);
        codeCellStyle.setBorderLeft(BorderStyle.THIN);
        codeCellStyle.setBorderRight(BorderStyle.THIN);
        codeCellStyle.setBorderBottom(BorderStyle.THIN);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 3));
        Cell codeCell1 = codeRow.createCell(1);
        codeCell1.setCellValue("派发单号：");
        codeRow.createCell(2).setCellValue(solutionHeadDTO.getSolutionCode());
        codeRow.createCell(3);
        codeRow.createCell(4);
        codeRow.createCell(5);
        codeRow.createCell(6);
        Iterator<Cell> codeCellIterator = codeRow.cellIterator();
        while (codeCellIterator.hasNext()) {

            Cell next = codeCellIterator.next();
            //next.getStringCellValue()
            next.setCellStyle(codeCellStyle);
        }

        //列表title
        CellStyle titleCellStyle = workbook.createCellStyle();
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCellStyle.setBorderTop(BorderStyle.THIN);
        titleCellStyle.setBorderLeft(BorderStyle.THIN);
        titleCellStyle.setBorderRight(BorderStyle.THIN);
        titleCellStyle.setBorderBottom(BorderStyle.THIN);

        Row titleRow = sheet.createRow(2);
        List<String> titleList = new ArrayList<>();
        titleList.add("序号");
        titleList.add("商品名称");
        titleList.add("品牌");
        titleList.add("需求参数");
        titleList.add("数量");
        titleList.add("单位");
        for (int i = 0; i < titleList.size(); i++) {
            Cell titleCell = titleRow.createCell(i + 1);
            titleCell.setCellValue(titleList.get(i));
            titleCell.setCellStyle(titleCellStyle);
        }

        for (int i = 0; i < itemInfoList.size(); i++) {
            CellStyle dataCellStyle = workbook.createCellStyle();
            dataCellStyle.setBorderTop(BorderStyle.THIN);
            dataCellStyle.setBorderLeft(BorderStyle.THIN);
            dataCellStyle.setBorderRight(BorderStyle.THIN);
            dataCellStyle.setBorderBottom(BorderStyle.THIN);

            SolutionDemandDTO solutionDemandDTO = itemInfoList.get(i);
            Row dataRow = sheet.createRow(i + 3);
            Cell dataCell1 = dataRow.createCell(1);

            dataCell1.setCellValue(i + 1);
            try {
                if (ObjectUtil.isNotEmpty(solutionDemandDTO)) {
                    dataRow.createCell(2).setCellValue(solutionDemandDTO.getItemName());
                    dataRow.createCell(3).setCellValue(solutionDemandDTO.getBrand());
                    dataRow.createCell(4).setCellValue(solutionDemandDTO.getDemandRemark());
                    BigDecimal quantity = solutionDemandDTO.getQuantity();
                    if (ObjectUtil.isNotEmpty(quantity)) {
                        dataRow.createCell(5).setCellValue(String.valueOf(
                                quantity.setScale(2, BigDecimal.ROUND_HALF_EVEN)));
                    }
                    dataRow.createCell(6).setCellValue(solutionDemandDTO.getUnitTypeName());
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.debug("solutionDemandDTO {} \n", solutionDemandDTO);
                throw new BusinessException(ResultStatusConstant.DATA_LACK_REQUIRED);
            }


            Iterator<Cell> dataCellIterator = dataRow.cellIterator();
            while (dataCellIterator.hasNext()) {
                Cell next = dataCellIterator.next();
                next.setCellStyle(dataCellStyle);
            }
            dataCellStyle.setAlignment(HorizontalAlignment.CENTER);
            dataCell1.setCellStyle(dataCellStyle);
        }
        //脚注行
        CellStyle footerCellStyle = workbook.createCellStyle();
        int footerRowNum = 3 + itemInfoList.size() + 1;
        Row footerRow = sheet.createRow(footerRowNum);
        sheet.addMergedRegion(new CellRangeAddress(footerRowNum, footerRowNum, 1, 3));
        sheet.addMergedRegion(new CellRangeAddress(footerRowNum, footerRowNum, 4, 6));
        footerRow.createCell(1).setCellValue("来源：www.vandream.com");
        footerRow.createCell(2);
        footerRow.createCell(3);
        footerRow.createCell(4).setCellValue("时间：" + DateUtils.formatDate(new Date(), ""));
        footerRow.createCell(5);
        footerRow.createCell(6);
        Iterator<Cell> footerCellIterator = footerRow.cellIterator();
        while (footerCellIterator.hasNext()) {
            Cell next = footerCellIterator.next();
            next.setCellStyle(footerCellStyle);
        }
        ByteArrayOutputStream os = null;
        byte[] bytes = null;
        try {
            os = new ByteArrayOutputStream();
            workbook.write(os);
            bytes = os.toByteArray();
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (os != null) {
                os.close();
            }
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //上传到FastDFS
        String filePath = storageService.upload(bytes, "xlsx");
        Map<String, String> resultMap = new HashMap<>(2);
        resultMap.put("fileName", solutionHeadDTO.getSolutionCode() + ".xlsx");
        resultMap.put("filePath", filePath);
        return resultMap;
    }
}
