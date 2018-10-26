package com.wizzstudio.substitute.web.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.wizzstudio.substitute.constants.Constants;
import com.wizzstudio.substitute.dto.ApprenticeBasicInfo;
import com.wizzstudio.substitute.dto.ModifyUserInfoDTO;
import com.wizzstudio.substitute.dto.WxInfo;
import com.wizzstudio.substitute.dto.ResultDTO;
import com.wizzstudio.substitute.enums.Role;
import com.wizzstudio.substitute.pojo.User;
import com.wizzstudio.substitute.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController extends BaseController{

    @Autowired
    private WxMaService wxService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity login(@NotNull @RequestBody WxInfo loginData) {
        try {
            WxMaJscode2SessionResult sessionResult = wxService.getUserService().getSessionInfo(loginData.getCode());
            User user = userService.findUserByOpenId(sessionResult.getOpenid());
            if (user == null) {
                WxMaUserInfo wxUserInfo = wxService.getUserService().getUserInfo(sessionResult.getSessionKey(), loginData.getEncryptedData(), loginData.getIv());
                String userId = KeyUtil.getUserUniqueKey();
                while (userService.findUserById(userId) != null) {
                    userId = KeyUtil.getUserUniqueKey();
                }
                user = User.newBuilder()
                        .setId(userId)
                        .setUserName(wxUserInfo.getNickName())
                        .setOpenid(wxUserInfo.getOpenId())
                        .setAvatar(wxUserInfo.getAvatarUrl())
                        .setRole(Role.ROLE_USER)
                        //.setGender(wxUserInfo.getGender())
                        .build();

                userService.addNewUser(user);
                log.info("Add a new account " + userId + " for " + user.getOpenid());
            }
            return new ResponseEntity<ResultDTO<User>>(new ResultDTO<User>(Constants.REQUEST_SUCCEED, Constants.QUERY_SUCCESSFULLY, user), HttpStatus.OK);
        } catch (WxErrorException e) {
            return new ResponseEntity<ResultDTO<User>>(new ResultDTO<User>(Constants.SYSTEM_BUSY, "Failed", null), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{openid}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity getUseInfo(@PathVariable String openid) {
        User user = userService.findUserByOpenId(openid);

        if (user != null) {
            return new ResponseEntity<ResultDTO>(new ResultDTO<User>(Constants.REQUEST_SUCCEED, Constants.QUERY_SUCCESSFULLY, user), HttpStatus.OK);
        } else {
            return new ResponseEntity<ResultDTO>(new ResultDTO<User>(Constants.SYSTEM_BUSY, Constants.QUERY_FAILED, null), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/info/{userId}", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity modifyUserInfo(@PathVariable String userId, @RequestBody ModifyUserInfoDTO modifyUserInfoDTO) {

        if (userService.modifyUserInfo(userId, modifyUserInfoDTO) != null) {
            return new ResponseEntity<ResultDTO>(new ResultDTO<User>(Constants.REQUEST_SUCCEED, Constants.QUERY_SUCCESSFULLY, null), HttpStatus.OK);
        } else {
            return new ResponseEntity<ResultDTO>(new ResultDTO<User>(Constants.SYSTEM_BUSY, Constants.QUERY_FAILED, null), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/apprentices/{userId}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity getAllApprenticesInfo(@PathVariable String userId) {
        return new ResponseEntity<ResultDTO<List<ApprenticeBasicInfo>>>(new ResultDTO<List<ApprenticeBasicInfo>>
                (Constants.REQUEST_SUCCEED, Constants.QUERY_SUCCESSFULLY,userService.getApprenticeInfo(userId)), HttpStatus.OK);
    }

}
