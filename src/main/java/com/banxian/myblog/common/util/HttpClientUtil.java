package com.banxian.myblog.common.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Description: httpClient工具类
 *
 * @author wangpeng
 */
public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    // 编码格式。发送编码格式统一用UTF-8
    private static final String ENCODING = "UTF-8";

    // 设置连接超时时间，单位毫秒。
    private static final int CONNECT_TIMEOUT = 15000;

    // 请求获取数据的超时时间(即响应时间)，单位毫秒。
    private static final int SOCKET_TIMEOUT = 15000;

    /**
     * 发送get请求；不带请求头和请求参数
     *
     * @param url 请求地址
     */
    public static String doGet(String url) {
        return doGet(url, null, null);
    }

    /**
     * 发送get请求；带请求参数
     *
     * @param url    请求地址
     * @param params 请求参数集合
     */
    public static String doGet(String url, Map<String, String> params) {
        return doGet(url, null, params);
    }

    /**
     * 发送get请求；带请求头和请求参数
     *
     * @param url     请求地址
     * @param headers 请求头集合
     * @param params  请求参数集合
     */
    public static String doGet(String url, Map<String, String> headers, Map<String, String> params) {
        logger.info("HttpClient远程调用{}，参数为{},请求头为{}", url, params, headers);
        // 创建httpClient对象
        CloseableHttpClient httpClient = null;
        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;
        String result = "";
        try {
            httpClient = HttpClients.createDefault();
            // 创建访问的地址
            URIBuilder uriBuilder = new URIBuilder(url);
            if (params != null) {
                params.forEach(uriBuilder::setParameter);
            }
            // 创建http对象
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            /**
             * setConnectTimeout：设置连接超时时间，单位毫秒。
             * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
             * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
             * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
             */
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(CONNECT_TIMEOUT)
                    .setSocketTimeout(SOCKET_TIMEOUT)
                    .build();
            httpGet.setConfig(requestConfig);
            // 设置请求头
            packageHeader(headers, httpGet);
            // 执行请求并获得响应结果
            result = getResult(httpResponse, httpClient, httpGet);
            logger.info("HttpClient远程调用{}，返回结果{}", url, result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("HttpClient远程调用{}，出现异常{}", url, e);
        } finally {
            // 释放资源
            release(httpResponse, httpClient);
        }
        return result;
    }

    /**
     * 发送post请求；不带请求头和请求参数
     */
    public static String doPost(String url) {
        return doPost(url, null, null);
    }

    /**
     * 发送post请求；带请求参数
     */
    public static String doPost(String url, Map<String, String> params) {
        return doPost(url, null, params);
    }

    /**
     * 发送post请求；带请求头和请求参数
     */
    public static String doPost(String url, Map<String, String> headers, Map<String, String> params) {
        logger.info("HttpClient远程调用{}，参数为{},请求头为{}", url, params, headers);
        // 创建httpClient对象
        CloseableHttpClient httpClient = null;
        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;
        String result = "";
        try {
            // 创建httpClient对象
            httpClient = HttpClients.createDefault();
            // 创建http对象
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(CONNECT_TIMEOUT)
                    .setSocketTimeout(SOCKET_TIMEOUT)
                    .build();
            httpPost.setConfig(requestConfig);
            // 设置请求头
            packageHeader(headers, httpPost);
            // 封装请求参数
            packageParam(params, httpPost);
            // 执行请求并获得响应结果
            result = getResult(httpResponse, httpClient, httpPost);
            logger.info("HttpClient远程调用{}，返回结果{}", url, result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("HttpClient远程调用{}，出现异常{}", url, e);
        } finally {
            // 释放资源
            release(httpResponse, httpClient);
        }
        return result;
    }

    public static String doJsonPost(String url, String jsonParam) {
        return doJsonPost(url, null, jsonParam);
    }

    public static String doJsonPost(String url, Map<String, String> headers, String jsonParam) {
        logger.info("HttpClient远程调用{}，参数为{},请求头为{}", url, jsonParam, headers);
        // 创建httpClient对象
        CloseableHttpClient httpClient = null;
        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;
        String result = "";
        try {
            // 创建httpClient对象
            httpClient = HttpClients.createDefault();
            // 创建http对象
            HttpPost httpPost = new HttpPost(url);
            // 设置json数据头
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(CONNECT_TIMEOUT)
                    .setSocketTimeout(SOCKET_TIMEOUT)
                    .build();
            httpPost.setConfig(requestConfig);
            // 设置请求头
            packageHeader(headers, httpPost);
            // 封装请求参数
            httpPost.setEntity(new StringEntity(jsonParam, ContentType.APPLICATION_JSON));
            // 执行请求并获得响应结果
            result = getResult(httpResponse, httpClient, httpPost);
            logger.info("HttpClient远程调用{}，返回结果{}", url, result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("HttpClient远程调用{}，出现异常{}", url, e);
        } finally {
            // 释放资源
            release(httpResponse, httpClient);
        }
        return result;
    }

    /**
     * Description: 封装请求头
     *
     * @param headers    请求头参数
     * @param httpMethod 请求方法
     */
    private static void packageHeader(Map<String, String> headers, HttpRequestBase httpMethod) {
        // 封装请求头
        if (headers != null) {
            headers.forEach((k, v) -> httpMethod.setHeader(k, v));
        }
    }

    /**
     * 封装请求体中请求参数
     */
    private static void packageParam(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod)
            throws UnsupportedEncodingException {
        if (params == null) return;
        List<NameValuePair> nvps = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();
            nvps.add(new BasicNameValuePair(k, v));
        }
        // 设置到请求的http对象中
        httpMethod.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));
    }

    /**
     * Description: 获得响应结果
     */
    private static String getResult(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient, HttpRequestBase httpMethod) throws Exception {
        // 执行请求
        httpResponse = httpClient.execute(httpMethod);
        String content = "";
        // 获取返回结果
        if (httpResponse != null && httpResponse.getStatusLine() != null) {
            if (httpResponse.getEntity() != null) {
                content = EntityUtils.toString(httpResponse.getEntity(), ENCODING);
            }
        }
        return content;
    }

    /**
     * Description: 释放资源
     */
    private static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) {
        // 释放资源
        try {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

