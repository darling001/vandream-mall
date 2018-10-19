package com.vandream.mall.business.dto.publish;

import lombok.Data;

import java.util.List;

/**
 * @author dingjie
 * @date 2018/10/10
 * @time 16:39
 * Description:
 */
@Data
public class PublishContentInfo {
    private String type;
    private List<PublishCodeRedis> list;
}
