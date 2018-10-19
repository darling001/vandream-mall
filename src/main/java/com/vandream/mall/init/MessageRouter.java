package com.vandream.mall.init;

import com.alibaba.fastjson.JSONObject;
import com.vandream.mall.business.execption.BusinessException;
import com.vandream.mall.business.execption.LoginTimeExpireException;
import com.vandream.mall.business.execption.SystemException;
import com.vandream.mall.business.execption.TokenNullException;
import com.vandream.mall.business.vo.DataVO;
import com.vandream.mall.business.vo.LoginVO;
import com.vandream.mall.business.vo.ResponseDataVO;
import com.vandream.mall.business.vo.base.BaseVO;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 临时类，先将HTTP请求转调用
 *
 * @Author Oakley(fangchao)
 * @Date 2018-01-17 14:16
 */
@RestController
public class MessageRouter {


    private static  String ACTION_NAME="login";
    private static long EXPIRE_TIME = 30;
    private static ArrayList WHITE_LIST = new ArrayList(5);
    private static String servicePackageImpl = "com.vandream.mall.business.service.impl";
    /**
     * 缓存服务名与类名的对应关系，回头放到配置文件中，并在初始化过程中加载
     * <p>
     * 今后的初始化加载过程中，配置文件存储服务名称与真实类名的对应关系；
     * 初始化时加载相应的对象放到相应的Hash中。
     */
    private static Map<String, String> serviceToBeanName = new HashMap<>();

    private static Map<String, Method> serviceActionMethod = new HashMap<>();


    /**
     * VO对象基类
     **/
    private static Class<BaseVO> baseVOClass = BaseVO.class;

    static {
        WHITE_LIST.add("verifyPhone");
        WHITE_LIST.add("login");
        WHITE_LIST.add("logout");
        WHITE_LIST.add("getJigsaw");
        WHITE_LIST.add("verifyJigsaw");
        WHITE_LIST.add("register");
        WHITE_LIST.add("queryCategoryList");
        WHITE_LIST.add("getArea");
        WHITE_LIST.add("getItemBaseInfo");
        WHITE_LIST.add("getSkuAttributeList");
        WHITE_LIST.add("getItemInfoDetail");
        WHITE_LIST.add("search");
        WHITE_LIST.add("verifyPhone");
        WHITE_LIST.add("sendSmsCode");
        WHITE_LIST.add("getTagInfoList");
        WHITE_LIST.add("findBrandList");
        WHITE_LIST.add("getBrandDetailById");
        WHITE_LIST.add("findHelpAndSolutionList");
        WHITE_LIST.add("getInformationDetail");
        WHITE_LIST.add("findListItemInfo");
        WHITE_LIST.add("findSpuItemList");
        WHITE_LIST.add("checkVerifyCode");
        WHITE_LIST.add("verifyPhoneExist");
        WHITE_LIST.add("modifyPassword");
        WHITE_LIST.add("record");
        WHITE_LIST.add("previewAdvContent");

    }

    static {
        serviceToBeanName.put("verifyJigsawService", "verifyJigsawService");
        serviceToBeanName.put("accountService", "accountService");
        serviceToBeanName.put("userService", "userService");
        serviceToBeanName.put("pageConfigService", "pageConfigService");
        serviceToBeanName.put("pagePositionService", "pagePositionService");
        serviceToBeanName.put("themeService", "themeService");
        serviceToBeanName.put("advertisementService", "advertisementService");
        serviceToBeanName.put("categoryService", "categoryService");
        serviceToBeanName.put("sectionService", "sectionService");
        serviceToBeanName.put("companyService", "companyService");
        serviceToBeanName.put("logService", "logService");
        serviceToBeanName.put("orderService", "orderService");
        serviceToBeanName.put("mallCartService", "mallCartService");
        serviceToBeanName.put("searchService", "searchService");
        serviceToBeanName.put("securityService", "securityService");
        serviceToBeanName.put("verifyCodeService", "verifyCodeService");
        serviceToBeanName.put("smsMsgService", "smsMsgService");
        serviceToBeanName.put("areaService", "areaService");
        serviceToBeanName.put("addressService", "addressService");
        serviceToBeanName.put("itemDetailService", "itemDetailService");
        serviceToBeanName.put("favoritesService", "favoritesService");
        serviceToBeanName.put("solutionService", "solutionService");
        serviceToBeanName.put("confirmationContractService", "confirmationContractService");
        serviceToBeanName.put("findContractService", "findContractService");
        serviceToBeanName.put("findDeliverService", "findDeliverService");
        serviceToBeanName.put("findLogisticsService", "findLogisticsService");
        serviceToBeanName.put("demandService", "demandService");
        serviceToBeanName.put("salesReservationService", "salesReservationService");
        serviceToBeanName.put("tagInfoService", "tagInfoService");
        serviceToBeanName.put("favoritesListByUserIdService", "favoritesListByUserIdService");
        serviceToBeanName.put("purchaseOrderService", "purchaseOrderService");
        serviceToBeanName.put("deliveryService", "deliveryService");
        serviceToBeanName.put("brandService", "brandService");
        serviceToBeanName.put("helpAndSolutionService", "helpAndSolutionService");
        serviceToBeanName.put("comparatorService", "comparatorService");
        serviceToBeanName.put("subAccountService", "subAccountService");
        serviceToBeanName.put("buryingPointService", "buryingPointService");
        serviceToBeanName.put("snapshotService", "snapshotService");
        serviceToBeanName.put("publishService", "publishService");

    }


    @Autowired
    RedisTemplate redisTemplate;
    private Logger logger = LoggerFactory.getLogger(MessageRouter.class);

    /**
     * 入口参数
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/api/{serviceName}/{actionName}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDataVO msgRouter(@PathVariable String serviceName, @PathVariable String
            actionName, @RequestBody(required = false) DataVO data) {

        String serverPath = "/api/" + serviceName + "/" + actionName;

        ResponseDataVO result = new ResponseDataVO();
        String beanName = serviceToBeanName.get(serviceName);
        Object server = SpringUtil.getBean(beanName);
        /*** 找不到服务 ***/
        if (server == null) {
            result.setStatus(ResultStatusConstant.SERVICE_NOT_FOUND.getStatus());
            result.setCode(ResultStatusConstant.METHOD_NOT_FOUND.getCode());
            result.setData(ResultStatusConstant.METHOD_NOT_FOUND.getDesc() + ": " + serviceName);
            logger.error("err service:" + serviceName + " not find.........");
            return result;
        }
        Method beanMethod = this.getMethod(server, actionName);


        /*** 找不到Action ***/
        if (beanMethod == null) {
            result.setStatus(ResultStatusConstant.METHOD_NOT_FOUND.getStatus());
            result.setCode(ResultStatusConstant.METHOD_NOT_FOUND.getCode());
            result.setData(ResultStatusConstant.METHOD_NOT_FOUND.getDesc());
            logger.error("err action:" + actionName + " not find.........");
            return result;
        }

        try {
            String methodCacheKey = serviceName + "." + actionName;
            Method classMethod = serviceActionMethod.get(methodCacheKey);
            if (classMethod == null) {
                String serviceClassName = this.getServiceClassName(serviceName);
                Class<?> serviceClazz = Class.forName(serviceClassName);
                classMethod = serviceClazz.getMethod(actionName, beanMethod.getParameterTypes());
                serviceActionMethod.put(methodCacheKey, classMethod);
            }
            JSONObject paramData = parseData(serverPath, data);
            loginCheck(actionName, data);
            Object[] args = this.getArgs(classMethod, paramData);
            Long startTime = System.currentTimeMillis();
            Object o = beanMethod.invoke(server, args);
            if (ACTION_NAME.equals(actionName)) {
                LoginVO loginVO = (LoginVO) o;
                result.setToken(loginVO.getToken());
            }
            result.setStatus(ResultStatusConstant.SUCCESS.getStatus());
            result.setCode(ResultStatusConstant.SUCCESS.getCode());
            result.setData(o);
            logger.info("接口名称,{}\t当前消耗时间{}",serverPath,(System.currentTimeMillis()-startTime));
            return result;
        } catch (Exception e) {
            logger.debug("Fail:\t{},{}", e.getMessage(), e);
            String status = ResultStatusConstant.SERVER_ERROR.getStatus();
            int code = ResultStatusConstant.SERVER_ERROR.getCode();
            String exceptionData = ResultStatusConstant.SERVER_ERROR.getDesc();
            if (e instanceof InvocationTargetException) {
                Throwable targetException = ((InvocationTargetException) e).getTargetException();
                if(ObjectUtil.isEmpty(targetException)){
                    targetException=e;
                }
                if (targetException != null) {
                    if (targetException instanceof SystemException) {
                        SystemException systemException = (SystemException) targetException;
                        status = systemException.getStatus();
                        code = systemException.getCode();
                        exceptionData = systemException.getMessage();
                    } else if (targetException instanceof BusinessException) {
                        BusinessException businessException = (BusinessException) targetException;
                        status = businessException.getStatus();
                        code = businessException.getCode();
                        exceptionData = businessException.getMessage();
                    }
                }
            }
            result.setStatus(status);
            result.setCode(code);
            result.setData(exceptionData);
            return result;
        }
    }

    /**
     * 参数解析
     *
     * @param data
     * @return
     */
    private JSONObject parseData(final String serverPath, DataVO data) {
        String tk = null;
        String paramJsonStr = null;
        JSONObject paramData = new JSONObject();
        if (data != null) {
            if (StringUtil.isNotBlank(data.getTk())) {
                tk = data.getTk();
                paramJsonStr = AES.decrypt(data.getData(), tk);
            } else {
                paramJsonStr = data.getData();
            }
            paramData = JSONObject.parseObject(paramJsonStr);
            logger.info("方法名称:{} \t 请求参数:{}", serverPath, paramJsonStr);
        }
        return paramData;
    }


    /**
     * 这里的类型先写死测试用
     *
     * @param testService
     * @return
     */
    private Method getMethod(Object testService, String methodName) {
        Method[] declaredMethods = testService.getClass().getDeclaredMethods();
        /*** 无法确定参数类型，先进行遍历匹配 ***/
        for (Method method : declaredMethods) {
            if (methodName.equals(method.getName())) {
                return method;
            }
        }
        return null;
    }

    private Object[] getArgs(Method method, JSONObject data) throws IllegalAccessException, InstantiationException {
        Object[] args = new Object[method.getParameterCount()];
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter p = parameters[i];
            if (MessageRouter.baseVOClass.equals(p.getType().getSuperclass())) {
                // 通过参数类型反射创建实例
                if (ObjectUtil.isEmpty(data)) {
                    args[i] = p.getType().newInstance();
                    continue;
                }
                args[i] = JSONUtil.toPOJO(data.toJSONString(), p.getType());
            } else {
                args[i] = data.getObject(p.getName(), p.getType());
            }
        }
        return args;
    }

    /**
     * 登录验证
     *
     * @param actionName
     * @param dataVO
     * @throws Exception
     */
    private void loginCheck(String actionName, DataVO dataVO) throws BusinessException {

        //判断方请求是否在白名单类
        boolean flag = WHITE_LIST.contains(actionName);
        if (flag) {
            return;
        }
        if (dataVO == null) {
            return;
        }
        if (dataVO != null) {
            if (StringUtil.isBlank(dataVO.getToken())) {
                throw new TokenNullException(ResultStatusConstant.TOKEN_NULL);
            }
        }
        if (StringUtil.isNotBlank(dataVO.getToken())) {
            HashMap<String, String> tokenMap = (HashMap<String, String>) redisTemplate
                    .opsForValue().get(dataVO.getToken());
            if (ObjectUtil.isEmpty(tokenMap)) {
                throw new LoginTimeExpireException(ResultStatusConstant.LOGIN_TIME_EXPIRE);
            }
            String userId = tokenMap.get("userId");
            String username = tokenMap.get("username");
            updateUserToken(dataVO.getToken(), userId, username);
        }
    }

    /**
     * 更新用户token
     *
     * @param tokenKey
     * @param userId
     * @param username
     */
    private void updateUserToken(String tokenKey, String userId, String username) {
        HashMap<String, String> tokenMap = new HashMap<>(3);
        tokenMap.put("userId", userId);
        tokenMap.put("username", username);
        long lastAccessTime = System.currentTimeMillis();
        tokenMap.put("lastLoginTime", String.valueOf(lastAccessTime));

        redisTemplate.opsForValue().set(tokenKey, tokenMap, EXPIRE_TIME, TimeUnit.MINUTES);

    }

    private String getServiceClassName(String serviceName) {
        StringBuilder className = new StringBuilder();
        className.append(servicePackageImpl);
        className.append(".");
        className.append(StringUtil.capitalize(serviceName));
        className.append("Impl");
        return className.toString();
    }

}

