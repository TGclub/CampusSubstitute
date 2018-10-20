package com.wizzstudio.substitute.web.controller;

import com.wizzstudio.substitute.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    private HttpServletRequest request;
    private UserService userService;

    @Autowired
    public UserController(HttpServletRequest request, UserService userService) {
        this.request = request;
        this.userService = userService;
    }
}
