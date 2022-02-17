package com.banxian.myblog.common.util;

import com.banxian.myblog.domain.SmMsFile;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * sm.ms图片上传工具类
 *
 * @author wangpeng
 * @since 2022-1-24 14:35:11
 */
public class SmMsUtil {

    private static final String BASE_URL = "https://sm.ms/api/v2";
    private static final String SMMS_TOKEN = "OZLH9LJLN0YaotYD77MTnxMIVEPtLI97";

    private static final Map<String, String> HEADERS = new HashMap<>();

    static {
        HEADERS.put("Authorization", SMMS_TOKEN);
        HEADERS.put("User-Agent", "OkHttp");
    }

    /**
     * 上传图片文件
     *
     * @param file 文件
     * @return 上传文件对象
     */
    public static SmMsFile upload(File file) {
        SmMsFile smMsFile = null;
        String rep = OkHttpUtil.upload(BASE_URL + "/upload", "smfile",file, HEADERS);
        JsonNode jsonNode = JacksonUtil.parse(rep, JsonNode.class);
        if ("true".equals(jsonNode.get("success").toString())) {
            String data = jsonNode.get("data").toString();
            smMsFile = JacksonUtil.parse(data, SmMsFile.class);
        }
        return smMsFile;
    }

    public static void main(String[] args) {
        File file = new File("D:/xmf3.jfif");
        System.out.println(upload(file));
    }
}
