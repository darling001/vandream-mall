package com.vandream.mall.business.dto.snapshot;

import com.vandream.mall.business.dto.BaseDTO;
import lombok.Data;

/**
 * Created with IntelliJ IDEA
 *
 * @author : liguoqing
 * @date : 2018/7/24
 * Time: 11:22
 * Description:
 */
@Data
public class ItemSnapshotVersionDTO extends BaseDTO {
    private String itemLineId;
    private String itemLineVersion;
}
