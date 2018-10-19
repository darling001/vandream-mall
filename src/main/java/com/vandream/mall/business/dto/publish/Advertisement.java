package com.vandream.mall.business.dto.publish;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author dingjie
 * @date 2018/10/10
 * @time 9:21
 * Description:
 */
@Data
@Getter
@Setter
public class Advertisement {
    private String advertisementCode;
    private String advertisementName;
    private List<ImageInfo> list;
}
