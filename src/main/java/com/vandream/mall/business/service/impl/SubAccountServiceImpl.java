package com.vandream.mall.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vandream.mall.business.constant.OperationType;
import com.vandream.mall.business.dao.SubAccountDAO;
import com.vandream.mall.business.dto.BxApiResult;
import com.vandream.mall.business.dto.subAccount.*;
import com.vandream.mall.business.execption.SubAccountException;
import com.vandream.mall.business.execption.SystemException;
import com.vandream.mall.business.service.SecurityService;
import com.vandream.mall.business.service.SubAccountService;
import com.vandream.mall.business.service.VerifyCodeService;
import com.vandream.mall.business.vo.subAccount.*;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.service.ApiExecutorBxService;
import com.vandream.mall.commons.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liuyuhong
 * @date 2018/5/25
 * @time 11:33
 * @description
 */
@Service("subAccountService")
public class SubAccountServiceImpl implements SubAccountService {

    /**
     * 注销
     **/
    private String CANCELLED = "CANCELLED";

    /**
     * 启用
     **/
    private String ENABLED = "ENABLED";

    /**
     * 停用
     **/
    private String DISABLE = "DISABLE";

    private String MENU_ID_LIST = "infPermissionInfoList";

    private static final Logger logger = LoggerFactory.getLogger(SubAccountService.class);

    @Autowired
    private SubAccountDAO subAccountDAO;

    @Autowired
    private ApiExecutorBxService apiExecutorBxService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private VerifyCodeService verifyCodeService;

    /**
     * 获取子账户列表
     *
     * @param findSubAccountVO
     * @return
     * @throws SubAccountException
     */
    @Override
    public SubAccountsVO findSubAccountList(FindSubAccountVO findSubAccountVO) throws SubAccountException {
        Map<String, Object> validation = ValidatorUtils.validation(findSubAccountVO);
        if (ObjectUtil.isNotEmpty(validation)) {
            SubAccountException subAccountException = new SubAccountException(ResultStatusConstant.INPUT_PARAM_ERROR);
            subAccountException.setMessage(JSON.toJSONString(validation));
            throw subAccountException;
        }
        PageHelper.startPage(findSubAccountVO.getPageNo(), findSubAccountVO.getPageSize());
        FindSubAccountDTO findSubAccountDTO = null;
        try {
            findSubAccountDTO = ObjectUtil.transfer(findSubAccountVO, FindSubAccountDTO.class);
        } catch (SystemException e) {
            logger.error("获取企业子账户列表传入参数数据转换失败", findSubAccountVO, e.getMessage());
            throw new SubAccountException(ResultStatusConstant.PARAM_FORMAT_FAILURE);
        }
        List<SubAccountListDTO> subAccountList = subAccountDAO.findSubAccountList(findSubAccountDTO);
        SubAccountsVO subAccountsVO = null;
        if (ObjectUtil.isNotEmpty(subAccountList)) {
            PageInfo<SubAccountListDTO> pageInfo = new PageInfo<>(subAccountList);
            subAccountsVO = new SubAccountsVO(pageInfo);
            subAccountsVO.setSubAccountList(subAccountList);
        } else {
            subAccountsVO = new SubAccountsVO(findSubAccountVO.getPageNo());
        }
        subAccountsVO.setParentAccountId(findSubAccountVO.getParentAccountId());
        logger.debug("subAccountsVO: \n{}", subAccountsVO);
        return subAccountsVO;
    }

    /**
     * 修改子账户
     *
     * @param updateSubAccountVO
     * @return
     * @throws SubAccountException
     */
    @Override
    public String updateSubAccount(UpdateSubAccountVO updateSubAccountVO) throws SubAccountException {
        Map<String, Object> validation = ValidatorUtils.validation(updateSubAccountVO);
        //入参校验
        if (ObjectUtil.isNotEmpty(validation)) {
            SubAccountException subAccountException = new SubAccountException(ResultStatusConstant.INPUT_PARAM_ERROR);
            subAccountException.setMessage(JSON.toJSONString(validation));
            throw subAccountException;
        }
        if (StringUtil.isNotBlank(updateSubAccountVO.getPassword()) && StringUtil.isNotBlank(updateSubAccountVO.getConfirmPwd())) {
            try {
                //调用修改密码接口
                securityService.modifyPassword(updateSubAccountVO.getAccountId(), updateSubAccountVO.getPassword(), updateSubAccountVO.getConfirmPwd());
            } catch (Exception e) {
                logger.error("修改子账户密码失败", updateSubAccountVO, e.getMessage());
                throw new SubAccountException(ResultStatusConstant.UPDATE_SUB_ACCOUNT_ERROR);
            }
        }
        UpdateSubAccountDTO updateSubAccountDTO = new UpdateSubAccountDTO();
        String accountStatus = updateSubAccountVO.getStatus();
        importStatus(updateSubAccountDTO, accountStatus);
        updateSubAccountDTO.setFromType("10");
        updateSubAccountDTO.setLoginUserId(updateSubAccountVO.getParentAccountId());
        updateSubAccountDTO.setLoginUserName(updateSubAccountVO.getParentAccoutName());
        List<String> list = new ArrayList<>();
        list.add(updateSubAccountVO.getAccountId());
        updateSubAccountDTO.setAccountIdList(list);
        try {
            //调用宝信修改状态接口
            apiExecutorBxService.updateSubAccount(updateSubAccountDTO);
        } catch (Exception e) {
            logger.error("修改子账户状态失败", updateSubAccountVO, e.getMessage());
            throw new SubAccountException(ResultStatusConstant.UPDATE_SUB_ACCOUNT_ERROR);
        }
        return "修改子账户信息成功";
    }

    /**
     * 修改权限
     *
     * @param updatePermissionVO
     * @return
     * @throws SubAccountException
     */
    @Override
    public String updatePermission(UpdatePermissionVO updatePermissionVO) throws SubAccountException {
        //入参检验
        Map<String, Object> validation = ValidatorUtils.validation(updatePermissionVO);
        if (ObjectUtil.isNotEmpty(validation)) {
            SubAccountException subAccountException = new SubAccountException(ResultStatusConstant.INPUT_PARAM_ERROR);
            subAccountException.setMessage(JSON.toJSONString(validation));
            throw subAccountException;
        }
        UpdatePermissionDTO updatePermissionDTO = new UpdatePermissionDTO();
        updatePermissionDTO.setFromType("10");
        updatePermissionDTO.setAccountId(updatePermissionVO.getAccountId());
        updatePermissionDTO.setAccountName(updatePermissionVO.getAccountName());
        updatePermissionDTO.setParentId(updatePermissionVO.getParentAccountId());
        updatePermissionDTO.setParentName(updatePermissionVO.getParentAccountName());
        updatePermissionDTO.setRoleId(updatePermissionVO.getRoleId());
        updatePermissionDTO.setRoleName(updatePermissionVO.getRoleName());
        updatePermissionDTO.setLoginUserId(updatePermissionVO.getParentAccountId());
        updatePermissionDTO.setLoginUserName(updatePermissionVO.getParentAccountName());
        Object menuList = updatePermissionVO.getInfPermissionInfoList();
        if (ObjectUtil.isNotEmpty(menuList)) {
            //获取菜单列表
            String menuLists = JSONUtil.toJson(menuList);
            List<MenuIdListDTO> subLineList = JSONUtil.toList(menuLists, MenuIdListDTO.class);
            updatePermissionDTO.setInfPermissionInfoList(subLineList);
        }else{
            //ignore
        }
        String roleId = null;
        try {
            //调用宝信接口
            String updateStr = apiExecutorBxService.updatePermission(updatePermissionDTO);
            if (StringUtil.isBlank(updateStr)) {
                throw new SubAccountException(ResultStatusConstant.UPDATE_PERMISSION_ERROR);
            }
            BxApiResult bxApiResult = JSONUtil.toBean(updateStr, BxApiResult.class);
            if (ObjectUtil.isNotEmpty(bxApiResult)) {
                if (1 == bxApiResult.getStatus()) {
                    //获取返回的pkId作为新生成的roleId
                    roleId = bxApiResult.getPkId();
                } else {
                    throw new SubAccountException(ResultStatusConstant.UPDATE_PERMISSION_ERROR);
                }
            }
        } catch (Exception e) {
            logger.error("修改子账户权限信息失败", updatePermissionVO, e.getMessage());
            throw new SubAccountException(ResultStatusConstant.UPDATE_PERMISSION_ERROR);
        }
        return roleId;
    }

    /**
     * 通过用户的状态给定中台需要的标识符
     *
     * @param updateSubAccountDTO
     * @param accountStatus
     */
    public void importStatus(UpdateSubAccountDTO updateSubAccountDTO, String accountStatus) {
        switch (accountStatus) {
            case "1":
                updateSubAccountDTO.setOperatorType(DISABLE);
                break;
            case "2":
                updateSubAccountDTO.setOperatorType(ENABLED);
                break;
            case "3":
                updateSubAccountDTO.setOperatorType(CANCELLED);
                break;
        }
    }

    /**
     * 根据账户id查询角色和菜单权限列表
     *
     * @param accountId
     * @return
     * @throws Exception
     */
    @Override
    public SubAccountVO findRoleAndMenuListByAccountId(String accountId) throws Exception {
        //入参校验
        if (StringUtil.isBlank(accountId)) {
            throw new SubAccountException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        //设置返回账户管理信息
        SubAccountVO subAccountVO = null;
        SubAccountDTO subAccountDTO = null;
        String accountType = null;
        try {
            subAccountDTO = subAccountDAO.getAccount(accountId);
            if (ObjectUtil.isNotEmpty(subAccountDTO)) {
                //获取当前账户下角色及菜单列表
                List<RoleDTO> roleList = subAccountDTO.getRoleList();
                for (RoleDTO roleDTO : roleList) {
                    //获取当前账户下菜单列表
                    List<MenuDTO> menuList = roleDTO.getMenuList();
                    //如果当前账户列表为空，则设置主账号的模板列表并且勾选全部未选中
                    if (ObjectUtil.isEmpty(menuList)) {
                        //获取主账号id
                        String parentAccountId = subAccountDTO.getParentAccountId();
                        //获取主账号信息
                        SubAccountDTO account = subAccountDAO.getAccount(parentAccountId);
                        if (ObjectUtil.isNotEmpty(account)) {
                            //主账号类型
                            accountType = account.getAccountType();
                        }
                        //查询模板角色信息
                        List<RoleDTO> templateRoleAndMenuList = subAccountDAO.getRoleListByAccountId(null, accountType, 1);
                        if (ObjectUtil.isNotEmpty(templateRoleAndMenuList)) {
                            for (RoleDTO templateRoleDTO : templateRoleAndMenuList) {
                                //获取当前角色对应的模板一级菜单列表
                                List<MenuDTO> templateMenuList = templateRoleDTO.getMenuList();
                                if (ObjectUtil.isNotEmpty(templateMenuList)) {
                                    for (MenuDTO menuDTO : templateMenuList) {
                                        //一级菜单设置勾选为 0
                                        menuDTO.setSelected("0");
                                        //获取二级模板菜单列表
                                        List<MenuDTO> secondMenuList = menuDTO.getMenuList();
                                        if (ObjectUtil.isNotEmpty(secondMenuList)) {
                                            for (MenuDTO secondMenuDTO : secondMenuList) {
                                                //二级菜单设置勾选为 0
                                                secondMenuDTO.setSelected("0");
                                                List<MenuDTO> thirdMenuDTOList = secondMenuDTO.getMenuList();
                                                if (ObjectUtil.isNotEmpty(thirdMenuDTOList)) {
                                                    for (MenuDTO thirdMenuDTO : thirdMenuDTOList) {
                                                        //三级菜单设置勾选为 0
                                                        thirdMenuDTO.setSelected("0");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                //设置模板列表并且勾选未选中
                                roleDTO.setMenuList(templateMenuList);
                            }
                        }
                    }
                }
                subAccountDTO.setRoleList(roleList);
            }
        } catch (Exception e) {
            logger.error("根据账户类型和账户id获取角色菜单异常！", e.getMessage(), e);
            throw new SubAccountException(ResultStatusConstant.FIND_ROLE_AND_MENU_LIST_BY_ID_ERROR);
        }
        try {
            //将获取的子账号DTO对象转换为VO对象传给前台
            subAccountVO = ObjectUtil.transfer(subAccountDTO, SubAccountVO.class);
        } catch (SystemException e) {
            logger.error("根据账户id查看角色菜单列表DTO转换VO数据异常", e.getMessage(), e);
            throw new SubAccountException(ResultStatusConstant.SERVER_ERROR);
        }
        return subAccountVO;
    }

    /**
     * 查询角色和菜单权限列表
     *
     * @param accountType
     * @param roleType
     * @return
     * @throws Exception
     */
    @Override
    public List<RoleVO> findAllRoleAndMenuList(String accountType, Integer roleType) throws Exception {
        //入参校验  1-模板角色，2-自定义角色
        if (StringUtil.isBlank(accountType) || ObjectUtil.isEmpty(roleType)) {
            throw new SubAccountException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        //设置返回的模板列表
        List<RoleVO> roleVOList = null;
        List<RoleDTO> roleDTOList = null;
        try {
            //根据账户类型和角色类型获取角色、菜单功能列表
            roleDTOList = subAccountDAO.getRoleListByAccountId(null, accountType, roleType);
        } catch (Exception e) {
            logger.error("获取账户模板角色列表异常！", e.getMessage(), e);
            throw new SubAccountException(ResultStatusConstant.FIND_ALL_ROLE_AND_MENU_LIST_ERROR);
        }
        try {
            roleVOList = ObjectUtil.transfer(roleDTOList, RoleVO.class);
        } catch (SystemException e) {
            logger.error("模板列表DTO转换VO数据异常", e.getMessage(), e);
            throw new SubAccountException(ResultStatusConstant.SERVER_ERROR);
        }
        return roleVOList;
    }

    /**
     * 添加子账户并绑定角色
     *
     * @param subAccVO
     * @return
     * @throws Exception
     */
    @Override
    public String saveSubAccount(SubAccVO subAccVO) throws Exception {
        //入参校验
        Map<String, Object> validation = ValidatorUtils.validation(subAccVO);
        if (ObjectUtil.isNotEmpty(validation)) {
            SubAccountException subAccountException = new SubAccountException(ResultStatusConstant.INPUT_PARAM_ERROR);
            subAccountException.setMessage(JSON.toJSONString(validation));
            throw subAccountException;
        }
        //子账户名称
        String accountName = subAccVO.getAccountName();
        if (accountName.length() <= 1 || accountName.length() >= 60) {
            logger.error("输入长度有误，请重新输入!");
            throw new SubAccountException(ResultStatusConstant.INPUT_LENGTH_ERROR);
        }
        //手机号
        String phoneNumber = subAccVO.getAccountMobile();
        //验证码
        String verifyCode = subAccVO.getVerifyCode();
        if (!RegexUtil.isChinaMobilePhone(phoneNumber)) {
            //手机号格式异常
            logger.error("手机号格式异常！");
            throw new SubAccountException(ResultStatusConstant.ACCOUNT_PHONE_FORMAT_ERROR);
        }
        //添加子账户时查询手机是否已存在一级以及状态使用情况
        List<SubAccountDTO> subAccountDTOList = subAccountDAO.getSubAccount(phoneNumber);
        for (SubAccountDTO subAccountDTO : subAccountDTOList) {
            String accountStatus = subAccountDTO.getAccountStatus();
            if (!"3".equals(accountStatus)) {
                //手机号已经停用或者正在使用中
                throw new SubAccountException(ResultStatusConstant.PHONE_NUMBER_IS_ALREADY_EXISTS);
            }
        }
        //验证短信验证码是否正确
        verifyCodeService.checkVerifyCode(phoneNumber, verifyCode, OperationType.OPE_CREATESUBACCOUNT);
        //设置传给宝信的子账号DTO数据
        SaveSubAccountDTO saveSubAccountDTO = new SaveSubAccountDTO();
        saveSubAccountDTO = ObjectUtil.transfer(subAccVO, SaveSubAccountDTO.class);
        //设置来源类型  商城
        saveSubAccountDTO.setFromType("10");
        //设置登录账号id（父账号id）
        saveSubAccountDTO.setLoginUserId(subAccVO.getParentAccountId());
        //设置登录账号名称（父账号名称）
        saveSubAccountDTO.setLoginUserName(subAccVO.getParentAccountName());
        Boolean flag = false;
        BxApiResult bxApiResult = null;
        try {
            //调用宝信保存子账户接口
            String saveStr = apiExecutorBxService.saveSubAccount(saveSubAccountDTO);
            if (StringUtil.isNotBlank(saveStr)) {
                bxApiResult = JSONUtil.toBean(saveStr, BxApiResult.class);
                flag = getaBoolean(flag, bxApiResult);
            }
        } catch (Exception e) {
            //调用失败
            throw new SubAccountException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
        }
        //设置传入宝信账号绑定角色DTO
        BindRoleDTO bindRoleDTO = new BindRoleDTO();
        bindRoleDTO.setFromType("10");

        bindRoleDTO.setRoleId(subAccVO.getRoleId());
        bindRoleDTO.setLoginUserId(subAccVO.getParentAccountId());
        Boolean roleFlag = false;
        if (flag) {
            //获取当前子账户id
            String pkId = bxApiResult.getPkId();
            bindRoleDTO.setAccountId(pkId);
            try {
                //调用宝信账号绑定角色api
                String str = apiExecutorBxService.bindRole(bindRoleDTO);
                if (StringUtil.isNotBlank(str)) {
                    BxApiResult apiResult = JSONUtil.toBean(str, BxApiResult.class);
                    roleFlag = getaBoolean(roleFlag, apiResult);
                }
            } catch (Exception e) {
                //调用失败
                throw new SubAccountException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
            }
        }
        if (flag && roleFlag) {
            return "success";
        }
        return "failure";
    }

    /**
     * 公共方法：判定调宝信接口是否成功
     *
     * @param roleFlag
     * @param apiResult
     * @return
     * @throws SubAccountException
     */
    private Boolean getaBoolean(Boolean roleFlag, BxApiResult apiResult) throws SubAccountException {
        if (ObjectUtil.isNotEmpty(apiResult)) {
            //获取返回状态
            Integer apiStatus = apiResult.getStatus();
            if (1 == apiStatus) {
                roleFlag = true;
            } else {
                //调用失败
                throw new SubAccountException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
            }
        }
        return roleFlag;
    }

}
