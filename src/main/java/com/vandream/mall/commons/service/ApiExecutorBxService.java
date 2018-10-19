package com.vandream.mall.commons.service;

import com.vandream.mall.business.dto.BaseDTO;
import com.vandream.mall.business.dto.delivery.DeliverySubmitDTO;
import com.vandream.mall.business.dto.subAccount.BindRoleDTO;
import com.vandream.mall.business.dto.subAccount.SaveSubAccountDTO;
import com.vandream.mall.business.dto.subAccount.UpdatePermissionDTO;
import com.vandream.mall.business.dto.subAccount.UpdateSubAccountDTO;
import com.vandream.mall.business.vo.delivery.DeliverySubmitVO;
import org.springframework.stereotype.Component;

/**
 * @author dingjie
 * @date 2018/3/20
 * @time 10:22
 * Description:调用宝信API
 */
@Component
public class ApiExecutorBxService {
    /**
     * 创建订单
     * @return
     */
    public String createSomOrder(BaseDTO baseDTO) throws Exception{
        return null;
    }
    /**
     * 业务单据附件删除
     * @param baseDTO
     * @return
     * @throws Exception
     */
    public String deleteAttachment(BaseDTO baseDTO) throws Exception {
        return null;
    }
    /**
     * 供方确定订单
     * @param baseDTO
     * @return
     * @throws Exception
     */
    public String confirmOrder(BaseDTO baseDTO) throws Exception {
        return null;
    }
    /**
     *  发货单签收
     * @param baseDTO
     * @return
     * @throws Exception
     */
    public String logisticsReceipt(BaseDTO baseDTO) throws Exception {
        return null;
    }
    /**
     * 修改单据状态
     * @param baseDTO
     * @return
     * @throws Exception
     */
    public String updateBillStatus(BaseDTO baseDTO) throws Exception {
        return null;
    }

    /**
     * 销售合同付款凭证上传
     * @param baseDTO
     * @return
     * @throws Exception
     */
    public String voucherUpload(BaseDTO baseDTO) throws Exception {
        return null;
    }
    /**
     *发布采购需求
     * @param baseDTO
     * @return
     */
    public String addDemandInfo(BaseDTO baseDTO) throws Exception {
        return null;
    }
    /**
     *修改需求方单条方案状态
     */
    public String updateSchemeStatus(BaseDTO baseDTO) throws Exception {
        return null;
    }
    /**
     * 上传供方解决方案附件
     */
    public String uploadAttachment(BaseDTO baseDTO) throws Exception{
        return null;
    }

    /**
     * 上传供方解决方案附件-新
     * @param baseDTO
     * @return
     * @throws Exception
     */
    public String supplierSubmit(BaseDTO baseDTO) throws Exception{
        return null;
    }
    /**
     *需方确认合同
     */
    public String confirmationContract(BaseDTO baseDTO) throws Exception {
        return null;
    }

    /**
     *发送短信验证码
     * @param baseDTO
     * @return
     */
    public String sendSmsCode(BaseDTO baseDTO) throws Exception {
        return null;
    }


    /**
     *用户注册
     * @param baseDTO
     * @return
     */
    public String register(BaseDTO baseDTO)throws Exception{
        return null;
    }


    /**
     * 需方会员认证接口
     * @param baseDTO
     * @return
     */
    public String customerAuthentication(BaseDTO baseDTO)throws Exception{
        return null;
    }

    /**
     * 供方会员认证
     * @param baseDTO
     * @return
     */
    public String supplierAuthentication(BaseDTO baseDTO)throws Exception{
        return null;
    }


    /**
     * 修改密码
     * @param baseDTO
     * @return
     */
    public String modifyPassword(BaseDTO baseDTO)throws Exception{
        return null;
    }

    /**
     * 修改手机号
     * @param baseDTO
     * @return
     */
    public String modifyPhone(BaseDTO baseDTO)throws Exception{
        return null;
    }

    /**
     * 新增/编辑收货地址
     * @param baseDTO
     * @return
     */
    public String modifyAddress(BaseDTO baseDTO)throws Exception{
        return null;
    }

    /**
     * 删除地址
     * @param baseDTO
     * @return
     */
    public String deleteAddress(BaseDTO baseDTO)throws Exception{
        return null;
    }

    /**
     * 设置默认地址
     * @param baseDTO
     * @return
     */
    public String setDefaultAddress(BaseDTO baseDTO)throws Exception{
        return null;
    }

    /**
     * 提交发货信息
     * @param deliverySubmitDTO
     * @return
     */
    public String submitDeliveryInfo(DeliverySubmitDTO deliverySubmitDTO)throws Exception{
        return null;
    }

    /**
     * 修改子账户信息
     * @param updateSubAccountDTO
     * @return
     * @throws Exception
     */
    public String updateSubAccount(UpdateSubAccountDTO updateSubAccountDTO) throws Exception {
        return null;
    }

    public String updatePermission(UpdatePermissionDTO updatePermissionDTO)throws Exception {
        return null;
    }

    /**
     * 添加子账户
     *
     * @param saveSubAccountDTO
     * @return
     * @throws Exception
     */
    public String saveSubAccount(SaveSubAccountDTO saveSubAccountDTO) throws Exception {
        return null;
    }

    /**
     * 账号绑定角色
     * @param bindRoleDTO
     * @return
     * @throws Exception
     */
    public String bindRole(BindRoleDTO bindRoleDTO) throws Exception {
        return null;
    }
}
