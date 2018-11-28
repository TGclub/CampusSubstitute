package com.wizzstudio.substitute.handler;

import com.wizzstudio.substitute.exception.SubstituteException;
import com.wizzstudio.substitute.util.ResultUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常捕获类
 * Created By Cx On 2018/11/6 11:17
 */
@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(SubstituteException.class)
    @ResponseBody
    public ResponseEntity handleSubstituteException(SubstituteException e) {
        return ResultUtil.error(e.getCode(), e.getMessage());
    }
}
