package com.vandream.mall.business.vo;

import com.vandream.mall.business.vo.base.BaseVO;
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
public class ComparatorSpecVO extends BaseVO {
    private static final long serialVersionUID = -2557955217579030999L;
    /**
     * 技术参数名称
     */
    private String specName;
    /**
     * 值是否全为相同项
     */
    private Boolean identical;
    /**
     * 技术参数值列表
     */
    private List<Map<String, String>> specMap;

    public ComparatorSpecVO(String specName) {
        this.specName = specName;
        this.identical=true;
    }
}
