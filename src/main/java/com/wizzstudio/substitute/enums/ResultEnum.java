package com.wizzstudio.substitute.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应结果集枚举类
 * Created By Cx On 2018/10/28 13:57
 */
@AllArgsConstructor
@Getter
public enum  ResultEnum {
    SUCCESS(0, "成功"),
    USER_NOT_EXISTS(1001,"用户不存在"),
    PARAM_ERROR(10002,"参数有误"),
    INNER_ERROR(-1,"服务器异常")
    ;

    private Integer code;
    private String msg;
}
