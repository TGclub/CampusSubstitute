package com.wizzstudio.substitute.exception;

import com.wizzstudio.substitute.enums.ResultEnum;

/**
 * Created By Cx On 2018/10/28 13:57
 */
public class SubstituteException extends RuntimeException {

    //错误码
    private Integer code;

    public SubstituteException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public SubstituteException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}
