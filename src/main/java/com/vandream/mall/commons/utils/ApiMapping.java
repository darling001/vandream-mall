package com.vandream.mall.commons.utils;

import java.util.Map;

/**
 * @author dingjie
 * @date 2018/3/20
 * @time 10:50
 * Description:
 */
public class ApiMapping {

    public Map<String,String> chainedUrlMap;

    public  ApiMapping(){

    }

    public  ApiMapping(Map<String,String> map){
            this.chainedUrlMap = map;
    }

    public Map<String, String> getChainedUrlMap() {
        return chainedUrlMap;
    }

}
