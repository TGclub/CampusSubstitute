package com.wizzstudio.substitute.web.controller;

import com.wizzstudio.substitute.constants.Constants;
import com.wizzstudio.substitute.dto.ResultDTO;
import com.wizzstudio.substitute.service.AddressService;
import com.wizzstudio.substitute.service.IndentService;
import com.wizzstudio.substitute.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class BaseController {

    HttpServletRequest request;

    @Autowired
    UserService userService;

    @Autowired
    protected IndentService indentService;
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
    public @ResponseBody ResponseEntity handleException() {
        return new ResponseEntity<ResultDTO>(new ResultDTO<>(Constants.SYSTEM_BUSY, Constants.QUERY_FAILED, null), HttpStatus.OK);
    }

}
