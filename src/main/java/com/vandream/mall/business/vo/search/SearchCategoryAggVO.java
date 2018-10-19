package com.vandream.mall.business.vo.search;

import com.alibaba.fastjson.annotation.JSONField;
import com.vandream.mall.commons.constant.ComparatorInstance;
import java.io.Serializable;
import java.util.Objects;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Li Jie
 */
@Data
@Getter
@Setter
public class SearchCategoryAggVO implements Serializable, Comparable<SearchCategoryAggVO> {
    private static final long serialVersionUID = 1434693533016697462L;
    @JSONField(name = "CATEGORY_ID")
    private String categoryId;
    @JSONField(name = "CATEGORY_NAME")
    private String categoryName;
    @JSONField(name = "CATEGORY_LEVEL")
    private String categoryLevel;

    public SearchCategoryAggVO() {

    }

    public SearchCategoryAggVO(String categoryId, String categoryName, String categoryLevel) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryLevel = categoryLevel;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SearchCategoryAggVO that = (SearchCategoryAggVO) o;
        return Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId);
    }


    @Override
    public int compareTo(SearchCategoryAggVO o) {
        return ComparatorInstance.INITIALS_COMPARATOR_CHINESE.compare(this.categoryName, o.categoryName);
    }
}
