package com.banxian.myblog.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * jackson工具列，包含常见的json操作方法
 *
 * @author wangpeng
 * @since 2021-12-27 10:55:02
 */
public class JacksonUtil {

    private static final ObjectMapper objectMapper;

    static {
//        objectMapper = SpringUtil.getBean(ObjectMapper.class);
        objectMapper = new ObjectMapper();
        // 通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
        // Include.Include.ALWAYS 默认
        // Include.NON_DEFAULT 属性为默认值不序列化
        // Include.NON_EMPTY 属性为 空（""） 或者为 NULL 都不序列化，则返回的json是没有这个字段的。这样对移动端会更省流量
        // Include.NON_NULL 属性为NULL 不序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //关闭Jackson时间戳
        objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        // 忽略空Bean转json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 忽略未知属性，防止json字符串中存在，java对象中不存在对应属性的情况出现错误
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 允许出现单引号
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 日期和时间格式化
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 注册一个时间序列化及反序列化的处理模块，用于解决jdk8中localDateTime等的序列化问题
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));
        objectMapper.registerModule(javaTimeModule);
//        // 字段保留，将null值转为""
//        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>()
//        {
//            @Override
//            public void serialize(Object o, JsonGenerator jsonGenerator,
//                                  SerializerProvider serializerProvider)
//                    throws IOException
//            {
//                jsonGenerator.writeString("");
//            }
//        });
    }


    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * json字符串 => 对象
     *
     * @param json  源json串
     * @param clazz 对象类
     * @param <T>   泛型
     */
    public static <T> T parse(String json, Class<T> clazz) {
        return parse(json, clazz, null);
    }

    /**
     * json字符串 => 对象
     *
     * @param json          源json串
     * @param typeReference 对象类
     * @param <T>           泛型
     */
    public static <T> T parse(String json, TypeReference<T> typeReference) {
        return parse(json, null, typeReference);
    }

    /**
     * json字符串 => 对象列表
     *
     * @param json  源json串
     * @param clazz 对象类型
     */
    public static <T> List<T> parseList(String json, Class<T> clazz) {
        TypeReference<List<T>> typeRef = new TypeReference<List<T>>() {
        };
        List<T> list = null;
        try {
            list = objectMapper.readValue(json, typeRef);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * json => 对象处理方法
     * <br>
     * 参数clazz和type必须一个为null，另一个不为null
     * <br>
     * 此方法不对外暴露，访问权限为private
     *
     * @param json  源json串
     * @param clazz 对象类
     * @param type  对象类型
     * @param <T>   泛型
     */
    private static <T> T parse(String json, Class<T> clazz, TypeReference<T> type) {
        T obj = null;
        if (json != null && !json.isEmpty()) {
            try {
                if (clazz != null) {
                    obj = objectMapper.readValue(json, clazz);
                } else {
                    obj = objectMapper.readValue(json, type);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    public static void main(String[] args) throws JsonProcessingException {
////        System.out.println(toJson(JsonView.suc("哈哈")));
//        List<Demo> list=new ArrayList<>();
//        list.add(new Demo());
//        list.add(new Demo());
//        list.add(new Demo());
//        System.out.println(toJson(new Demo()));
//        System.out.println(toJson(list));
//        String json = "{\"s1\":\"你好啊\",\"s2\":2,\"s3\":true,\"s4\":null,\"s5\":[\"12\",\"456\",null],\"jv\":{\"returnCode\":0,\"returnMsg\":\"\",\"returnData\":{\"name\":\"wui的人啊\"}},\"date\":\"2021-12-27 09:49:25\",\"localDateTime\":\"2021-12-27T09:49:25.494\"}\n";
//        // JsonNode是json对象
//        JsonNode parse = parse(json, JsonNode.class);
//        System.out.println(parse);
//        System.out.println(parse.get("s1"));
//        System.out.println(parse(json, JsonView.class));
//        String jsonList="[{\"s1\":\"你好啊\",\"s2\":2,\"s3\":true,\"s4\":null,\"s5\":[\"12\",\"456\",null],\"jv\":{\"returnCode\":0,\"returnMsg\":\"\",\"returnData\":{\"name\":\"wui的人啊\"}},\"date\":\"2021-12-27 10:51:35\",\"localDateTime\":\"2021-12-27T10:51:35.538\"},{\"s1\":\"你好啊\",\"s2\":2,\"s3\":true,\"s4\":null,\"s5\":[\"12\",\"456\",null],\"jv\":{\"returnCode\":0,\"returnMsg\":\"\",\"returnData\":{\"name\":\"wui的人啊\"}},\"date\":\"2021-12-27 10:51:35\",\"localDateTime\":\"2021-12-27T10:51:35.538\"},{\"s1\":\"你好啊\",\"s2\":2,\"s3\":true,\"s4\":null,\"s5\":[\"12\",\"456\",null],\"jv\":{\"returnCode\":0,\"returnMsg\":\"\",\"returnData\":{\"name\":\"wui的人啊\"}},\"date\":\"2021-12-27 10:51:35\",\"localDateTime\":\"2021-12-27T10:51:35.538\"}]\n";
//        System.out.println(parseList(jsonList,Demo.class));
//        Demo demo = new Demo();
//        demo.setLocalDateTime(LocalDateTime.now());
//        System.out.println(toJson(demo));
        String mes = "{\n" +
                "    \"code\" : \"000\",\n" +
                "    \"message\" : \"成功\",\n" +
                "    \"baseInfo\" : \"[{\"psid\":\"TB12001199\",\"grade_descr\":\"9描述\",\"post_descr\":\"需求与预算管理助理工程师\",\"dept_descr\":\"数据管理和分析中心\",\"status\":\"在职\",\"title_descr\":\"集团主管\",\"empl_no\":\"0663\",\"company_descr\":\"集团公司\"}]\"}";
//        String str =  "[{\"psid\":\"TB12001199\",\"grade_descr\":\"9描述\",\"post_descr\":\"需求与预算管理助理工程师\",\"dept_descr\":\"数据管理和分析中心\",\"status\":\"在职\",\"title_descr\":\"集团主管\",\"empl_no\":\"0663\",\"company_descr\":\"集团公司\"}]";
        JsonNode jsonNode = objectMapper.readValue(mes, JsonNode.class);
        System.out.println(jsonNode);
    }
}

/*class Demo {

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public Integer getS2() {
        return s2;
    }

    public void setS2(Integer s2) {
        this.s2 = s2;
    }

    public Boolean getS3() {
        return s3;
    }

    public void setS3(Boolean s3) {
        this.s3 = s3;
    }

    public String getS4() {
        return s4;
    }

    public void setS4(String s4) {
        this.s4 = s4;
    }

    public String[] getS5() {
        return s5;
    }

    public void setS5(String[] s5) {
        this.s5 = s5;
    }

    public JsonView getJv() {
        return jv;
    }

    public void setJv(JsonView jv) {
        this.jv = jv;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    private String s1 = "你好啊";

    private Integer s2 = 2;

    private Boolean s3 = true;

    private String s4 = null;

    private String[] s5 = {"12", "456", null};

    private JsonView jv = JsonView.suc("name", "wui的人啊");

    private Date date = new Date();

    private LocalDateTime localDateTime = LocalDateTime.now();

    @Override
    public String toString() {
        return "Demo{" +
                "s1='" + s1 + '\'' +
                ", s2=" + s2 +
                ", s3=" + s3 +
                ", s4='" + s4 + '\'' +
                ", s5=" + Arrays.toString(s5) +
                ", jv=" + jv +
                ", date=" + date +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
*/