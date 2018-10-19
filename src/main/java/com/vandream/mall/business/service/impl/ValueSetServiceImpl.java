package com.vandream.mall.business.service.impl;

import com.vandream.mall.business.dao.ValueSetLineDAO;
import com.vandream.mall.business.dto.ValueSetLineDTO;
import com.vandream.mall.business.service.ValueSetService;
import com.vandream.mall.commons.constant.ValueSetConstant;
import com.vandream.mall.commons.utils.ObjectUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author Li Jie
 */
@Service("valueSetService")
public class ValueSetServiceImpl implements ValueSetService {

    @Resource
    private ValueSetLineDAO valueSetLineDAO;

    @Override
    public Map<String, String> getItemUnitTypeNameMap() {
        //取回商品单位值集
        List<ValueSetLineDTO> unitTypeList = valueSetLineDAO.findListByValueSetHeadId
                (ValueSetConstant.TLERP_AUSBS_UNIT_TYPE);
        if (ObjectUtil.isNotEmpty(unitTypeList)) {
            Map<String, String> unitTypeMap = new HashMap<>(unitTypeList.size());
            if (ObjectUtil.isNotEmpty(unitTypeList)) {
                unitTypeMap = new HashMap<>(unitTypeList.size());
                for (ValueSetLineDTO valueSetLineDTO : unitTypeList) {
                    unitTypeMap.put(valueSetLineDTO.getValueCode(), valueSetLineDTO.getValueName());
                }
            }
            return unitTypeMap;
        }
        return new HashMap<>(1);

    }
}
