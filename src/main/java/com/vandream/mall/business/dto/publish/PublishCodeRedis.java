package com.vandream.mall.business.dto.publish;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dingjie
 * @date 2018/10/10
 * @time 11:29
 * Description:
 */
@Data
@Getter
@Setter
public class PublishCodeRedis {
     private  String id;
     private String  desc;
     private  String url;
     private String imageUrl;
     private String sort;
     private String publishStatus;
}
