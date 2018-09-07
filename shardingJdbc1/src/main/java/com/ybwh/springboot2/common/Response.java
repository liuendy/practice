package com.ybwh.springboot2.common;


/**
 * 响应base类
 */
public class Response<T> {
    /**
     * 错误码
     */
    private int flag;

    /**
     * 错误描述
     */
    private String error;

    /**
     * 返回数据
     */
    private T resultMessage;


    public Response() {

    }

    public Response(int flag) {
        this.flag = flag;
    }

    public Response(int flag, T resultMessage) {
        this.flag = flag;
        this.resultMessage = resultMessage;
    }

    public Response(int flag, String error) {
        this.flag = flag;
        this.error = error;
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(T resultmessage) {
        this.resultMessage = resultmessage;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}