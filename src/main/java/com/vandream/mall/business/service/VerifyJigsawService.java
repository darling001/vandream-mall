package com.vandream.mall.business.service;

import com.vandream.mall.business.vo.jigsaw.JigsawVO;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Li Jie
 */

public interface VerifyJigsawService {
    JigsawVO getJigsaw(String phoneNumber) throws InvocationTargetException;

    boolean verifyJigsaw(String phoneNumber, Integer pointX) throws InvocationTargetException;

}
