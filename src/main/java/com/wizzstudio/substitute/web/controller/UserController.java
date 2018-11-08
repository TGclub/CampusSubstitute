package com.wizzstudio.substitute.web.controller;

import com.wizzstudio.substitute.constants.Constant;
import com.wizzstudio.substitute.dto.UserBasicInfo;
import com.wizzstudio.substitute.dto.ModifyUserInfoDTO;
import com.wizzstudio.substitute.dto.ResultDTO;
import com.wizzstudio.substitute.pojo.Address;
import com.wizzstudio.substitute.pojo.School;
import com.wizzstudio.substitute.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController extends BaseController {


    /**
     * 用户基本信息获取
     * @param userId
     * @return
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity getUseInfo(@PathVariable String userId) {
        User user = userService.findUserById(userId);

        if (user != null) {
            return new ResponseEntity<ResultDTO>(new ResultDTO<User>(Constant.REQUEST_SUCCEED, Constant.QUERY_SUCCESSFULLY, user), HttpStatus.OK);
        } else {
            return new ResponseEntity<ResultDTO>(new ResultDTO<User>(Constant.SYSTEM_BUSY, Constant.QUERY_FAILED, null), HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<ResultDTO>(new ResultDTO<User>(Constant.REQUEST_SUCCEED, Constant.QUERY_SUCCESSFULLY, null), HttpStatus.OK);
        } else {
            return new ResponseEntity<ResultDTO>(new ResultDTO<User>(Constant.SYSTEM_BUSY, Constant.QUERY_FAILED, null), HttpStatus.BAD_REQUEST);
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
                (Constant.REQUEST_SUCCEED, Constant.QUERY_SUCCESSFULLY, userService
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
                new ResultDTO<UserBasicInfo>(Constant.REQUEST_SUCCEED, Constant.QUERY_SUCCESSFULLY, userService.getBasicInfo
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
            resultDTO.setCode(Constant.REQUEST_SUCCEED);
            resultDTO.setMsg(Constant.QUERY_SUCCESSFULLY);
        } else {
            resultDTO.setCode(Constant.SYSTEM_BUSY);
            resultDTO.setMsg(Constant.QUERY_FAILED);
        }
        return new ResponseEntity<ResultDTO>(resultDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/address/{userId}", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity addAddress(@PathVariable String userId, @RequestBody String address) {
        addressService.addUsualAddress(userId, address);
        return new ResponseEntity<ResultDTO>
                (new ResultDTO<>(Constant.REQUEST_SUCCEED, Constant.QUERY_SUCCESSFULLY, null), HttpStatus.OK);
    }

    @RequestMapping(value = "/school", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity getSchool(String key) {
        List<School> schools = addressService.getSchoolInFuzzyMatching(key);
        return new ResponseEntity<ResultDTO>(new ResultDTO<List<School>>
                (Constant.REQUEST_SUCCEED, Constant.QUERY_SUCCESSFULLY, schools), HttpStatus.OK);
    }

    @RequestMapping(value = "/addresses/{userId}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity getAllAddress(@PathVariable String userId) {
        List<Address> addresses = addressService.getUsualAddress(userId);
        return new ResponseEntity<ResultDTO>(new ResultDTO<List<Address>>(Constant.REQUEST_SUCCEED, Constant.QUERY_SUCCESSFULLY, addresses) ,HttpStatus.OK);
    }

}
