package com.wizzstudio.substitute.dto;

import lombok.Data;

import java.io.Serializable;


@Data
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

    public ResultDTO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
