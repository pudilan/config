package com.sintmo.redis.controller.result;

public class BaseResult<T> {

    private int status;

    private String code;

    private String desc;

    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> BaseResult<T> result(int status, String code, String desc, T data) {
        BaseResult<T> baseResult = new BaseResult<T>();
        baseResult.setStatus(status);
        baseResult.setCode(code);
        baseResult.setDesc(desc);
        baseResult.setData(data);
        return baseResult;
    }

    public static <T> BaseResult<T> success(T data) {
        return result(0, null, "success", data);
    }

    public static <T> BaseResult<T> success(String code, T data) {
        return result(0, code, "success", data);
    }

    public static <T> BaseResult<T> fail(String code, String message) {
        return result(1, code, message, null);
    }

}
