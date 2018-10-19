package com.vandream.mall.business.dto.mallCart;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.vandream.mall.commons.utils.JSONUtil;
import com.vandream.mall.commons.utils.ObjectUtil;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author dingjie
 * @date 2018/3/8
 * @time 14:42
 * Description:
 */
public class SearchEsReturnData {

    private static final Logger logger = LoggerFactory.getLogger(SearchEsReturnData.class);


    /**
     * 通过skuId查询es匹配数据
     * @param skuId
     * @return
     */
    public static Map<String,Object> getCartDetail(TransportClient transportClient,String skuId,String resultJson){
        Map<String,Object> resultMap= new HashMap<String,Object>();
        IndicesExistsResponse inExistsResponse = transportClient.admin().indices().exists(new IndicesExistsRequest("gms")).actionGet();
        if (inExistsResponse.isExists()) {
            logger.info("==============Index [" + skuId + "] is exist!");
            GetRequestBuilder getRequestBuilder = transportClient.prepareGet("gms","item_agg",skuId);
            if (ObjectUtil.isNotEmpty(getRequestBuilder)) {
                GetResponse getResponse = getRequestBuilder.execute().actionGet();
                Map<String,Object> map=getResponse.getSource();
                //根据返回json转map后，查询elasticSearch数据
                Map responseMap= JSONUtil.toMap(resultJson);

                if(null!=responseMap&&responseMap.size()>0&&null!=map&&map.size()>0){
                    resultMap=reBuildMap(responseMap,map);
                }
            }
        } else {
            logger.info("===============Index [" + skuId + "] is not exist!");
        }
        return resultMap;
    }
    /**
     * 通过skuId查询es匹配数据
     * @param itemLineId
     * @return
     */
    public static Map<String,Object> getCartDetailByItemLineId(TransportClient transportClient,String itemLineId,String resultJson){
        Map<String,Object> resultMap= new HashMap<String,Object>();
        IndicesExistsResponse inExistsResponse = transportClient.admin().indices().exists(new IndicesExistsRequest("gms")).actionGet();
        if (inExistsResponse.isExists()) {
            logger.info("==============Index [" + itemLineId + "] is exist!");
            /**查询商品库（从ElasticSearch中查询）**/
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.should(QueryBuilders.termQuery("item_line_id", transportClient));
            SearchResponse response = transportClient.prepareSearch("gms").setTypes("item_agg").setQuery(boolQueryBuilder).get();
            SearchHits hits = response.getHits();
            if(null!=hits){
                SearchHit[] searchHits = hits.getHits();
                /**处理Item相应的商品属性**/
                if(null!=searchHits&&searchHits.length>0){
                    for (SearchHit searchHit : searchHits) {
                        Map<String, Object> map = searchHit.getSource();
                        //根据返回json转map后，查询elasticSearch数据
                        Map responseMap=buildMap(resultJson);
                        if(null!=responseMap&&responseMap.size()>0&&null!=map&&map.size()>0){
                            resultMap=reBuildMap(responseMap,map);
                        }
                    }
                }
            }
        } else {
            logger.info("===============Index [" + itemLineId + "] is not exist!");
        }
        return resultMap;
    }

    /**
     * 将返回到前台的json模版转map,生成响应map
     * @param json
     * @return
     */
    public static  Map buildMap(String json) {
        Map jsonMap =(Map) JSON.parse(json);
        return jsonMap;
    }

    /**
     * 根据前台map，从es中读取值并返回
     * @param reqMap 前台需要查询的map参数
     * @param esSearchMap  es中查询结果map
     * @return
     */
    public static  Map<String,Object> reBuildMap(Map<String,Object> reqMap,Map<String,Object> esSearchMap) {
        Map<String,Object> respMap = new HashMap<String,Object>();
        if (null!=reqMap&&reqMap.size()>0) {
            for (Map.Entry<String,Object> entry:reqMap.entrySet()) {
                String keyName=entry.getKey();
                respMap.put(entry.getKey(),esSearchMap.get(keyName));
            }
        }
        return respMap;
    }


}
