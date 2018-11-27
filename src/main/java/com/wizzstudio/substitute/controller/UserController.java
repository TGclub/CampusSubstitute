package com.wizzstudio.substitute.controller;

import com.wizzstudio.substitute.dto.UserBasicInfo;
import com.wizzstudio.substitute.dto.ModifyUserInfoDTO;
import com.wizzstudio.substitute.domain.Address;
import com.wizzstudio.substitute.domain.School;
import com.wizzstudio.substitute.domain.User;
import com.wizzstudio.substitute.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
@Secured("ROLE_USER")
public class UserController extends BaseController {


    /**
     * 用户基本信息获取
     */
    @GetMapping(value = "/{userId}")
    public ResponseEntity getUseInfo(@PathVariable String userId) {
        User user = userService.findUserById(userId);
        if (user != null) {
            return ResultUtil.success(user);
        } else {
            return ResultUtil.error();
        }
    }

    /**
     * 修改用户信息
     */
    @PostMapping(value = "/info/{userId}")
    public ResponseEntity modifyUserInfo(@PathVariable String userId, @RequestBody ModifyUserInfoDTO modifyUserInfoDTO) {
        userService.modifyUserInfo(userId, modifyUserInfoDTO);
        return ResultUtil.success();
    }

    /**
     * 获取徒弟信息
     */
    @GetMapping(value = "/apprentices/{userId}")
    public ResponseEntity getAllApprenticesInfo(@PathVariable String userId) {
        List<UserBasicInfo> usersInfo = userService
                .getBasicInfo(new ArrayList<UserBasicInfo>(), userId);
        return ResultUtil.success(usersInfo);
    }

    /**
     * 获取师傅信息
     */
    @GetMapping(value = "/master/{userId}")
    public ResponseEntity getMasterInfo(@PathVariable String userId) {
        UserBasicInfo basicInfo = userService.getBasicInfo
                (new UserBasicInfo(), userId);
        return ResultUtil.success(basicInfo);
    }

    /**
     * 添加师傅
     */
    @PostMapping(value = "/master/{userId}/{masterId}")
    public ResponseEntity addMaster(@PathVariable String userId, @PathVariable String masterId) {
        if (userService.addReferrer(userId, masterId)) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error();
        }
    }

    /**
     * 添加某用户的常用地址
     */
    @PostMapping(value = "/address/{userId}")
    public ResponseEntity addAddress(@PathVariable String userId, @RequestBody String address) {
        addressService.addUsualAddress(userId, address);
        return ResultUtil.success();
    }

    /**
     * 获取某用户所有常用地址列表
     */
    @GetMapping(value = "/addresses/{userId}")
    public ResponseEntity getAllAddress(@PathVariable String userId) {
        List<Address> addresses = addressService.getUsualAddress(userId);
        return ResultUtil.success(addresses);
    }

    /**
     * 通过key关键字模糊匹配相应的schoolName
     */
    @GetMapping(value = "/school")
    public ResponseEntity getSchool(String key) {
        List<School> schools = addressService.getSchoolInFuzzyMatching(key);
        return ResultUtil.success(schools);
    }

}
