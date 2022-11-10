package com.banxian.myblog.common.util;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * okhttp工具类
 *
 * @author wangpeng
 * @since 2022-8-4 11:46:10
 */
public class OkHttpUtil {

    private static final Logger log = LoggerFactory.getLogger(OkHttpUtil.class);

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType FILE = MediaType.parse("application/octet-stream");

    private static final OkHttpClient okHttpClient;

    static {
        okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public static void main(String[] args) {
//        String url = "http://127.0.0.1:7777/myblog/index";
//        String url = "http://127.0.0.1:7777/myblog/blogType/page";
        String url = "https://sm.ms/api/v2/upload";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "OZLH9LJLN0YaotYD77MTnxMIVEPtLI97");
        headers.put("User-Agent", "OkHttp");
        Map<String, String> params = new HashMap<>();
        params.put("name", "123");
//        System.out.println(get(url, params, headers));
        File file = new File("D:/xiaomao.jpg");
        System.out.println(upload(url, "smfile", file, headers));
    }

    /**
     * Get方法访问
     */
    public static String get(String url, Map<String, String> params, Map<String, String> headers) {
        log.info("GET请求，url:{},params:{},headers:{}", url, params, headers);
        Request request = buildGetRequest(url, params, headers);
        return sync(request);
    }

    public static String get(String url, Map<String, String> params) {
        return get(url, params, null);
    }

    public static String get(String url) {
        return get(url, null, null);
    }


    /**
     * POST方法访问
     */
    public static String post(String url, Map<String, String> params, Map<String, String> headers) {
        log.info("POST请求，url:{},params:{},headers:{}", url, params, headers);
        Request request = buildPostRequest(url, params, headers);
        return sync(request);
    }

    public static String post(String url, Map<String, String> params) {
        return post(url, params, null);
    }

    public static String post(String url) {
        return post(url, null, null);
    }


    public static String upload(String url, String paraName, File file, Map<String, String> headers) {
        log.info("POST-FILE请求，url:{}, file:{},headers:{}", url, file.getName(), headers);
        Request request = buildPostFileRequest(url, paraName, file, headers);
        return sync(request);
    }

    public static String upload(String url, String paraName, File file) {
        return upload(url, paraName, file, null);
    }


    public static String postJson(String url, String jsonBody, Map<String, String> headers) {
        log.info("POST-JSON请求，url:{},jsonBody:{},headers:{}", url, jsonBody, headers);
        Request request = buildPostJsonRequest(url, jsonBody, headers);
        return sync(request);
    }

    public static String postJson(String url, String jsonBody) {
        return postJson(url, jsonBody, null);
    }

    /**
     * GET方式构建Request
     */
    private static Request buildGetRequest(String url, Map<String, String> params, Map<String, String> headers) {
        Request.Builder requestBuilder = new Request.Builder();
        HttpUrl.Builder paramBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        if (params != null) {
            params.forEach(paramBuilder::addQueryParameter);
        }
        if (headers != null) {
            requestBuilder.headers(Headers.of(headers));
        }
        return requestBuilder.url(paramBuilder.build()).build();
    }

    /**
     * POST方式构建Request {Form}
     */
    private static Request buildPostRequest(String url, Map<String, String> params, Map<String, String> headers) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (params != null) {
            params.forEach(formBuilder::add);
        }
        if (headers != null) {
            requestBuilder.headers(Headers.of(headers));
        }
        return requestBuilder.post(formBuilder.build()).build();
    }

    /**
     * POST方式构建Request {json}
     */
    private static Request buildPostJsonRequest(String url, String json, Map<String, String> headers) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        RequestBody body = RequestBody.create(json, JSON);
        if (headers != null) {
            requestBuilder.headers(Headers.of(headers));
        }
        return requestBuilder.post(body).build();
    }

    /**
     * POST方式构建Request {File 文件上传}
     */
    private static Request buildPostFileRequest(String url, String paraName, File file, Map<String, String> headers) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(paraName, file.getName(), RequestBody.create(file, FILE))
                .build();
        if (headers != null) {
            requestBuilder.headers(Headers.of(headers));
        }
        return requestBuilder.post(body).build();
    }

    /**
     * 同步发送请求
     */
    private static String sync(Request request) {
        try {
            Response execute = okHttpClient.newCall(request).execute();
            if (execute.isSuccessful()) {
                ResponseBody body = execute.body();
                if (body != null) {
                    String result = body.string();
                    log.info("Result is: {}", result);
                    return result;
                } else {
                    log.error("Result Null: {}", execute.message());
                }
            } else {
                log.error("Response Fail: code:{},message:{}", execute.code(), execute.message());
            }
        } catch (IOException e) {
            log.error("Response Error:", e);
        }
        return null;
    }

    /**
     * 异步发送请求
     */
    private static void async(Request request) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                log.error("Response Error:", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    System.out.println(response.body());
                }
            }
        });
    }
}
