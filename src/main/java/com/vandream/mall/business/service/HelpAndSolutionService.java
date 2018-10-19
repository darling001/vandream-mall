package com.vandream.mall.business.service;

import com.vandream.mall.business.vo.base.DataListVO;
import com.vandream.mall.business.vo.homepage.HelpAndSolutionDetailVO;

import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/4/12
 * @time : 11:18
 * Description:
 */
public interface HelpAndSolutionService {

    DataListVO findHelpAndSolutionList(Integer pageSize, Integer pageNo, Integer type) throws InvocationTargetException;

    HelpAndSolutionDetailVO getInformationDetail(Integer id) throws InvocationTargetException;
}
