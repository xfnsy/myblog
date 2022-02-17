package com.banxian.myblog.web.view;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回给前端的视图,json格式
 *
 * @author wangpeng
 * @since 2020-12-31 10:54:25
 */
//@ApiModel("JsonView")
//@Data
public class JsonView {

    // 成功码
    public static final int SUCCESS = 0;

    // 失败码
    public static final int ERROR = 1;

    // token失效
    public static final int TOKEN_INVALID = 2;


    // 返回码
//    @ApiModelProperty("返回码,默认0正常返回,1错误返回")
    private int code;

    // 返回信息
//    @ApiModelProperty("返回说明信息,一般错误信息才会有")
    private String msg;

    // 返回数据
//    @ApiModelProperty("返回数据")
    private Map<String, Object> data = new HashMap<>(3);


    public JsonView() {
        code = SUCCESS;
        msg = "";
    }

    public JsonView(int code, String returnMsg) {
        this.code = code;
        this.msg = returnMsg;
    }

    /**
     * 成功，有data数据
     */
    public static JsonView suc(String key, Object data) {
        JsonView jv = new JsonView();
        jv.setCode(SUCCESS);
        jv.add(key, data);
        return jv;
    }

    public static JsonView suc(String returnMsg) {
        JsonView jv = new JsonView();
        jv.setCode(SUCCESS);
        jv.setMsg(returnMsg);
        return jv;
    }
    public static JsonView sucAdd() {
      return suc("新增成功");
    }
    public static JsonView sucUpdate() {
        return suc("修改成功");
    }
    public static JsonView sucDel() {
        return suc("删除成功");
    }
    public static JsonView suc() {
        JsonView jv = new JsonView();
        jv.setCode(SUCCESS);
        return jv;
    }


    /**
     * 失败
     */
    public static JsonView fail(String returnMsg) {
        JsonView jv = new JsonView();
        jv.setCode(ERROR);
        jv.setMsg(returnMsg);
        return jv;

    }

    /**
     * 失败
     */
    public static JsonView fail(int returnCode, String returnMsg) {
        JsonView jv = new JsonView();
        jv.setCode(returnCode);
        jv.setMsg(returnMsg);
        return jv;
    }

    public void add(String key, Object value) {
        data.put(key, value);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JsonView{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
