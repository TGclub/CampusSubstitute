package com.wizzstudio.substitute.web.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.wizzstudio.substitute.constants.Constants;
import com.wizzstudio.substitute.dto.UserBasicInfo;
import com.wizzstudio.substitute.dto.ModifyUserInfoDTO;
import com.wizzstudio.substitute.dto.WxInfo;
import com.wizzstudio.substitute.dto.ResultDTO;
import com.wizzstudio.substitute.enums.Gender;
import com.wizzstudio.substitute.enums.Role;
import com.wizzstudio.substitute.pojo.Address;
import com.wizzstudio.substitute.pojo.School;
import com.wizzstudio.substitute.pojo.User;
import com.wizzstudio.substitute.util.KeyUtil;
import com.wizzstudio.substitute.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController extends BaseController {

    @Autowired
    private WxMaService wxService;

    /**
     * 用户注册
     * @param loginData
     * @return
     */

    @PostMapping("/login")
    public ResponseEntity login(@NotNull @RequestBody WxInfo loginData) {
        try {
            WxMaJscode2SessionResult sessionResult = wxService.getUserService().getSessionInfo(loginData.getCode());
            User user = userService.findUserByOpenId(sessionResult.getOpenid());
            if (user == null) {
                WxMaUserInfo wxUserInfo = wxService.getUserService().getUserInfo(sessionResult.getSessionKey(), loginData.getEncryptedData(), loginData.getIv());
                user = User.newBuilder()
                        .setUserName(wxUserInfo.getNickName())
                        .setOpenid(wxUserInfo.getOpenId())
                        .setAvatar(wxUserInfo.getAvatarUrl())
                        .setRole(Role.ROLE_USER)
                        .build();
                switch (Integer.valueOf(wxUserInfo.getGender())){
                    //性别 0：未知、1：男、2：女
                    case 0:
                        user.setGender(Gender.NO_LIMITED);
                        break;
                    case 1:
                        user.setGender(Gender.MALE);
                        break;
                    case 2:
                        user.setGender(Gender.FAMALE);
                        break;
                    default:
                        return ResultUtil.error("用户信息有误");
                }
                userService.addNewUser(user);
                log.info("Add a new account for " + user.getOpenid());
            }
            return new ResponseEntity<ResultDTO<User>>(new ResultDTO<User>(Constants.REQUEST_SUCCEED, Constants.QUERY_SUCCESSFULLY, user), HttpStatus.OK);
        } catch (WxErrorException e) {
            return new ResponseEntity<ResultDTO<User>>(new ResultDTO<User>(Constants.SYSTEM_BUSY, "Failed", null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 用户基本信息获取
     * @param openid
     * @return
     */
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

    /**
     * 修改用户信息
     * @param userId
     * @param modifyUserInfoDTO
     * @return
     */
    @RequestMapping(value = "/info/{userId}", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity modifyUserInfo(@PathVariable String userId, @RequestBody ModifyUserInfoDTO modifyUserInfoDTO) {

        if (userService.modifyUserInfo(userId, modifyUserInfoDTO) != null) {
            return new ResponseEntity<ResultDTO>(new ResultDTO<User>(Constants.REQUEST_SUCCEED, Constants.QUERY_SUCCESSFULLY, null), HttpStatus.OK);
        } else {
            return new ResponseEntity<ResultDTO>(new ResultDTO<User>(Constants.SYSTEM_BUSY, Constants.QUERY_FAILED, null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 获取徒弟信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/apprentices/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity getAllApprenticesInfo(@PathVariable String userId) {
        return new ResponseEntity<ResultDTO<List<UserBasicInfo>>>(new ResultDTO<List<UserBasicInfo>>
                (Constants.REQUEST_SUCCEED, Constants.QUERY_SUCCESSFULLY, userService
                        .getBasicInfo(new ArrayList<UserBasicInfo>(), userId)), HttpStatus.OK);
    }

    /**
     * 获取师傅信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/master/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity getMasterInfo(@PathVariable String userId) {

        return new ResponseEntity<ResultDTO>(
                new ResultDTO<UserBasicInfo>(Constants.REQUEST_SUCCEED, Constants.QUERY_SUCCESSFULLY, userService.getBasicInfo
                        (new UserBasicInfo(), userId)), HttpStatus.OK);
    }

    /**
     * 添加师傅
     * @param userId
     * @param masterId
     * @return
     */
    @RequestMapping(value = "/master/{userId}/{masterId}", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity addMaster(@PathVariable String userId, @PathVariable String masterId) {
        ResultDTO resultDTO = new ResultDTO();
        if (userService.addReferrer(userId, masterId)) {
            resultDTO.setCode(Constants.REQUEST_SUCCEED);
            resultDTO.setMsg(Constants.QUERY_SUCCESSFULLY);
        } else {
            resultDTO.setCode(Constants.SYSTEM_BUSY);
            resultDTO.setMsg(Constants.QUERY_FAILED);
        }
        return new ResponseEntity<ResultDTO>(resultDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/address/{userId}", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity addAddress(@PathVariable String userId, @RequestBody String address) {
        addressService.addUsualAddress(userId, address);
        return new ResponseEntity<ResultDTO>
                (new ResultDTO<>(Constants.REQUEST_SUCCEED, Constants.QUERY_SUCCESSFULLY, null), HttpStatus.OK);
    }

    @RequestMapping(value = "/school", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity getSchool(String key) {
        List<School> schools = addressService.getSchoolInFuzzyMatching(key);
        return new ResponseEntity<ResultDTO>(new ResultDTO<List<School>>
                (Constants.REQUEST_SUCCEED, Constants.QUERY_SUCCESSFULLY, schools), HttpStatus.OK);
    }

    @RequestMapping(value = "/addresses/{userId}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity getAllAddress(@PathVariable String userId) {
        List<Address> addresses = addressService.getUsualAddress(userId);
        return new ResponseEntity<ResultDTO>(new ResultDTO<List<Address>>(Constants.REQUEST_SUCCEED, Constants.QUERY_SUCCESSFULLY, addresses) ,HttpStatus.OK);
    }

}
