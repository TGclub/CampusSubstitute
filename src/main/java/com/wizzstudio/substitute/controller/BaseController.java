package com.wizzstudio.substitute.controller;

import com.wizzstudio.substitute.constants.Constant;
import com.wizzstudio.substitute.dto.ResultDTO;
import com.wizzstudio.substitute.exception.SubstituteException;
import com.wizzstudio.substitute.service.AddressService;
import com.wizzstudio.substitute.service.AdminService;
import com.wizzstudio.substitute.service.IndentService;
import com.wizzstudio.substitute.service.UserService;
import com.wizzstudio.substitute.util.RedisUtil;
import com.wizzstudio.substitute.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
    AdminService adminService;

    @Autowired
    protected AddressService addressService;

    @Autowired
    public BaseController(HttpServletRequest request, UserService userService) {
        this.request = request;
        this.userService = userService;
    }

    public BaseController() {
    }

    @ExceptionHandler(SubstituteException.class)
    @ResponseBody
    public ResponseEntity handleSubstituteException(SubstituteException e) {
        return ResultUtil.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity handleAccessDeniedException(Exception e) {
        log.error(e.getMessage());
        return ResultUtil.error(Constant.SYSTEM_BUSY_CODE, e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity handleNPE(Exception e) {
        log.error(e.getMessage());
        return ResultUtil.error(Constant.SYSTEM_BUSY_CODE,null, HttpStatus.NOT_FOUND);
    }

}
