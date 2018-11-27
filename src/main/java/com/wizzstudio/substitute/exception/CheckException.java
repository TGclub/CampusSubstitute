package com.wizzstudio.substitute.exception;

import com.wizzstudio.substitute.enums.ResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 验证信息错误类
 * Created By Cx On 2018/11/27 15:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CheckException extends Exception {

    //错误码
    private Integer code;

    public CheckException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public CheckException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public CheckException(String message) {
        super(message);
        this.code = -1;
    }
}
