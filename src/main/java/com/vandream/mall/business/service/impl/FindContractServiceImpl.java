package com.vandream.mall.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.vandream.mall.business.constant.AttachmentType;
import com.vandream.mall.business.dao.*;
import com.vandream.mall.business.domain.Attachment;
import com.vandream.mall.business.dto.AttachMent2BXDTO;
import com.vandream.mall.business.dto.BxApiResult;
import com.vandream.mall.business.dto.ValueSetLineDTO;
import com.vandream.mall.business.dto.findContract.LogisticsReceiptDTO;
import com.vandream.mall.business.dto.findContract.SubLineList;
import com.vandream.mall.business.dto.findContract.UpdateBillStatusDTO;
import com.vandream.mall.business.dto.findContract.VoucherUploadDTO;
import com.vandream.mall.business.dto.snapshot.ItemSnapshotVersionDTO;
import com.vandream.mall.business.dto.snapshot.ContractSnapshotDTO;
import com.vandream.mall.business.execption.FindContractException;
import com.vandream.mall.business.execption.SystemException;
import com.vandream.mall.business.service.FindContractService;
import com.vandream.mall.business.service.SnapshotService;
import com.vandream.mall.business.vo.buyerContract.*;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.service.ApiExecutorBxService;
import com.vandream.mall.commons.service.StorageService;
import com.vandream.mall.commons.utils.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import static org.apache.commons.compress.utils.IOUtils.copy;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/28 19:13
 */
@Service("findContractService")
public class FindContractServiceImpl implements FindContractService {

    private static final Logger logger = LoggerFactory.getLogger(FindContractServiceImpl.class);

    @Resource
    private FindContractDAO findContractDAO;

    @Resource
    private ApiExecutorBxService apiExecutorBxService;

    @Resource
    private FindPaymentDAO findPaymentDAO;

    @Resource
    private ContractInfoDAO contractInfoDAO;

    @Resource
    private DeliveryInfoDAO deliveryInfoDAO;

    @Resource
    private DeliveryNoticeInfoDAO deliveryNoticeInfoDAO;

    @Resource
    private ValueSetLineDAO valueSetLineDAO;

    @Resource
    private SnapshotDAO  snapshotDAO;
    @Resource
    private SnapshotService snapshotService;
    @Resource
    private StorageService storageService;

    @Override
    public FindContractListVO findContractList(String userId,
                                               String companyId,
                                               String keyword,
                                               Long contractStartDate,
                                               Long contractEndDate,
                                               int pageSize,
                                               int pageNo,
                                               String contractStatus,
                                               String paymentStatus,
                                               String DeliveryStatus) throws FindContractException {
        if (StringUtil.isBlank(companyId) || StringUtil.isBlank(userId)) {
            logger.error("参数为空");
            throw new FindContractException(ResultStatusConstant
                    .CONTRACT_PARAMETERS_INTRODUCE_INTO_NULL);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date conStartDate = null;
        java.util.Date conEndDate = null;
        String startDate = null;
        String endDate = null;
        if (null != contractStartDate) {
            conStartDate = new Date(contractStartDate);
            startDate = sdf.format(conStartDate);
        }
        if (null != contractEndDate) {
            conEndDate = new Date(contractEndDate + 24*60*60*1000);
            endDate = sdf.format(conEndDate);
        }
        if (0 >= pageSize) {
            pageSize = 10;
        }
        if (0 >= pageNo) {
            pageNo = 1;
        }
        //传入的分页开始的条数
        int pageNumber = (pageNo - 1) * pageSize;
        List<ContractListVO> contractListVOList = findContractDAO.findContractList(userId,
                companyId, keyword, startDate, endDate, contractStatus, paymentStatus,
                DeliveryStatus, pageNumber, pageSize);
        FindContractListVO findContractListVO = new FindContractListVO();
        int totalSize = findContractDAO.totalSize(userId,
                companyId, keyword, startDate, endDate, contractStatus, paymentStatus,
                DeliveryStatus);
        if(ObjectUtil.isNotEmpty(contractListVOList)) {
            checkOrderItemChange(contractListVOList);
        }
        findContractListVO.setContractList(contractListVOList);
        findContractListVO.setPageNo(pageNo);
        findContractListVO.setTotalSize(totalSize);
        return findContractListVO;
    }

    /**
     * 修改单据状态
     *
     * @param billId
     * @param billType
     * @param companyId
     * @param companyName
     * @param userId
     * @param userName
     * @param operatorType
     * @return
     */
    @Override
    public String updateBillStatus(String billId, String billType, String companyId, String
            companyName, String userId, String userName, String operatorType) throws Exception {
        if (StringUtil.isBlank(billId) || StringUtil.isBlank(billType) || StringUtil.isBlank
                (companyId) || StringUtil.isBlank(companyName)
                || StringUtil.isBlank(userId) || StringUtil.isBlank(userName) || StringUtil
                .isBlank(operatorType)) {
            //抛异常参数不为空
            logger.error("参数为空");
            throw new FindContractException(ResultStatusConstant
                    .CONTRACT_PARAMETERS_INTRODUCE_INTO_NULL);
        }
        UpdateBillStatusDTO updateBillStatusDTO = new UpdateBillStatusDTO();
        updateBillStatusDTO.setAccountId(userId);
        updateBillStatusDTO.setAccountName(userName);
        updateBillStatusDTO.setBillId(billId);
        updateBillStatusDTO.setBillType(billType);
        updateBillStatusDTO.setCompanyName(companyName);
        updateBillStatusDTO.setCompanyId(companyId);
        updateBillStatusDTO.setOperatorType(operatorType);
        updateBillStatusDTO.setOperatorUserId(userId);
        updateBillStatusDTO.setOperatorUserName(userName);
        String updateBillStatusReturnResult = apiExecutorBxService.updateBillStatus
                (updateBillStatusDTO);
        getApiExecutorBxService(updateBillStatusReturnResult);
        return "提交成功";
    }

    /**
     * 销售合同付款凭证上传
     *
     * @param userId
     * @param userName
     * @param contractId
     * @param fromType
     * @param bussinessType
     * @param attachmentList
     * @return
     */
    @Override
    public String voucherUpload(String userId, String userName, String contractId, String
            fromType, String bussinessType, Object attachmentList) throws Exception {
        if (StringUtil.isBlank(userId) || StringUtil.isBlank(userName) || StringUtil.isBlank
                (contractId) || StringUtil.isBlank(fromType) ||
                StringUtil.isBlank(bussinessType) || null == attachmentList) {
            //参数不为空
            logger.error("参数为空");
            throw new FindContractException(ResultStatusConstant
                    .CONTRACT_PARAMETERS_INTRODUCE_INTO_NULL);
        }
        String attachment = attachmentList.toString();
        List<Attachment> attachmentLists = JSONUtil.toList(attachment, Attachment.class);
        VoucherUploadDTO voucherUploadDTO = new VoucherUploadDTO();
        voucherUploadDTO.setAccountId(userId);
        voucherUploadDTO.setAccountName(userName);
        voucherUploadDTO.setAttachmentList(attachmentLists);
        voucherUploadDTO.setBusinessType(bussinessType);
        voucherUploadDTO.setSalesContractHeadId(contractId);
        voucherUploadDTO.setFromType(fromType);
        voucherUploadDTO.setOperatorUserId(userId);
        voucherUploadDTO.setOperatorUserName(userName);
        String voucherUploadReturnResult = apiExecutorBxService.voucherUpload(voucherUploadDTO);
        getApiExecutorBxService(voucherUploadReturnResult);
        return "提交成功";
    }

    /**
     * 获取合同对应的付款历史
     *
     * @param userId
     * @param contractId
     * @return
     */

    @Override
    public FindPaymentHistoryVO findPaymentHistory(String userId, String contractId) throws
            FindContractException {
        if (StringUtil.isBlank(userId) || StringUtil.isBlank(contractId)) {
            logger.error("传入参数为空");
            throw new FindContractException(ResultStatusConstant
                    .CONTRACT_PARAMETERS_INTRODUCE_INTO_NULL);
        }
        String businessType = "somSalesContract";
        List<FindPaymentVO> findPaymentHistory = findPaymentDAO.findPaymentHistory(userId,
                contractId, businessType);
        FindPaymentHistoryVO findPaymentHistoryVO = findPaymentDAO.findPaymentContract(contractId);
        if(ObjectUtil.isEmpty(findPaymentHistoryVO)){
           return findPaymentHistoryVO;
        }
        findPaymentHistoryVO.setPaymentedHistory(findPaymentHistory);
        return findPaymentHistoryVO;
    }

    /**
     * 获取发货单通知详情
     *
     * @param userId
     * @param deliveryNoticeId
     * @return
     */
    @Override
    public DeliveryNoticeInfoVO getDeliveryNoticeInfo(String userId, String deliveryNoticeId)
            throws FindContractException {
        if (StringUtil.isBlank(userId) || StringUtil.isBlank(deliveryNoticeId)) {
            logger.error("传入参数为空");
            throw new FindContractException(ResultStatusConstant
                    .CONTRACT_PARAMETERS_INTRODUCE_INTO_NULL);
        }

        DeliveryNoticeInfoVO deliveryNoticeInfoVO = null;
        try {
            deliveryNoticeInfoVO = deliveryNoticeInfoDAO.getDeliveryNoticeInfo(userId, deliveryNoticeId);
            List<NoticeItemListVO> noticeItemList = deliveryNoticeInfoVO.getItemList();
            for(NoticeItemListVO noticeItem:noticeItemList){
                String unit = noticeItem.getUnit();
                String unitName = getUnitName(unit);
                noticeItem.setUnit(unitName);
            }
        }catch (Exception e){
            logger.error("=====>数据库查询异常");
            throw new FindContractException(ResultStatusConstant.QUERY_DATABASE_ERROR);
        }
        return deliveryNoticeInfoVO;
    }

    /**
     * 获取发货单详情
     *
     * @param userId
     * @param deliveryId
     * @return
     */

    @Override
    public DeliveryInfoVO getDeliveryInfo(String userId, String deliveryId) throws
            FindContractException {
        if (StringUtil.isBlank(userId) || StringUtil.isBlank(deliveryId)) {
            logger.error("传入参数为空");
            throw new FindContractException(ResultStatusConstant
                    .CONTRACT_PARAMETERS_INTRODUCE_INTO_NULL);
        }
        DeliveryInfoVO deliveryInfoVO = new DeliveryInfoVO();
        try {
            deliveryInfoVO=deliveryInfoDAO.getDeliveryInfo(userId, deliveryId);
            List<InfoItemListVO> infoItemList =  deliveryInfoVO.getItemList();
            for(InfoItemListVO infoItem:infoItemList){
                String unit = infoItem.getUnit();
                String unitName = getUnitName(unit);
                infoItem.setUnit(unitName);
            }
        }catch (Exception e){
            logger.error("=====>数据库查询异常");
            throw new FindContractException(ResultStatusConstant.QUERY_DATABASE_ERROR);
        }
        return deliveryInfoVO;
    }

    /**
     * 发货单签收
     *
     * @param userId
     * @param deliveryId
     * @param deliveryCode
     * @param userName
     * @param itemList
     * @param receiptInfo
     * @param bussinessType
     * @return
     */
    @Override
    public String logisticsReceipt(String userId, String deliveryId, String deliveryCode, String
            userName,Object itemList, String receiptInfo, List<Object>attachmentList, String bussinessType) throws Exception {
        List<SubLineList> subLineLists = null;
        if (ObjectUtil.isNotEmpty(itemList)) {
            subLineLists = JSON.parseArray(JSON.toJSONString
                    (itemList), SubLineList.class);
        }
        if (StringUtil.isBlank(userId) || StringUtil.isBlank(deliveryId) || StringUtil.isBlank
                (deliveryCode)
                || StringUtil.isBlank(userName) || StringUtil.isBlank(bussinessType)
                || ObjectUtil.isEmpty(subLineLists) || ObjectUtil.isEmpty(attachmentList)) {
            logger.error("参数为空");
            throw new FindContractException(ResultStatusConstant
                    .CONTRACT_PARAMETERS_INTRODUCE_INTO_NULL);
        }
        for (SubLineList subLineList : subLineLists) {
            if (subLineList.getDeliveryHeadId() == null || subLineList.getDeliveryLineCode() == null
                    || subLineList.getDeliveryLineId() == null ||
                    subLineList.getReceiptQuantity().compareTo(new BigDecimal(0)) < 1) {
                logger.error("参数为空");
                throw new FindContractException(ResultStatusConstant
                        .CONTRACT_PARAMETERS_INTRODUCE_INTO_NULL);
            }
            if(subLineList.getReceiptQuantity().compareTo(subLineList.getRealQuantity())==1){
                throw new FindContractException(ResultStatusConstant.INVOICE_QUANTITY_ABNORMAL);
            }
        }
        List<Attachment> attachments=new ArrayList<>();
        for (Object attachmentObj : attachmentList) {
            Attachment attachment = JSON.parseObject(JSON.toJSONString(attachmentObj), Attachment
                    .class);
            //Map<String, Object> validation = ValidatorUtils.validation(attachment);
            //if(validation.size()>0){
            //    logger.error("参数为空");
            //    throw new FindContractException(ResultStatusConstant
            //            .CONTRACT_PARAMETERS_INTRODUCE_INTO_NULL);
            //}
            attachment.setAttachmentType(AttachmentType.TMS_DELIVERY_FILE);
            if (attachment.getAttachmentName() == null || attachment.getAttachmentPath() == null
                    || attachment.getAttachmentType() == null
                    || attachment.getFileType() == null || attachment.getFileSize().compareTo(new
                    BigDecimal(0)) < 1) {
                logger.error("参数为空");
                throw new FindContractException(ResultStatusConstant
                        .CONTRACT_PARAMETERS_INTRODUCE_INTO_NULL);
            }
            attachments.add(attachment);
        }
        LogisticsReceiptDTO logisticsReceiptDTO = new LogisticsReceiptDTO();
        logisticsReceiptDTO.setDeliveryHeadId(deliveryId);
        logisticsReceiptDTO.setDeliveryCode(deliveryCode);
        logisticsReceiptDTO.setBusinessType(bussinessType);
        logisticsReceiptDTO.setReceiptRemark(receiptInfo);
        logisticsReceiptDTO.setAttachmentList(attachments);
        logisticsReceiptDTO.setSubLineList(subLineLists);
        logisticsReceiptDTO.setOperatorUserId(userId);
        logisticsReceiptDTO.setOperatorUserName(userName);
        String logisticsReceipt = apiExecutorBxService.logisticsReceipt(logisticsReceiptDTO);
        getApiExecutorBxService(logisticsReceipt);
        return "提交成功";
    }

    /**
     * 合同详情
     *
     * @param userId
     * @param contractId
     * @return
     */
    @Override
    public ContractInfoVO getContractInfo(String userId, String contractId) throws
            FindContractException, SystemException {
        if (StringUtil.isBlank(userId) || StringUtil.isBlank(contractId)) {
            logger.error("参数为空");
            throw new FindContractException(ResultStatusConstant
                    .CONTRACT_PARAMETERS_INTRODUCE_INTO_NULL);
        }
        ContractInfoVO contractInfoVO = contractInfoDAO.getContractInfo(userId, contractId);
        if(ObjectUtil.isEmpty(contractInfoVO)){
            return contractInfoVO;
        }

        List<ContractInfoitemListVO> itemInfo = contractInfoDAO.getContractInfoItem(contractId);
        for(ContractInfoitemListVO contractInfo:itemInfo){
            String unit = contractInfo.getUnit();
            String unitName = getUnitName(unit);
            contractInfo.setUnit(unitName);
        }
        List<AttachmentListVO> contractInfo = contractInfoDAO.getContractList(contractId);
        List<AttachmentListVO> attachmentInfo = contractInfoDAO.getAttachment(contractId);
        ContractInfoVO contractIdentifyInfo = contractInfoDAO.getContractIdentifyPeople(contractId);
        BigDecimal paymentTotalAmount = contractInfoDAO.getTotalAmount(contractId);
        List<BillingInformationVO> billingInformation = contractInfoDAO.getBillingInformation(contractId);
        checkOrderItemChanged(itemInfo);
        contractInfoVO.setItemList(itemInfo);
        contractInfoVO.setAttachmentList(attachmentInfo);
        contractInfoVO.setBillingInformation(billingInformation);
        contractInfoVO.setTotalAmount(paymentTotalAmount);
        contractInfoVO.setContractList(contractInfo);
        if (contractIdentifyInfo != null && contractIdentifyInfo.getConfirmer() != null) {
            contractInfoVO.setConfirmer(contractIdentifyInfo.getConfirmer());
        } else {
            contractInfoVO.setConfirmer(null);
        }
        if (contractIdentifyInfo != null && contractIdentifyInfo.getConfirmDateTime() != null) {
            contractInfoVO.setConfirmDateTime(contractIdentifyInfo.getConfirmDateTime());
        } else {
            contractInfoVO.setConfirmDateTime(null);
        }
        return contractInfoVO;
    }
    public static byte[] toByteArray(InputStream input)
            throws IOException
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }
    @Override
    public Map<String, String> exportContractPdf(String userId, String contractId)throws
            FindContractException, SystemException{

        ContractInfoVO contractInfoVO = getContractInfo(userId,contractId);
        // 页面大小
        Rectangle tRectangle = new Rectangle(PageSize.A4);
        // 边框颜色
        tRectangle.setBorderColor(BaseColor.GRAY);
        // 边框宽度
        tRectangle.setBorderWidth(5.2f);
        //创建文本对象
        Document document = new Document(tRectangle);
        String blankStr="          ";
        try {
//            String filePath="C:\\Users\\admin\\Desktop\\testTempletePdf" + new Random().nextInt() + ".pdf";
            //初始化 pdf输出对象 PdfWriter
            ByteArrayOutputStream  out = new ByteArrayOutputStream ();
//            FileOutputStream out=new FileOutputStream(filePath);
            PdfWriter writer = PdfWriter.getInstance(document, out);
            writer.setPdfVersion(PdfWriter.PDF_VERSION_1_2);
            //打开文档
            document.open();
            int headTableColNum=3;
            PdfPTable headertable = new PdfPTable(headTableColNum);
            BaseColor color333333=new BaseColor(51,51,51);
            //黑体14字体兼容中文
            Font size14BlackFont=getChineseFont(9,"PingFangSC-Medium",color333333);
            //黑体8号字体兼容中文
            Font size12BlackFont=getChineseFont(8,"PingFangSC-Medium",color333333);
            //表格 商品名称
            Font size12SemiboldFont=getChineseFont(8,"PingFangSC-Semibold",color333333);
            //部分描述12号浅灰色
            Font size12GrayFont=getChineseFont(8,"PingFangSC-Regular",color333333);
            //部分描述14号浅灰色
            Font size14GrayFont=getChineseFont(8,"PingFangSC-Regular",color333333);
            //背景色
            BaseColor backGroundColor=new BaseColor(249,249,249);
            //边框色
            BaseColor borderGrayColor=new BaseColor(226,226,226);
            //白色边框
            BaseColor whiteBorderColor=new BaseColor(255,255,255);
            //背景色深灰
            BaseColor backGroudDeepGray=new BaseColor(196,196,196);
            //添加图片

            Image img = Image.getInstance("classpath:header01.jpg");
            img.setAlignment(Image.LEFT);
            //大小
            img.scaleToFit(530, 75);
            img.setSpacingAfter(2f);

            document.add(img);
            //第一部分，设置订单名
            Paragraph paraTitle = new Paragraph();
            paraTitle.add(new Chunk(contractInfoVO.getContractName(),getChineseFont(11,"PingFangSC-Medium",color333333)));
            paraTitle.add(Chunk.NEWLINE);
            Phrase phrase = new Phrase();
            Chunk chunkCode = new Chunk("采购订单号：" + contractInfoVO.getContractCode(),size12GrayFont);
            Chunk chunkOrderDate = new Chunk("订单日期：" +
                    DateUtils.formatDate(new Date(contractInfoVO.getContractDate()), "yyyy-MM-dd"), size12GrayFont);
            // 设置背景色
            phrase.setLeading(16);
            phrase.add(chunkCode);
            phrase.add(new Chunk(blankStr));
            phrase.add(chunkOrderDate);
            // 表格的宽度百分比
            headertable.setWidthPercentage(100);
            PdfPCell titleCell = new PdfPCell(paraTitle);
            titleCell.setColspan(3);
            titleCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
            titleCell.setFixedHeight(25);
            titleCell.setBorder(Rectangle.NO_BORDER);
            titleCell.setBackgroundColor(backGroundColor);
            headertable.addCell(titleCell);

            PdfPCell codeCell = new PdfPCell(phrase);
            codeCell.setColspan(3);
            codeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            codeCell.setVerticalAlignment(Element.ALIGN_TOP);
            codeCell.setFixedHeight(25);
            codeCell.setBorder(Rectangle.NO_BORDER);
            codeCell.setBackgroundColor(backGroundColor);
            headertable.addCell(codeCell);

            document.add(headertable);
            //下划线
            PdfPTable tt = new PdfPTable(2);
            tt.setWidthPercentage(100f);
            tt.getDefaultCell().setBorderColor(whiteBorderColor);
            tt.getDefaultCell().setBorderWidthLeft(0);
            tt.getDefaultCell().setBorderWidthRight(0);
            tt.getDefaultCell().setBorderWidthTop(0);
            tt.addCell("");
            tt.addCell("");

            //第二部分，基础信息
            PdfPTable baseTable=new PdfPTable(2);
            // 定义表格的宽度
            int[] baseCellsWidth = {1,9};
            // 单元格宽度
            baseTable.setWidths(baseCellsWidth);
            baseTable.setWidthPercentage(100);
            baseTable.getDefaultCell().setPaddingLeft(0);
            baseTable.getDefaultCell().setBorder(1);
            baseTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            baseTable.getDefaultCell().setBorderColor(whiteBorderColor);
            //垂直居中
            baseTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
            baseTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            Phrase baseInfoPara = new Phrase("基础信息", size14BlackFont);
            PdfPCell baseInfoCell=new PdfPCell(baseInfoPara);
            baseInfoCell.setColspan(2);
            baseInfoCell.setFixedHeight(20);
            baseInfoCell.setBorder(1);
            baseInfoCell.setBorderColor(whiteBorderColor);
            baseInfoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            baseTable.addCell(baseInfoCell);
            String purchaser = contractInfoVO.getPurchaser();
            baseTable.addCell(new Phrase("甲方" , size12GrayFont));
            baseTable.addCell(new Phrase(purchaser == null ? "" : purchaser,size12GrayFont));
            String supplier = contractInfoVO.getSupplier();
            baseTable.addCell(new Phrase("乙方" ,size12GrayFont));
            baseTable.addCell(new Phrase(supplier == null ? "" : supplier,size12GrayFont));
            String projectName = contractInfoVO.getProjectName();
            baseTable.addCell(new Phrase("项目名称" , size12GrayFont));
            baseTable.addCell(new Phrase(projectName == null ? "" : projectName,size12GrayFont));
            String address = contractInfoVO.getAddress();
            baseTable.addCell(new Phrase("收货地址" , size12GrayFont));
            String name = contractInfoVO.getName();
            String phone = contractInfoVO.getPhone();
            baseTable.addCell(new Phrase((address == null ? "" : address)+(name==null?"":"("+name+") 收  ")+ (phone==null?"":phone),size12GrayFont));
            document.add(baseTable);



            //第三部分，采购需求
            Phrase phraseOrder=new Phrase();
            phraseOrder.add(new Chunk("采购需求", size14BlackFont));
            PdfPCell dataCell=new PdfPCell(phraseOrder);
            dataCell.setColspan(8);
            dataCell.setFixedHeight(20);
            dataCell.setBorder(1);
            dataCell.setBorderColor(whiteBorderColor);
            dataCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            //定义一个写入数据的Table
            int colNumber = 8;
            PdfPTable datatable = new PdfPTable(colNumber);

            // 定义表格的宽度
            int[] cellsWidth = {1, 6, 2, 1, 2, 1, 2, 2};
            // 单元格宽度
            datatable.setWidths(cellsWidth);
            //表格的总宽度
            // datatable.setTotalWidth(300f);
            // 表格的宽度百分比
            datatable.setWidthPercentage(100);
            // 单元格的间隔
            datatable.getDefaultCell().setPadding(1);
            // 边框宽度
            datatable.getDefaultCell().setBorderColor(backGroudDeepGray);
            datatable.getDefaultCell().setBorderWidth(1);
            // 设置表格的底色
//            datatable.getDefaultCell().setBackgroundColor(borderGrayColor);
            datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            datatable.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
            datatable.getDefaultCell().setPadding(5);
            datatable.setSpacingBefore(2f);
            datatable.setSpacingAfter(5f);
            datatable.addCell(dataCell);
            //表头数组
            String[] tableHeader = {"序号", "商品", "数量", "单位", "含税单价(元)", "税率", "金额(元)", "预计交付日期"};

            // 添加表头元素
            for (int i = 0; i < colNumber; i++) {
                PdfPCell headerCell=new PdfPCell(new Paragraph(tableHeader[i], size12BlackFont));
                headerCell.setBackgroundColor(borderGrayColor);
                headerCell.setBorderColor(backGroudDeepGray);
                datatable.addCell(headerCell);
            }
            //采购需求列表信息
            List<ContractInfoitemListVO> itemList = contractInfoVO.getItemList();
            //数量合计
            BigDecimal totalCount = BigDecimal.ZERO;
            //金额合计
            BigDecimal totalPrice = BigDecimal.ZERO;
            // 添加表格的内容
            if (ObjectUtil.isNotEmpty(itemList)) {
                for (int j = 0; j < itemList.size(); j++) {
                    Map<String,String> cellMap=new HashMap<>(3);
                    ContractInfoitemListVO infoitemVO = itemList.get(j);
                    String itemName = infoitemVO.getItemName()==null?"":infoitemVO.getItemName();
                    cellMap.put("itemName",itemName);
                    String brandName = infoitemVO.getBrandName()==null?"":infoitemVO.getBrandName();
                    cellMap.put("brandName",brandName);
                    String paramters = infoitemVO.getParamters()==null?"":infoitemVO.getParamters();
                    cellMap.put("paramters",paramters);
                    String quantity = infoitemVO.getQuantity()==null?"":infoitemVO.getQuantity().setScale(3,BigDecimal.ROUND_HALF_DOWN).toString();
                    String unit = infoitemVO.getUnit()==null?"":infoitemVO.getUnit();
                    String unitPrice = infoitemVO.getUnitPrice()==null?"":infoitemVO.getUnitPrice().toString();
                    String taxRate = infoitemVO.getTaxRate()==null?"":infoitemVO.getTaxRate()+"%";
                    String  anountPrice = infoitemVO.getAnountPrice()==null?"":infoitemVO.getAnountPrice().toString();
                    String  expectDate = infoitemVO.getExpectDate()==null?"":DateUtils.formatDate(new Date(infoitemVO.getExpectDate()), "yyyy-MM-dd");
                    //循环遍历表格内容
                    String[] tableCont = {j + 1 +"", "",
                            quantity,unit ,unitPrice,taxRate,anountPrice,expectDate};
                    for (int i = 0; i < colNumber; i++) {
                        Paragraph paragh=new Paragraph();
                        paragh.setAlignment(Element.ALIGN_LEFT);
                        if(i==1){
                            paragh.add(new Chunk(cellMap.get("itemName")+"\n",size12SemiboldFont));
                            paragh.add(new Chunk("品牌:", size12SemiboldFont));
                            paragh.add(new Chunk(cellMap.get("brandName")+"\n",size12GrayFont));
                            paragh.add(new Chunk("技术参数:", size12SemiboldFont));
                            paragh.add(new Phrase(cellMap.get("paramters"),size12GrayFont));
                        }else{
                            paragh=new Paragraph(tableCont[i], size12GrayFont);
                        }
                        PdfPCell pdfPCell=new PdfPCell(paragh);
                        if(j%2==0){
                            pdfPCell.setBackgroundColor(whiteBorderColor);
                        }else{
                            pdfPCell.setBackgroundColor(borderGrayColor);
                        }
                        pdfPCell.setPaddingTop(5);
                        pdfPCell.setPaddingBottom(5);
                        pdfPCell.setBorderColor(backGroudDeepGray);
                        datatable.addCell(pdfPCell);
                        if (i == 2) {
                            totalCount = totalCount.add(new BigDecimal(tableCont[i]));
                        }
                        if (i == 6) {
                            totalPrice = totalPrice.add(new BigDecimal(tableCont[i]));
                        }
                    }
                }
            }
            // 单元格
            PdfPCell totalcell = new PdfPCell(new Phrase("合计", size12GrayFont));
            totalcell.setBorderColor(backGroudDeepGray);
            // 跨2列
            totalcell.setColspan(2);
            datatable.addCell(totalcell);
            //数量合计
            BigDecimal totalAmount = contractInfoVO.getTotalAmount();
            PdfPCell totalNum = new PdfPCell(new Phrase(
                    totalAmount == null ? totalCount.toString() : totalAmount.toString(), size12GrayFont));
            totalNum.setBorderColor(backGroudDeepGray);
            datatable.addCell(totalNum);
            //空格
            PdfPCell blankThreeCell = new PdfPCell(new Phrase("",size12GrayFont));
            blankThreeCell.setColspan(3);
            blankThreeCell.setBorderColor(backGroudDeepGray);
            datatable.addCell(blankThreeCell);
            //金额合计
            PdfPCell totalMoney = new PdfPCell(new Phrase(totalPrice.toString(), size12BlackFont));

            totalMoney.setBorderColor(backGroudDeepGray);
            datatable.addCell(totalMoney);
            //空格
            PdfPCell blankOneCell = new PdfPCell(new Phrase("", size12GrayFont));
            blankOneCell.setBorderColor(backGroudDeepGray);
            datatable.addCell(blankOneCell);
            // 设置表格下面空白行
            datatable.setSpacingAfter(5f);

            //添加备注
            String contractRemarks = contractInfoVO.getContractRemarks();
            if(StringUtil.isNotBlank(contractRemarks)){
                Paragraph paragRemark = new Paragraph();
                paragRemark.add(new Chunk("备注: "+(contractRemarks == null ? "" : contractRemarks), size12BlackFont));
                PdfPCell remarkCell=new PdfPCell(paragRemark);
                remarkCell.setColspan(8);
                remarkCell.setPaddingTop(3);
                remarkCell.setPaddingBottom(3);
                remarkCell.setBorder(1);
                remarkCell.setBorderColor(backGroudDeepGray);
                remarkCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                datatable.addCell(remarkCell);

            }

            // 把表格加入文档
            document.add(datatable);
            document.add(Chunk.NEWLINE);
            //第四部分 结算信息

            //结算信息集合
            List<BillingInformationVO> billingInformation = contractInfoVO.getBillingInformation();
            if(ObjectUtil.isNotEmpty(billingInformation)&&billingInformation.size()>0){

                int balanceNumber = 5;
                PdfPTable balanceTable = new PdfPTable(balanceNumber);
                int[] cellsBalanceWidth = {4, 2, 1, 1, 2};
                // 单元格宽度
                balanceTable.setWidths(cellsBalanceWidth);
                Phrase balancePhrase=new Phrase();
                balancePhrase.add(new Chunk("结算信息",size14BlackFont));
                PdfPCell balanceCell=new PdfPCell(balancePhrase);
                balanceCell.setColspan(5);
                balanceCell.setFixedHeight(20);
                balanceCell.setBorder(1);
                balanceCell.setBorderColor(whiteBorderColor);
                balanceCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                balanceTable.addCell(balanceCell);

                balanceTable.setWidthPercentage(100);
                // 单元格的间隔
                balanceTable.getDefaultCell().setPadding(1);
                // 边框宽度
                balanceTable.getDefaultCell().setBorderColor(backGroudDeepGray);
                balanceTable.getDefaultCell().setBorderWidth(1);
                // 设置表格的底色
                balanceTable.getDefaultCell().setBackgroundColor(backGroundColor);
                balanceTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                balanceTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
                balanceTable.getDefaultCell().setPadding(5);
                balanceTable.setSpacingBefore(2f);
                balanceTable.setSpacingAfter(5f);
                //表头数组
                String[] balanTableHeader = {"收款说明", "开始日期", "收款期限(天)", "金额占比", "金额"};

                // 添加表头元素
                for (int i = 0; i < balanceNumber; i++) {
                    PdfPCell banlanPdfPCell=new PdfPCell(new Paragraph(balanTableHeader[i], size12SemiboldFont));
                    //设置背景颜色
                    banlanPdfPCell.setBackgroundColor(borderGrayColor);
                    banlanPdfPCell.setBorderColor(backGroudDeepGray);
                    balanceTable.addCell(banlanPdfPCell);
                }
                //结算总金额
                BigDecimal balanTotalAmount = BigDecimal.ZERO;
                for (int j = 0; j < billingInformation.size(); j++) {
                    BillingInformationVO billingInfoVO = billingInformation.get(j);
                    String receiptsExplain = billingInfoVO.getReceiptsExplain();
                    Long startDate = billingInfoVO.getStartDate();
                    String limitDay = billingInfoVO.getLimitDay();
                    BigDecimal proportion = billingInfoVO.getProportion();
                    BigDecimal amount = billingInfoVO.getAmount();
                    String[] balanContent = {receiptsExplain==null?"":receiptsExplain,startDate==null?"":DateUtils.formatDate(new Date(startDate), "yyyy-MM-dd"),
                            limitDay==null?"":limitDay, proportion==null?"":proportion.toString()+"%",amount==null?"":amount.toString()};
                    for (int k = 0; k < balanceNumber; k++) {
                        Paragraph paragraph=new Paragraph(new Paragraph(balanContent[k], size12GrayFont));
                        PdfPCell pdfPCell=new PdfPCell(paragraph);
                        if(j%2==0){
                            pdfPCell.setBackgroundColor(whiteBorderColor);
                        }else{
                            pdfPCell.setBackgroundColor(borderGrayColor);
                        }
                        pdfPCell.setPaddingTop(5);
                        pdfPCell.setPaddingBottom(5);
                        pdfPCell.setBorderColor(backGroudDeepGray);
                        balanceTable.addCell(pdfPCell);
                        if (k == 4) {
                            balanTotalAmount = balanTotalAmount.add(new BigDecimal(balanContent[k]));
                        }
                    }
                }

                // 结算单元行
                PdfPCell balanTotalCell = new PdfPCell(new Phrase("合计", size12GrayFont));
                // 跨4列
                balanTotalCell.setColspan(4);
                balanTotalCell.setBorderColor(backGroudDeepGray);
                balanceTable.addCell(balanTotalCell);
                PdfPCell balanTotalAmountCell = new PdfPCell(new Phrase(
                        balanTotalAmount == null ? "" : balanTotalAmount.toString(),size12BlackFont));
                balanTotalAmountCell.setBorderColor(backGroudDeepGray);
                balanceTable.addCell(balanTotalAmountCell);
                document.add(balanceTable);
            }

            //第五部分 发票信息
            Phrase billPhrase = new Phrase();
            billPhrase.add(new Chunk("发票信息", size14BlackFont));
            PdfPCell billInfoCell=new PdfPCell(billPhrase);
            billInfoCell.setColspan(2);
            billInfoCell.setFixedHeight(20);
            billInfoCell.setBorder(1);
            billInfoCell.setBorderColor(whiteBorderColor);
            billInfoCell.setHorizontalAlignment(Element.ALIGN_LEFT);

            int billNumber = 2;
            PdfPTable billTable=new PdfPTable(billNumber);
            billTable.addCell(billInfoCell);
            // 定义表格的宽度
            int[] billCellsWidth = {3,7};
            // 单元格宽度
            billTable.setWidths(billCellsWidth);
            // 表格的宽度百分比
            billTable.setWidthPercentage(50);
            // 单元格的间隔
            billTable.getDefaultCell().setPadding(1);
            // 边框宽度
            billTable.getDefaultCell().setBorderWidth(1);
            billTable.setSpacingBefore(2f);
            billTable.setSpacingAfter(5f);
            billTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            billTable.getDefaultCell().setPadding(5);
            //垂直居中
            billTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
            PdfPCell companyNameCell=new PdfPCell(new Paragraph("单位名称", size12SemiboldFont));
            companyNameCell.setBackgroundColor(borderGrayColor);
            companyNameCell.setPaddingTop(5);
            companyNameCell.setPaddingBottom(5);
            companyNameCell.setBorderColor( backGroudDeepGray);
            billTable.addCell(companyNameCell);
            String companyName = contractInfoVO.getCompanyName();
            PdfPCell comNameCell=new PdfPCell(new Paragraph(companyName==null?"":companyName, size12GrayFont));
            comNameCell.setBorderColor(backGroudDeepGray);
            comNameCell.setPaddingTop(5);
            comNameCell.setPaddingBottom(5);
            billTable.addCell(comNameCell);

            PdfPCell identCodeCell=new PdfPCell(new Paragraph("纳税人识别号", size12SemiboldFont));
            identCodeCell.setBorderColor( backGroudDeepGray);
            identCodeCell.setBackgroundColor(borderGrayColor);
            identCodeCell.setPaddingTop(5);
            identCodeCell.setPaddingBottom(5);
            billTable.addCell(identCodeCell);
            String identificationCode = contractInfoVO.getIdentificationCode();

            PdfPCell ficationCodeCell=new PdfPCell(new Paragraph(identificationCode==null?"":identificationCode,size12GrayFont));
            ficationCodeCell.setBorderColor(backGroudDeepGray);
            ficationCodeCell.setPaddingTop(5);
            ficationCodeCell.setPaddingBottom(5);
            billTable.addCell(ficationCodeCell);

            PdfPCell addressCell=new PdfPCell(new Paragraph("单位地址", size12SemiboldFont));
            addressCell.setBorderColor( backGroudDeepGray);
            addressCell.setBackgroundColor(borderGrayColor);
            addressCell.setPaddingTop(5);
            addressCell.setPaddingBottom(5);
            billTable.addCell(addressCell);
            String companyAddress = contractInfoVO.getCompanyAddress();
            PdfPCell comAddressCell=new PdfPCell(new Paragraph(companyAddress==null?"":companyAddress, size12GrayFont));
            comAddressCell.setBorderColor(backGroudDeepGray);
            comAddressCell.setPaddingTop(5);
            comAddressCell.setPaddingBottom(5);
            billTable.addCell(comAddressCell);

            PdfPCell telCell=new PdfPCell(new Paragraph("电话", size12SemiboldFont));
            telCell.setBackgroundColor(borderGrayColor);
            telCell.setBorderColor( backGroudDeepGray);
            telCell.setPaddingTop(5);
            telCell.setPaddingBottom(5);
            billTable.addCell(telCell);
            String tel = contractInfoVO.getTel();
            PdfPCell phoneCell=new PdfPCell(new Paragraph(tel==null?"":tel, size12GrayFont));
            phoneCell.setBorderColor(backGroudDeepGray);
            phoneCell.setPaddingTop(5);
            phoneCell.setPaddingBottom(5);
            billTable.addCell(phoneCell);

            PdfPCell cardNumCell=new PdfPCell(new Paragraph("开户行及账号", size12SemiboldFont));
            cardNumCell.setBackgroundColor(borderGrayColor);
            cardNumCell.setPaddingTop(5);
            cardNumCell.setPaddingBottom(5);
            cardNumCell.setBorderColor( backGroudDeepGray);
            billTable.addCell(cardNumCell);
            String bankName = contractInfoVO.getBankName();
            String cardNumber = contractInfoVO.getCardNumber();
            PdfPCell cardNoCell=new PdfPCell(new Paragraph(bankName==null?"":bankName+" "+cardNumber==null?"":cardNumber, size12GrayFont));
            cardNoCell.setBorderColor(backGroudDeepGray);
            cardNoCell.setPaddingTop(5);
            cardNoCell.setPaddingBottom(5);
            billTable.addCell(cardNoCell);
            document.add(billTable);
            //第六部分 合同状态
            String status = contractInfoVO.getStatus();
            if(StringUtil.isNotBlank(status)&&status.equals("60")){
                PdfPTable conTable=new PdfPTable(2);
                int conWidth[]={2,8};
                conTable.setWidths(conWidth);
                conTable.getDefaultCell().setBorder(1);
                conTable.getDefaultCell().setBorderColor(whiteBorderColor);
                conTable.getDefaultCell().setPadding(3);
                conTable.setHorizontalAlignment(Element.ALIGN_LEFT);

                String confirmer = contractInfoVO.getConfirmer();
                Long confirmDateTime = contractInfoVO.getConfirmDateTime();
                String firstParty = contractInfoVO.getFirstParty();
                String secondParty = contractInfoVO.getSecondParty();
                Long takeEffectDateTime = contractInfoVO.getTakeEffectDateTime();
                //合同生效
                if(StringUtil.isNotBlank(confirmer)||ObjectUtil.isNotEmpty(confirmDateTime)||
                        StringUtil.isNotBlank(firstParty)||StringUtil.isNotBlank(secondParty)||
                        ObjectUtil.isNotEmpty(takeEffectDateTime)){
                    Phrase conInfoPara = new Phrase("确认信息",size14BlackFont);
                    PdfPCell conInfoCell=new PdfPCell(conInfoPara);
                    conInfoCell.setColspan(2);
                    conInfoCell.setFixedHeight(20);
                    conInfoCell.setBorder(1);
                    conInfoCell.setBorderColor(whiteBorderColor);
                    conInfoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    conTable.addCell(conInfoCell);
                }
                Phrase  confirmPhrase= new Phrase();
                if(StringUtil.isNotBlank(confirmer)){
                    conTable.addCell(new Phrase("确认人" , size12GrayFont));
                    conTable.addCell(new Phrase(confirmer == null ? "" : confirmer,size12GrayFont));
                }
                if(ObjectUtil.isNotEmpty(confirmDateTime)){
                    String confirmerTime =confirmDateTime==null?"":DateUtils.formatDate(new Date(confirmDateTime), "yyyy-MM-dd");
                    conTable.addCell(new Phrase("确认时间" , size12GrayFont));
                    conTable.addCell(new Phrase(confirmerTime,size12GrayFont));
                }

                if(StringUtil.isNotBlank(firstParty)){
                    conTable.addCell(new Phrase("甲方签约人" , size12GrayFont));
                    conTable.addCell(new Phrase(firstParty == null ? "" : firstParty,size12GrayFont));
                }

                if(StringUtil.isNotBlank(secondParty)){
                    conTable.addCell(new Phrase("已方签约人" , size12GrayFont));
                    conTable.addCell(new Phrase(secondParty == null ? "" : secondParty,size12GrayFont));
                }
                if(ObjectUtil.isNotEmpty(takeEffectDateTime)){
                    String takeEffectTime = takeEffectDateTime==null?"":DateUtils.formatDate(new Date(takeEffectDateTime), "yyyy-MM-dd");
                    conTable.addCell(new Phrase("签约时间" , size12GrayFont));
                    conTable.addCell(new Phrase(takeEffectTime,size12GrayFont));
                }
                document.add(conTable);
            }else{
                PdfPTable signTable=new PdfPTable(2);
                int conWidth[]={7,3};
                signTable.setWidths(conWidth);
                signTable.getDefaultCell().setBorder(1);
                signTable.getDefaultCell().setBorderColor(whiteBorderColor);
                signTable.getDefaultCell().setPadding(20);
                signTable.setHorizontalAlignment(Element.ALIGN_LEFT);
                //合同待需方签约 签字盖章
                signTable.addCell(new Phrase("甲方(盖章):", size12GrayFont));

                signTable.addCell(new Paragraph("乙方(盖章):",size12GrayFont));
                PdfPCell blankSignCell=new PdfPCell();
                blankSignCell.setColspan(2);
                blankSignCell.setRowspan(4);
                blankSignCell.setBorder(1);
                blankSignCell.setBorderColor(whiteBorderColor);
                signTable.addCell(blankSignCell);
                signTable.addCell(new Phrase("授权代表签字:", size12GrayFont));
                signTable.addCell(new Paragraph("授权代表签字:", size12GrayFont));
                signTable.addCell(blankSignCell);
                signTable.addCell(new Phrase("日期:", size12GrayFont));
                signTable.addCell(new Paragraph("日期:", size12GrayFont));
                document.add(signTable);
            }
            //添加页脚
            PdfReportM1HeaderFooter footer=new PdfReportM1HeaderFooter();
            footer.onOpenDocument(writer,document);
            footer.onStartPage(writer,document);
            footer.onEndPage(writer,document);
            footer.onCloseDocument(writer,document);
            writer.setPageEvent(footer);
            document.close();
            //添加水印

            Map<String, String> resultMap = new HashMap<>(2);
//            byte[] bytes = out.toByteArray();
          String filePath=addShuiYinByTempete(out,"万郡绿建",300,360);
            resultMap.put("fileName", contractInfoVO.getContractCode()+ ".pdf");
            resultMap.put("filePath", filePath);
            return  resultMap;
        } catch (DocumentException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //添加水印
    public String addShuiYinByTempete(ByteArrayOutputStream fos, String text,int textWidth,
                                    int textHeight){

        // 生成的文件路径
//        String tPdfResultFile ="C:\\Users\\admin\\Desktop\\testTempletePdf" + new Random().nextInt() + ".pdf";
        PdfReader reader=null;
        try {
            // 2-解析PDF模板
            // 需要生成PDF
//            FileOutputStream fos = new FileOutputStream(tPdfResultFile);
            // 待加水印的文件
            reader= new PdfReader(fos.toByteArray());
            // 加完水印的文件
            PdfStamper stamper = new PdfStamper(reader, fos);

            PdfContentByte content;
            //pdf页数
            int total = reader.getNumberOfPages() + 1;
            //字体
            BaseFont font =BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            // 水印文字
            String waterText = text;
            // 高度
            int high = 0;
            // 循环对每页插入水印
            for (int i = 1; i < total; i++) {
                // 水印的起始
                high = 50;
                // 水印在之前文本之上
                content = stamper.getOverContent(i);

                if (StringUtil.isNotBlank(text)) {
                    // 开始
                    content.beginText();
                    // 设置颜色 默认为蓝色
                    content.setColorFill(new BaseColor(189,189,189));
                    PdfGState gs = new PdfGState();
                    // 设置透明度为0.3
                    gs.setFillOpacity(0.30f);
                    content.setGState(gs);
                    // 设置字体及字号
                    content.setFontAndSize(font, 120);
                    // 设置起始位置
                    content.setTextMatrix(textWidth, textHeight);
                    // 开始写入水印
                    content.showTextAligned(Element.ALIGN_CENTER, text, textWidth, textHeight,45);
                    content.endText();
                }
            }
            stamper.close();
            byte[] waterMarkBytes = fos.toByteArray();
            //上传到FastDFS
            String filePath = storageService.upload(waterMarkBytes, "pdf");
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }finally {
            if(reader != null) {
                reader.close();
            }
        }
        return null;
    }

    /**
     * 中文支持
     * @return
     */
    public Font getChineseFont(int Size,String fontName,BaseColor baseColor) {
        BaseFont bfChinese;
        Font fontChinese = null;
        try {
            bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            // fontChinese = new Font(bfChinese, 12, Font.NORMAL);
            if(Size==9){
                fontChinese = new Font(bfChinese, Size, Font.BOLD,baseColor);
            }else{
                fontChinese = new Font(bfChinese, Size, Font.NORMAL,baseColor);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fontChinese;
    }

    /**
     * 业务单据附件删除
     *
     * @param billId
     * @param businessType
     * @param fromType
     * @param accountId
     * @param accountName
     * @param attachmentIdList
     * @return
     * @throws FindContractException
     */
    @Override
    public Boolean deleteAttachment(String billId, String businessType, String fromType,
                                    String accountId, String accountName, String[]
                                            attachmentIdList) throws FindContractException {
        if (StringUtil.isBlank(billId) || StringUtil.isBlank(businessType) || StringUtil.isBlank
                (fromType) ||
                StringUtil.isBlank(accountId) || StringUtil.isBlank(accountName) || null ==
                attachmentIdList) {
            throw new FindContractException(ResultStatusConstant
                    .CONTRACT_PARAMETERS_INTRODUCE_INTO_NULL);
        }
        AttachMent2BXDTO attachMent2BXDTO = new AttachMent2BXDTO();
        try {
            attachMent2BXDTO.setBillId(billId);
            attachMent2BXDTO.setBusinessType(businessType);
            attachMent2BXDTO.setFromType(fromType);
            attachMent2BXDTO.setAccountId(accountId);
            attachMent2BXDTO.setAccountName(accountName);
            if (null != attachmentIdList && attachmentIdList.length > 0) {
                List<String> attList = Arrays.asList(attachmentIdList);
                attachMent2BXDTO.setAttachmentIdList(attList);
            }
            String resultStatus = apiExecutorBxService.deleteAttachment(attachMent2BXDTO);
            if (null != resultStatus && !"".equals(resultStatus)) {
                BxApiResult bxApiResult = JSONUtil.toBean(resultStatus, BxApiResult.class);
                if (null != bxApiResult) {
                    if ("1".equals(bxApiResult.getStatus().toString())) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindContractException(ResultStatusConstant.DEMAND_SOLUTION_PARAM_CAN_NOT_NULL);
        }
        return false;
    }

    /**
     *
     *
     */

    public void getApiExecutorBxService(String returnResult) throws Exception {
        if (StringUtil.isNotBlank(returnResult)) {
            BxApiResult bxApiResult = JSONUtil.toBean(returnResult, BxApiResult.class);
            if (0 == bxApiResult.getStatus()) {
                logger.error("调用接口返回数据错误");
                FindContractException findContractException = new FindContractException
                        (ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
                findContractException.setMessage(bxApiResult.getMessage());
                throw findContractException;
            }
        }
    }

    public String getUnitName(String unitCode){
        //获取商品单位结果集
        List<ValueSetLineDTO> unitTypeList = valueSetLineDAO.findListByValueSetHeadId("tlerp.ausbs.unitType");
        Map<String,String> unitTypeMap=new HashMap<>();
        for (ValueSetLineDTO valueSetLineDTO : unitTypeList) {
            unitTypeMap.put(valueSetLineDTO.getValueCode(), valueSetLineDTO.getValueName());
        }
        String unitName = unitTypeMap.get(unitCode);
        return unitName;
    }

    /**
     * 检查商品版本是否变化
     * @param contractListVOList
     */
    private void  checkOrderItemChange( List<ContractListVO> contractListVOList){
        Set<String> contactHeadIds =  new HashSet<>(contractListVOList.size());
        try {
            contractListVOList.forEach(purchaseOrderListVO -> contactHeadIds.add(purchaseOrderListVO.getContractId()));
            List<ContractSnapshotDTO> contractSnapshotDTOS = snapshotDAO.findContractSnapshotList(contactHeadIds);
            Set<String> itemLineIds = new HashSet<>();
            contractSnapshotDTOS.forEach(contractSnapshotDTO -> contractSnapshotDTO.getItems().forEach(
                    item -> {
                        itemLineIds.add(item.getItemLineId());
                    }
            ));

            Map itemLineMap = snapshotService.getItemLineMap(itemLineIds);
            Map<String, Boolean> purchaseHeadStatusMap = new HashMap();
            String purchaseHeadId = null;
            List<ItemSnapshotVersionDTO> itemSnapshotDTOs = null;
            for (int i = 0; i < contractSnapshotDTOS.size(); i++) {
                purchaseHeadId = contractSnapshotDTOS.get(i).getContractHeadId();
                purchaseHeadStatusMap.put(purchaseHeadId, true);
                itemSnapshotDTOs = contractSnapshotDTOS.get(i).getItems();
                for (ItemSnapshotVersionDTO itemSnapshotDTO : itemSnapshotDTOs) {
                    if ((ObjectUtil.isNotEmpty(itemSnapshotDTO.getItemLineVersion()))&&(ObjectUtil.isNotEmpty(itemLineMap.get(itemSnapshotDTO.getItemLineId())) && !itemLineMap.get(itemSnapshotDTO.getItemLineId()).equals(itemSnapshotDTO.getItemLineVersion()))) {
                        purchaseHeadStatusMap.put(purchaseHeadId, false);
                        break;
                    }
                }
            }
            contractListVOList.forEach(item -> item.setIsChanged(purchaseHeadStatusMap.get(item.getContractId())));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void checkOrderItemChanged( List<ContractInfoitemListVO> itemInfo) {
        Set<String> itemIds = new HashSet<>();
        Set<String> itemLineIds = new HashSet<>();
        itemInfo.forEach(item -> {
            itemLineIds.add(item.getItemLineId());
        });
      
        Map itemLineMap = snapshotService.getItemLineMap(itemLineIds);
        for (ContractInfoitemListVO lineDTO : itemInfo) {
            if (ObjectUtil.isNotEmpty(lineDTO.getOrderItemVersion())) {
                lineDTO.setItemVersion((String)itemLineMap.get(lineDTO.getItemLineId()));

            }
        }
    }
}