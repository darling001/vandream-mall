package com.vandream.mall.business.vo.search;

import com.vandream.mall.commons.constant.ComparatorInstance;
import com.vandream.mall.commons.utils.ObjectUtil;
import com.vandream.mall.commons.utils.StringUtil;
import java.io.Serializable;
import java.util.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Li Jie
 */
@Data
@Getter
@Setter
public class SearchSpecAggVO implements Serializable, Comparable<SearchSpecAggVO> {

    private static final long serialVersionUID = -9005794987647390062L;

    private String aggId;
    private String categoryId;
    private String specFieldId;
    private String specName;
    private List<String> specValues;

    public SearchSpecAggVO(String specName, List<String> specValues) {
        this.specName = specName;
        this.specValues = specValues;
    }

    public static List<SearchSpecAggVO> transform(List<SearchItemSpecVO> itemSpecList) {
        Map<String, Set<String>> specAggSetMap = new HashMap<>(itemSpecList.size());
        for (SearchItemSpecVO itemSpecVO : itemSpecList) {
            if (StringUtil.isBlank(itemSpecVO.getName()) || StringUtil.isBlank(itemSpecVO
                    .getValue())) {
                continue;
            }
            Set<String> valueSet = specAggSetMap.get(itemSpecVO.getName());
            if (ObjectUtil.isEmpty(valueSet)) {
                valueSet = new HashSet<>();
            }
            valueSet.add(itemSpecVO.getValue());
            specAggSetMap.put(itemSpecVO.getName(), valueSet);
        }
        List<SearchSpecAggVO> resultList = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : specAggSetMap.entrySet()) {
            String specName = entry.getKey();
            Set<String> valueSet = entry.getValue();
            resultList.add(new SearchSpecAggVO(specName, new ArrayList<>(valueSet)));
        }
        return resultList;
    }

    @Override
    public String toString() {
        return "SpecFieldAggVO{" +
                "aggId='" + aggId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", specFieldId='" + specFieldId + '\'' +
                ", specName='" + specName + '\'' +
                ", specValues='" + specValues + '\'' +
                '}';
    }

    @Override
    public int compareTo(SearchSpecAggVO o) {
        return ComparatorInstance.INITIALS_COMPARATOR_CHINESE.compare(this.specName, o.specName);
    }
}
