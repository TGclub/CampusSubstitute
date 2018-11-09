package com.wizzstudio.substitute.web.controller;

import com.wizzstudio.substitute.constants.Constant;
import com.wizzstudio.substitute.dto.ResultDTO;
import com.wizzstudio.substitute.service.AddressService;
import com.wizzstudio.substitute.service.IndentService;
import com.wizzstudio.substitute.service.UserService;
import com.wizzstudio.substitute.util.RedisUtil;
import com.wizzstudio.substitute.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class BaseController {

    HttpServletRequest request;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserService userService;

    @Autowired
    protected AddressService addressService;

    @Autowired
    public BaseController(HttpServletRequest request, UserService userService) {
        this.request = request;
        this.userService = userService;
    }

    public BaseController() {
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        log.error(e.getMessage());
      //  if (e instanceof AccessDeniedException)
       //     return ResultUtil.error(Constant.SYSTEM_BUSY_CODE, e.getMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<ResultDTO>(new ResultDTO<>(Constant.SYSTEM_BUSY_CODE, e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

}
