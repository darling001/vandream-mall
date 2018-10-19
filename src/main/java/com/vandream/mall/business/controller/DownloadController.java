package com.vandream.mall.business.controller;

import com.vandream.mall.business.service.SolutionService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Li Jie
 */
@Controller
public class DownloadController {
    @Resource
    private SolutionService solutionService;


    @RequestMapping(value = "/download/solutionPurchaseExcel", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> getSolutionPurchaseExcel(HttpServletRequest request,
                                                           HttpServletResponse response
    ) throws Exception {
        //String userId = request.getParameter("userId");
        //String supplierId = request.getParameter("supplierId");
        //String solutionId = request.getParameter("solutionId");
        //String solutionCode = request.getParameter("solutionCode");
        //if (StringUtil.isBlank(userId)) {
        //    throw new BusinessException(ResultStatusConstant.INPUT_PARAM_ERROR);
        //}
        //if (StringUtil.isBlank(supplierId)) {
        //    throw new BusinessException(ResultStatusConstant.INPUT_PARAM_ERROR);
        //}
        //if (StringUtil.isBlank(solutionId)) {
        //    throw new BusinessException(ResultStatusConstant.INPUT_PARAM_ERROR);
        //}
        //if (StringUtil.isBlank(solutionCode)) {
        //    throw new BusinessException(ResultStatusConstant.INPUT_PARAM_ERROR);
        //}
        //userId= userId.replaceAll("\"", "");
        //supplierId= supplierId.replaceAll("\"", "");
        //solutionId= solutionId.replaceAll("\"", "");
        //solutionCode= solutionCode.replaceAll("\"", "");
        //
        ////生成Excel文件
        //Workbook workbook = solutionService.generatorSolutionPurchaseExcel(userId, supplierId,
        //        solutionId);
        //
        //ByteArrayOutputStream os = new ByteArrayOutputStream();
        //byte[] bytes = null;
        //try {
        //    workbook.write(os);
        //    bytes = os.toByteArray();
        //    os.close();
        //} catch (Exception e) {
        //    logger.error("{}",e.getMessage(),e);
        //    os.close();
        //} finally {
        //    os.close();
        //}
        //
        //HttpHeaders headers = new HttpHeaders();
        //headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;" +
        //        "filename=" + solutionCode + ".xlsx");
        //headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //
        //
        //ResponseEntity<byte[]> result = new ResponseEntity<>(bytes, headers,
        //        HttpStatus.OK);
        //return result;
        return null;

    }
}
