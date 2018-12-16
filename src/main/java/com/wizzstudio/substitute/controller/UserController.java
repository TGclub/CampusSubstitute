package com.wizzstudio.substitute.controller;

import com.wizzstudio.substitute.domain.*;
import com.wizzstudio.substitute.dto.AddressDTO;
import com.wizzstudio.substitute.dto.UserBasicInfo;
import com.wizzstudio.substitute.dto.ModifyUserInfoDTO;
import com.wizzstudio.substitute.util.ResultUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wizzstudio.substitute.enums.GenderEnum.NO_LIMITED;
import static com.wizzstudio.substitute.enums.indent.IndentTypeEnum.HELP_OTHER;

@RestController
@RequestMapping("/user")
@Slf4j
@Secured("ROLE_USER")
public class UserController extends BaseController {

    /**
     * 用户基本信息获取
     * @param userId
     * @return
     */
    @GetMapping(value = "/{userId}")
    public ResponseEntity getUseInfo(@PathVariable String userId, Principal principal) {
        User user = userService.findUserById(userId);
        if (user != null) {
            return ResultUtil.success(user);
        } else {
            return ResultUtil.error();
        }
    }

    /**
     * 获取用户当日作为推荐人的收益
     */
    @GetMapping("/income/master/{userId}")
    public ResponseEntity getMasterTodayIncome(@PathVariable String userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("masterIncome",userService.getMasterTodayIncome(userId));
        return ResultUtil.success(map);
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

    @PostMapping(value = "/address")
    public ResponseEntity addAddress(@RequestBody AddressDTO address, Principal principal) {
        String userId = principal.getName();
        addressService.addUsualAddress(userId, address);
        return ResultUtil.success();
    }

    @GetMapping(value = "/address/delete")
    public ResponseEntity deleteAddress(Integer addressId, Principal principal) {
        addressService.deleteAddress(addressId, principal.getName());
        return ResultUtil.success();
    }

    @PostMapping("/address/modify")
    public ResponseEntity modifyAddress(@RequestBody AddressDTO address, Principal principal) {
        addressService.modifyAddress(address.getAddressId(), principal.getName(), address);
        return ResultUtil.success();
    }

    /**
     * 获取所有常用地址列表, 当key不为空的时候按关键词进行模糊匹配
     */
    @GetMapping(value = "/addresses/{userId}")
    public ResponseEntity getAllAddress(@PathVariable String userId, String key) {
        List<Address> addresses;
        if (key != null) {
            addresses = addressService.getAllByAddress(userId,key);
        } else {
            addresses = addressService.getUsualAddress(userId);
        }
        return ResultUtil.success(addresses);
    }

}
