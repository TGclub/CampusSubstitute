package com.wizzstudio.substitute.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.wizzstudio.substitute.constants.Constant;
import com.wizzstudio.substitute.dto.AdminLoginDTO;
import com.wizzstudio.substitute.dto.wx.WxInfo;
import com.wizzstudio.substitute.enums.GenderEnum;
import com.wizzstudio.substitute.enums.Role;
import com.wizzstudio.substitute.domain.User;
import com.wizzstudio.substitute.util.CookieUtil;
import com.wizzstudio.substitute.util.RandomUtil;
import com.wizzstudio.substitute.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.security.Principal;

@Controller
@Slf4j
public class LoginController extends BaseController{

    @Autowired
    private WxMaService wxService;

    /**
     * 用户注册
     * @param loginData
     * @return
     */
    @PostMapping("login/user")
    public ResponseEntity login(@NotNull @RequestBody WxInfo loginData, HttpServletResponse response) {
        try {
            WxMaJscode2SessionResult sessionResult = wxService.getUserService().getSessionInfo(loginData.getCode());
            User user = userService.findUserByOpenId(sessionResult.getOpenid());
            if (user == null) {
                //获得一个未被使用过的userId
                String userId = RandomUtil.getSixRandom();
                user = userService.findUserById(userId);
                while(user != null){
                    userId = RandomUtil.getSixRandom();
                    user = userService.findUserById(userId);
                }
                WxMaUserInfo wxUserInfo = wxService.getUserService().getUserInfo(sessionResult.getSessionKey(), loginData.getEncryptedData(), loginData.getIv());
                user = User.newBuilder()
                        .setId(userId)
                        .setUserName(wxUserInfo.getNickName())
                        .setOpenid(wxUserInfo.getOpenId())
                        .setAvatar(wxUserInfo.getAvatarUrl())
                        .setRole(Role.ROLE_USER)
                        .build();
                switch (Integer.valueOf(wxUserInfo.getGender())){
                    //性别 0：未知、1：男、2：女
                    case 0:
                        user.setGender(GenderEnum.NO_LIMITED);
                        break;
                    case 1:
                        user.setGender(GenderEnum.MALE);
                        break;
                    case 2:
                        user.setGender(GenderEnum.FEMALE);
                        break;
                    default:
                        return ResultUtil.error("用户信息有误");
                }
                userService.saveUser(user);
                String cookie = CookieUtil.tokenGenerate();
                redisUtil.storeNewCookie(cookie, user.getId());
                CookieUtil.setCookie(response, Constant.TOKEN, cookie, Constant.TOKEN_EXPIRED);
                log.info("Add a new account for " + user.getOpenid());
            }
            return ResultUtil.success(user);
        } catch (WxErrorException e) {
            return ResultUtil.error();
        }
    }

    @PostMapping("login/admin")
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



}
