package com.vandream.mall.business.vo;

import com.vandream.mall.business.vo.base.BaseVO;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Li Jie
 */
@Data
@Getter
@Setter
public class ComparatorVO extends BaseVO{
    private static final long serialVersionUID = 2164762995044483732L;
    List<ComparatorItemVO> itemList;
    List<ComparatorSpecVO> specList;
}
