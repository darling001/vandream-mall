package com.vandream.mall.business.vo.search;

import java.io.Serializable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Li Jie
 */
@Data
@Getter
@Setter
public class SearchCrumbsVO implements Serializable {
    private static final long serialVersionUID = 2801630415749426251L;
    private SearchCategoryAggVO levelOne;
    private SearchCategoryAggVO levelTwo;
    private SearchCategoryAggVO levelThree;

    /**
     * 是否已定位到叶子类目
     * Whether this crumb is located in the leaf category.
     *
     * @return
     */
    public Boolean isLocated() {
        return (this.levelOne != null && this.levelTwo != null && this.levelThree != null);
    }
}
