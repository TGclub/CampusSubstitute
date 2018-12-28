package com.wizzstudio.substitute.controller;

import com.wizzstudio.substitute.constants.Constant;
import com.wizzstudio.substitute.dto.AdminLoginDTO;
import com.wizzstudio.substitute.dto.wx.WxInfo;
import com.wizzstudio.substitute.domain.User;
import com.wizzstudio.substitute.security.service.CustomUserDetailsService;
import com.wizzstudio.substitute.service.UserService;
import com.wizzstudio.substitute.util.CookieUtil;
import com.wizzstudio.substitute.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * 用户注册
     */
    @PostMapping("/login/user")
    public ResponseEntity login(@NotNull @RequestBody WxInfo loginData, HttpServletResponse response, HttpServletRequest request) {
        try {
            //获取用户信息，若用户未使用过该程序，先进行注册
            User user = userService.userLogin(loginData);
            String cookie = CookieUtil.tokenGenerate();
            redisUtil.storeNewCookie(cookie, user.getId());
            CookieUtil.setCookie(response, Constant.TOKEN, cookie, Constant.TOKEN_EXPIRED);
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getId());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResultUtil.success(user);
        } catch (WxErrorException | NullPointerException e) {
            log.error("【微信登录】登录失败，e={}", e);
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 前端要求返回一个布尔值表示是否为二级管理员--cx
     */
//    @CrossOrigin(origins = "*")
    @PostMapping("/login/admin")
    public ResponseEntity login(@NotNull @RequestBody AdminLoginDTO loginDTO, HttpServletResponse response) {
        if (adminService.isValidAdmin(loginDTO)) {
            Map<String, Object> res = new HashMap<>();
            res.put("admin", adminService.getAdminByName(loginDTO.getAdminName()));
            String cookie = CookieUtil.tokenGenerate();
            redisUtil.storeNewCookie(cookie, loginDTO.getAdminName());
            CookieUtil.setCookie(response, Constant.TOKEN, cookie, Constant.TOKEN_EXPIRED);
            response.setHeader("TOKEN", cookie);
            res.put("token", cookie);
            return ResultUtil.success(res);
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
