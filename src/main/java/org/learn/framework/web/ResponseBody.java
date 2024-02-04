package org.learn.framework.web;

/**
 * 统一消息返回格式
 */
public class ResponseBody {

    private int code;

    private String message;

    private Object data;

    public static ResponseBody success(){
        return new ResponseBody().setCode(200).setMessage("操作成功");
    }

    public static ResponseBody success(Object data){
        return new ResponseBody().setCode(200).setMessage("success").setData(data);
    }

    public static ResponseBody failure(){
        return new ResponseBody().setCode(500).setMessage("操作失败");
    }

    public static ResponseBody failure(Object data){
        return new ResponseBody().setCode(500).setMessage("failure").setData(data);
    }

    public int getCode() {
        return code;
    }

    public ResponseBody setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseBody setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResponseBody setData(Object data) {
        this.data = data;
        return this;
    }
}
