package com.wizzstudio.substitute.controller;

import com.wizzstudio.substitute.constants.Constant;
import com.wizzstudio.substitute.dto.AdminLoginDTO;
import com.wizzstudio.substitute.dto.wx.WxInfo;
import com.wizzstudio.substitute.domain.User;
import com.wizzstudio.substitute.service.UserService;
import com.wizzstudio.substitute.util.CookieUtil;
import com.wizzstudio.substitute.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@RestController
@Slf4j
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/login/user")
    public ResponseEntity login(@NotNull @RequestBody WxInfo loginData, HttpServletResponse response) {
        try {
            //获取用户信息，若用户未使用过该程序，先进行注册
            User user = userService.userLogin(loginData);
            String cookie = CookieUtil.tokenGenerate();
            redisUtil.storeNewCookie(cookie, user.getId());
            CookieUtil.setCookie(response, Constant.TOKEN, cookie, Constant.TOKEN_EXPIRED);
            return ResultUtil.success(user);
        } catch (WxErrorException e) {
            log.error("【微信登录】登录失败，e={}",e);
            return ResultUtil.error();
        }
    }

    @PostMapping("/login/admin")
    public ResponseEntity login(@NotNull @RequestBody AdminLoginDTO loginDTO, HttpServletResponse response) {
        if (adminService.isValidAdmin(loginDTO)) {
            String cookie = CookieUtil.tokenGenerate();
            redisUtil.storeNewCookie(cookie, loginDTO.getAdminName());
            CookieUtil.setCookie(response, Constant.TOKEN, cookie, Constant.TOKEN_EXPIRED);
            return ResultUtil.success();
        }
        return ResultUtil.error();
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie cookie = CookieUtil.getCookie(request);
        if (cookie != null) redisUtil.delete(cookie.getValue());
        response.sendRedirect("/login/admin");
    }

    /*
    @PostMapping("/test")
    public String test(@RequestBody AdminLoginDTO dto) {

        return "Heoo " + dto.getAdminName();
    }*/


}
