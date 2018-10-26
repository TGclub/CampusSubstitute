package com.wizzstudio.substitute.dto;

import java.io.Serializable;

public class ResultDTO<T> implements Serializable {

    private static final long serialVersionUID = -276259361015856308L;

    //状态码
    private Integer code;

    //信息
    private String msg;

    //数据
    private T data;

    public ResultDTO() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
