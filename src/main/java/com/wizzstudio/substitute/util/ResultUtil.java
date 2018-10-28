package com.wizzstudio.substitute.util;

import com.wizzstudio.substitute.dto.ResultDTO;
import com.wizzstudio.substitute.pojo.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created By Cx On 2018/10/28 14:52
 */
public class ResultUtil<T> {

    public static ResponseEntity success(Object data) {
        return new ResponseEntity<>(new ResultDTO<>(0, "请求成功", data), HttpStatus.OK);
    }

    public static ResponseEntity success() {
        return success(null);
    }

    public static ResponseEntity error(Integer code, String msg) {
        return new ResponseEntity<>(new ResultDTO<>(code, msg, null),HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity error(String msg) {
        return error(-1, msg);
    }
}
