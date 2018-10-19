package com.vandream.mall.commons.utils;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLDecoder;

/**
 * @author dingjie
 * @date 2018/3/20
 * @time 16:07
 * Description:
 */
public class HttpRequestUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);


    /**
     * post请求
     * @param url         url地址
     * @param jsonParam     参数
     * @return
     */
    public static String httpPost(String url,String jsonParam) throws Exception{
        /**post请求返回结果**/
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String jsonResult = "";
        HttpPost method = new HttpPost(url);
        if (null != jsonParam&&!"".equals(jsonParam)) {
                /**解决中文乱码问题**/
                StringEntity entity = new StringEntity(jsonParam, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
//            url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                    if(null==result.getEntity()){
                        logger.info("====result.getEntity()为空"+result.toString());
                        return null;
                    }
                    /**读取服务器返回过来的json字符串数据**/
                    jsonResult = EntityUtils.toString(result.getEntity());
            }
        return jsonResult;
    }


    /**
     * 发送get请求
     * @param url    路径
     * @return
     */
    public static String httpGet(String url){
        //get请求返回结果
        String  jsonResult = null;
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                if(null==response.getEntity()){
                    logger.info("====response.getEntity()为空"+response.toString());
                    return null;
                }
                /**读取服务器返回过来的json字符串数据**/
                jsonResult = EntityUtils.toString(response.getEntity());

                url = URLDecoder.decode(url, "UTF-8");
            } else {
                logger.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            logger.error("get请求提交失败:" + url, e);
        }
        return jsonResult;
    }
}
