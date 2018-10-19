package com.vandream.mall.business.vo.search;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Li Jie
 */
@Data
@Getter
@Setter
public class SearchResultVO implements Serializable {
    private static final long serialVersionUID = 4920423538334628363L;

    private SearchCrumbsVO crumbs;
    private List<Map<String, Object>> recommendation;
    private List<SearchCategoryAggVO> category;
    private List<SearchSpecAggVO> parameters;
    private List<SearchItemAggVO> itemList;
    private Integer currentPage;
    private Long totalSize;


    public SearchResultVO(Integer currentPage, Long totalSize) {
        this.currentPage = currentPage;
        this.totalSize = totalSize;
    }
}
