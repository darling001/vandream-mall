package com.vandream.mall.commons.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dingjie
 * @date 2018/4/13
 * @time 19:53
 * Description:
 */
public class Http4RequestUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);

    /**
     * @param url
     * @param params
     * @param charset
     * @param socketTimeOut
     * @param connectTimeOut
     * @param connReqTimeOut
     * @return String    返回类型
     * @throws IOException
     * @throws
     * @Title: httpPost
     * @Description: httpPost请求工具类
     * @date 2018年4月13日 上午11:55:39
     */
    public static String httpPost(String url, String  params, String charset, int socketTimeOut, int
            connectTimeOut, int connReqTimeOut) throws IOException {
        HttpPost httppost = new HttpPost(url);
        //设置请求和传输超时时间
        /**
         ConnectionRequestTimeout  单位毫秒
         httpclient使用连接池来管理连接，这个时间就是从连接池获取连接的超时时间，可以想象下数据库连接池

         ConnectTimeout  单位毫秒
         连接建立时间，三次握手完成时间

         SocketTimeout  单位毫秒
         数据传输过程中数据包之间间隔的最大时间
         */
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeOut).setConnectTimeout
                (connectTimeOut).setConnectionRequestTimeout(connReqTimeOut).build();
        httppost.setConfig(requestConfig);
        //定义传递参数
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        if (params != null) {
            formparams.add(new BasicNameValuePair("data",params));
        }
        UrlEncodedFormEntity uefEntity;
        String result = null;
        CloseableHttpClient httpclient = HttpClients.custom().build();
        CloseableHttpResponse response = null;
        try {
            /** 解决中文乱码问题 */
            if (null!=params&&!"".equals(params)) {
                StringEntity utfentity = new StringEntity(params, "utf-8");
                utfentity.setContentEncoding("UTF-8");
                utfentity.setContentType("application/json");
                //对参数进行编码
                httppost.setEntity(utfentity);
            }
            response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity, charset);
                }
            } else {
                return result;
            }
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error("{}",e.getMessage(),e);
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("{}",e.getMessage(),e);
            }
        }
        return result;
    }

    /**
     * @param url
     * @param params
     * @param timeOut
     * @param charset
     * @return String    返回类型
     * @throws IOException
     * @throws
     * @Title: httpPost
     * @Description: httpPost请求工具类
     * @date 2018年4月13日 下午4:51:55
     */
    public static String httpPost(String url, String params, String charset, int timeOut) throws
            IOException {
        return httpPost(url, params, charset, timeOut, timeOut, timeOut);
    }

    /**
     * @param url
     * @param params
     * @param charset
     * @param socketTimeOut
     * @param connectTimeOut
     * @param connReqTimeOut
     * @return String    返回类型
     * @throws IOException
     * @throws
     * @Title: httpGet
     * @Description: httpPost请求工具类
     * @date 2018年4月13日 上午11:55:39
     */
    public static String httpGet(String url, String params, String charset, int socketTimeOut, int
            connectTimeOut, int connReqTimeOut) throws IOException {

        logger.info("url : [{}]", url);
        String result = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            //定义传递参数
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            if (null!=params) {
                formparams.add(new BasicNameValuePair("data",params));
            }
            url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(formparams), charset);

            HttpGet httpget = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeOut).setConnectTimeout
                    (connectTimeOut).setConnectionRequestTimeout(connReqTimeOut)
                    .build();//设置请求和传输超时时间
            httpget.setConfig(requestConfig);
            response = httpclient.execute(httpget);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity, charset);
                }
            } else {
                return result;
            }
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error("{}",e.getMessage(),e);
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("{}",e.getMessage(),e);
            }
        }
        return result;
    }

    /**
     * @param url
     * @param params
     * @param charset
     * @param timeOut
     * @return String    返回类型
     * @throws IOException
     * @throws
     * @Title: httpGet
     * @Description: httpGet请求工具类
     * @date 2018年4月13日 上午10:34:41
     */
    public static String httpGet(String url, String params, String charset, int timeOut) throws
            IOException {
        return httpGet(url, params, charset, timeOut, timeOut, timeOut);
    }
}
