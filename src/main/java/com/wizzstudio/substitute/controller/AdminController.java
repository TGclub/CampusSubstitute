package com.wizzstudio.substitute.controller;

import com.wizzstudio.substitute.VO.WithdrawRequestVO;
import com.wizzstudio.substitute.domain.AdminInfo;
import com.wizzstudio.substitute.domain.WithdrawRequest;
import com.wizzstudio.substitute.dto.AdminCouponDTO;
import com.wizzstudio.substitute.dto.ResultDTO;
import com.wizzstudio.substitute.enums.Role;
import com.wizzstudio.substitute.service.AdminService;
import com.wizzstudio.substitute.service.SchoolService;
import com.wizzstudio.substitute.service.UserService;
import com.wizzstudio.substitute.util.ResultUtil;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * Created by Kikyou on 18-11-12
 */
@Api(value = "后台管理接口", description = "分配权限，查看统计信息， 处理应急订单和优惠券等服务, 不要看它自动生成的example，是错的，" +
        "而且swagger这东西提供的自定义接口还不起作用，醉了。example以description里的为准，另外，所有的仅返回操作结果没有具体数据的api返回体格式均为 实例：{\n" +
        "    \"code\": 0,\n" +
        "    \"msg\": \"请求成功\",\n" +
        "    \"data\": null\n" +
        "},\n另二级管理员仅拥有查看和处理反馈信息的权限")
@RestController
@RequestMapping("/admin")
@Secured("ROLE_ADMIN_2")
@CrossOrigin(origins = "http://bang.zhengsj.top")
public class AdminController {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    /**
     * 分配权限接口
     *
     * @param id        被分配管理员id
     * @param privilege 被分配权限
     * @return 处理结果，200 成功，400 失败 401 权限不足
     */
    @ApiOperation("授权接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "被分配管理员id", dataType = "int"),
            @ApiImplicitParam(name = "privilege", value = "被分配权限", dataType = "string")
    })
    @GetMapping("/privilege/allocation/{id}/{privilege}")
    @Secured("ROLE_ADMIN_1")
    public ResponseEntity allocatePrivilege(@PathVariable Integer id, @PathVariable String privilege) {
        Role role = Role.valueOf(privilege);
        adminService.allocatePrivilege(id, role);
        return ResultUtil.success();
    }

    /**
     * 创建新的管理员
     *
     * @param info          管理员信息
     * @param bindingResult
     * @return 处理结果，200 成功，400 失败 401 权限不足
     */
    @ApiOperation("创建新的管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminId", value = "管理员id"),
            @ApiImplicitParam(name = "adminName", value = "管理员姓名"),
            @ApiImplicitParam(name = "adminPass", value = "密码"),
            @ApiImplicitParam(name = "adminPhone", value = "电话"),
            @ApiImplicitParam(name = "adminRole", value = "权限"),
            @ApiImplicitParam(name = "adminSchoolId", value = "负责的学校id"),
            @ApiImplicitParam(name = "isBoss", value = "是否是负责人， true 是， false 不是")}
    )
    @Secured("ROLE_ADMIN_1")
    @PostMapping("/create")
    public ResponseEntity createNewAdministrator(@RequestBody @Valid AdminInfo info, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return ResultUtil.error();
        adminService.addNewAdmin(info);
        return ResultUtil.success();
    }

    /**
     * 查询未被接单的订单列表
     *
     * @param principal
     * @return 订单列表
     */
    @ApiOperation("查看未被接单的订单列表")
    @ApiResponse(code = 200, message = "订单列表")
    @GetMapping("statistics/viewUnPicked")
    public ResponseEntity viewUnPickedIndent(Principal principal) {
        return ResultUtil.success(adminService.getUnPickedIndent());

    }

    /**
     * 查询日期范围闭区间的每日统计信息
     *
     * @param from 起始日期
     * @param to   结束日趋
     * @return 统计信息列表
     */
    @ApiOperation("查询日期范围闭区间的每日统计信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "from", value = "起始日期"),
            @ApiImplicitParam(name = "to", value = "结束日期")
    })
    @Secured("ROLE_ADMIN_1")
    @GetMapping("statistics/viewConcreteInfo/{from}/{to}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "200请求成功，其他见返回码说明 返回示例：{\n" +
                    "    \"code\": 0,\n" +
                    "    \"msg\": \"请求成功\",\n" +
                    "    \"data\": [\n" +
                    "        {\n" +
                    "            \"countId\": 6,\n" +
                    "            \"schoolId\": 1,\n" +
                    "            \"countDate\": 6,\n" +
                    "            \"newIndent\": 3,\n" +
                    "            \"finishedIndent\": 4,\n" +
                    "            \"income\": 5,\n" +
                    "            \"loginUser\": 9\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}")
    })
    public ResponseEntity viewConcreteInfo(@PathVariable Integer from, @PathVariable Integer to) {
        return ResultUtil.success(adminService.getConcreteCountInfo(from, to));
    }

    /**
     * 查询所有的紧急订单
     *
     * @return 紧急订单列表
     */
    @ApiOperation("查询所有未处理的紧急订单")
    @ApiResponses({
    @ApiResponse(code = 200, message = "{\n" +
            "    \"code\": 0,\n" +
            "    \"msg\": \"请求成功\",\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"indentId\": 29,\n" +
            "            \"performerId\": \"XB4lFU\",\n" +
            "            \"publisherId\": \"XZQ7ja\",\n" +
            "            \"couponId\": null,\n" +
            "            \"indentType\": \"HELP_SEND\",\n" +
            "            \"requireGender\": \"MALE\",\n" +
            "            \"indentContent\": \"qwe\",\n" +
            "            \"indentPrice\": 23,\n" +
            "            \"couponPrice\": 0,\n" +
            "            \"totalPrice\": 23,\n" +
            "            \"urgentType\": 1,\n" +
            "            \"isSolved\": false,\n" +
            "            \"indentState\": \"CANCELED\",\n" +
            "            \"takeGoodAddress\": \"qwddde\",\n" +
            "            \"shippingAddressId\": 18,\n" +
            "            \"secretText\": \"快递号101010\",\n" +
            "            \"goodPrice\": null,\n" +
            "            \"createTime\": \"2018-12-14T11:45:41.000+0000\",\n" +
            "            \"updateTime\": \"2018-12-18T01:25:43.000+0000\",\n" +
            "            \"userName\": \"Kiris\",\n" +
            "            \"trueName\": \"张俊\",\n" +
            "            \"phone\": null,\n" +
            "            \"school\": \"西安邮电大学\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"indentId\": 5,\n" +
            "            \"performerId\": null,\n" +
            "            \"publisherId\": \"EEETEE\",\n" +
            "            \"couponId\": null,\n" +
            "            \"indentType\": \"HELP_SEND\",\n" +
            "            \"requireGender\": \"NO_LIMITED\",\n" +
            "            \"indentContent\": \"我在呢\",\n" +
            "            \"indentPrice\": 5,\n" +
            "            \"couponPrice\": 0,\n" +
            "            \"totalPrice\": 0,\n" +
            "            \"urgentType\": 1,\n" +
            "            \"isSolved\": false,\n" +
            "            \"indentState\": \"WAIT_FOR_PERFORMER\",\n" +
            "            \"takeGoodAddress\": \"1\",\n" +
            "            \"shippingAddressId\": 2,\n" +
            "            \"secretText\": null,\n" +
            "            \"goodPrice\": null,\n" +
            "            \"createTime\": \"2018-11-28T02:47:29.000+0000\",\n" +
            "            \"updateTime\": \"2018-12-03T05:49:48.000+0000\",\n" +
            "            \"userName\": \"twet\",\n" +
            "            \"trueName\": null,\n" +
            "            \"phone\": 110112119,\n" +
            "            \"school\": \"西安邮电大学\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"indentId\": 4,\n" +
            "            \"performerId\": \"6gxVye\",\n" +
            "            \"publisherId\": \"EEETEE\",\n" +
            "            \"couponId\": null,\n" +
            "            \"indentType\": \"HELP_OTHER\",\n" +
            "            \"requireGender\": \"NO_LIMITED\",\n" +
            "            \"indentContent\": \"我在呢\",\n" +
            "            \"indentPrice\": 4,\n" +
            "            \"couponPrice\": 0,\n" +
            "            \"totalPrice\": 0,\n" +
            "            \"urgentType\": 1,\n" +
            "            \"isSolved\": false,\n" +
            "            \"indentState\": \"WAIT_FOR_PERFORMER\",\n" +
            "            \"takeGoodAddress\": null,\n" +
            "            \"shippingAddressId\": 2,\n" +
            "            \"secretText\": null,\n" +
            "            \"goodPrice\": null,\n" +
            "            \"createTime\": \"2018-11-10T13:45:15.000+0000\",\n" +
            "            \"updateTime\": \"2018-12-03T05:49:48.000+0000\",\n" +
            "            \"userName\": \"twet\",\n" +
            "            \"trueName\": null,\n" +
            "            \"phone\": 110112119,\n" +
            "            \"school\": \"西安邮电大学\"\n" +
            "        }\n" +
            "    ]\n" +
            "}")})
    @Secured("ROLE_ADMIN_1")
    @GetMapping("/urgent/indent/unhandled")
    public ResponseEntity viewUrgentIndent(Integer schoolId) {
        return ResultUtil.success(adminService.getUrgentIndentsByHandledState(false, schoolId   ));
    }

    /**
     * 处理紧急订单
     *
     * @param id 订单id
     * @return 处理结果，200 成功，400 失败 401 权限不足
     */
    @ApiOperation("处理紧急订单")
    @ApiParam(name = "id", value = "订单id")
    @Secured("ROLE_ADMIN_1")
    @GetMapping("/urgent/indent/{id}")
    public ResponseEntity setHandled(@PathVariable int id) {
        adminService.handleUrgent(id);
        return ResultUtil.success();
    }

    /**
     * 查询已处理的紧急订单
     *
     * @return 已处理紧急订单列表
     */
    @ApiOperation("查询已处理的紧急订单")
    @Secured("ROLE_ADMIN_1")
    @ApiResponses({
    @ApiResponse(code = 200, message = "{\n" +
            "    \"code\": 0,\n" +
            "    \"msg\": \"请求成功\",\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"indentId\": 29,\n" +
            "            \"performerId\": \"XB4lFU\",\n" +
            "            \"publisherId\": \"XZQ7ja\",\n" +
            "            \"couponId\": null,\n" +
            "            \"indentType\": \"HELP_SEND\",\n" +
            "            \"requireGender\": \"MALE\",\n" +
            "            \"indentContent\": \"qwe\",\n" +
            "            \"indentPrice\": 23,\n" +
            "            \"couponPrice\": 0,\n" +
            "            \"totalPrice\": 23,\n" +
            "            \"urgentType\": 1,\n" +
            "            \"isSolved\": false,\n" +
            "            \"indentState\": \"CANCELED\",\n" +
            "            \"takeGoodAddress\": \"qwddde\",\n" +
            "            \"shippingAddressId\": 18,\n" +
            "            \"secretText\": \"快递号101010\",\n" +
            "            \"goodPrice\": null,\n" +
            "            \"createTime\": \"2018-12-14T11:45:41.000+0000\",\n" +
            "            \"updateTime\": \"2018-12-18T01:25:43.000+0000\",\n" +
            "            \"userName\": \"Kiris\",\n" +
            "            \"trueName\": \"张俊\",\n" +
            "            \"phone\": null,\n" +
            "            \"school\": \"西安邮电大学\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"indentId\": 5,\n" +
            "            \"performerId\": null,\n" +
            "            \"publisherId\": \"EEETEE\",\n" +
            "            \"couponId\": null,\n" +
            "            \"indentType\": \"HELP_SEND\",\n" +
            "            \"requireGender\": \"NO_LIMITED\",\n" +
            "            \"indentContent\": \"我在呢\",\n" +
            "            \"indentPrice\": 5,\n" +
            "            \"couponPrice\": 0,\n" +
            "            \"totalPrice\": 0,\n" +
            "            \"urgentType\": 1,\n" +
            "            \"isSolved\": false,\n" +
            "            \"indentState\": \"WAIT_FOR_PERFORMER\",\n" +
            "            \"takeGoodAddress\": \"1\",\n" +
            "            \"shippingAddressId\": 2,\n" +
            "            \"secretText\": null,\n" +
            "            \"goodPrice\": null,\n" +
            "            \"createTime\": \"2018-11-28T02:47:29.000+0000\",\n" +
            "            \"updateTime\": \"2018-12-03T05:49:48.000+0000\",\n" +
            "            \"userName\": \"twet\",\n" +
            "            \"trueName\": null,\n" +
            "            \"phone\": 110112119,\n" +
            "            \"school\": \"西安邮电大学\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"indentId\": 4,\n" +
            "            \"performerId\": \"6gxVye\",\n" +
            "            \"publisherId\": \"EEETEE\",\n" +
            "            \"couponId\": null,\n" +
            "            \"indentType\": \"HELP_OTHER\",\n" +
            "            \"requireGender\": \"NO_LIMITED\",\n" +
            "            \"indentContent\": \"我在呢\",\n" +
            "            \"indentPrice\": 4,\n" +
            "            \"couponPrice\": 0,\n" +
            "            \"totalPrice\": 0,\n" +
            "            \"urgentType\": 1,\n" +
            "            \"isSolved\": false,\n" +
            "            \"indentState\": \"WAIT_FOR_PERFORMER\",\n" +
            "            \"takeGoodAddress\": null,\n" +
            "            \"shippingAddressId\": 2,\n" +
            "            \"secretText\": null,\n" +
            "            \"goodPrice\": null,\n" +
            "            \"createTime\": \"2018-11-10T13:45:15.000+0000\",\n" +
            "            \"updateTime\": \"2018-12-03T05:49:48.000+0000\",\n" +
            "            \"userName\": \"twet\",\n" +
            "            \"trueName\": null,\n" +
            "            \"phone\": 110112119,\n" +
            "            \"school\": \"西安邮电大学\"\n" +
            "        }\n" +
            "    ]\n" +
            "}")})
    @GetMapping("/urgent/indent/handled")
    public ResponseEntity viewHandledUrgentIndent(Integer schoolId) {
        return ResultUtil.success(adminService.getUrgentIndentsByHandledState(true, schoolId));
    }

    /**
     * 查询所有的优惠券信息
     *
     * @return 优惠券列表
     */
    @ApiOperation("查询所有的优惠券信息")
    @ApiResponses({
    @ApiResponse(code = 200, message = "200请求成功，其他见返回码说明 返回示例：{\n" +
            "    \"code\": 0,\n" +
            "    \"msg\": \"请求成功\",\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"couponId\": 3,\n" +
            "            \"leastPrice\": 1,\n" +
            "            \"reducePrice\": 2,\n" +
            "            \"isDeleted\": false,\n" +
            "            \"validTime\": 1543806535000,\n" +
            "            \"invalidTime\": 1543806535000\n" +
            "        }\n" +
            "    ]\n" +
            "}" +
            "图片需额外向/coupon/img/{id}发起请求")})
    @Secured("ROLE_ADMIN_1")
    @GetMapping("/coupon/viewAll")
    public ResponseEntity viewAllCoupon() {

        return ResultUtil.success(adminService.getAllCoupon());
    }

    /**
     * 创建新的优惠券
     *
     * @param coupon 优惠券信息
     * @return 处理结果，200 成功，400 失败 401 权限不足
     */
    @ApiOperation("创建新的优惠券， 以form data的形式发送请求")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "leastPrice", value = "最小满减金额，单位元"),
            @ApiImplicitParam(name = "reducePrice", value = "可减金额，单位元"),
            @ApiImplicitParam(name = "validTime", value = "validTime, 使用unix时间精确到ms"),
            @ApiImplicitParam(name = "invalidTime", value = "失效时间"),
            @ApiImplicitParam(name = "picture", value = "优惠券图片")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "200请求成功，其他见返回码说明", response = ResultDTO.class)
    })
    @Secured("ROLE_ADMIN_1")
    @PostMapping("/coupon/addNew")
    public ResponseEntity newCoupon(AdminCouponDTO coupon) throws IOException {
        adminService.addNewCoupon(coupon);
        return ResultUtil.success();
    }

    @ApiOperation("返回指定优惠券的图片， 例如")
    @ApiImplicitParam(name = "id", value = "指定优惠券id", example = "localhost:2333/admin/coupon/img/3")
    @ApiResponses({
            @ApiResponse(code = 200, message = "200请求成功，返回二进制图片")


    })
    @Secured("ROLE_ADMIN_1")
    @GetMapping("/coupon/img/{id}")
    public void getImg(@PathVariable int id, HttpServletResponse response) throws IOException {
        IOUtils.copy(new ByteArrayInputStream(adminService.getSpecificCoupon(id).getPicture()),
                response.getOutputStream());
    }

    /**
     * 删除指定优惠券
     *
     * @param id 被删优惠券id
     * @return 处理结果，200 成功，400 失败 401 权限不足
     */
    @ApiOperation("删除指定优惠券")
    @ApiImplicitParam(name = "id", value = "被删优惠券id", example = "localhost:2333/admin/coupon/delete/1")
    @Secured("ROLE_ADMIN_1")
    @GetMapping("/coupon/delete/{id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "200请求成功，其他见返回码说明", response = ResultDTO.class)
    })
    public ResponseEntity deleteCoupon(@PathVariable int id) {
        adminService.deleteCoupon(id);
        return ResultUtil.success();
    }


    /**
     * 查询未处理的提现请求
     *
     * @return 处理结果，200 成功，400 失败 401 权限不足
     */
    @ApiOperation("查询所有未处理的提现请求")
    @Secured("ROLE_ADMIN_1")
    @GetMapping("/withdrawDeposit/viewUnHandled")
    @ApiResponses({
    @ApiResponse(code = 200, message = "{\n" +
            "    \"code\": 0,\n" +
            "    \"msg\": \"请求成功\",\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"withdrawId\": 6,\n" +
            "            \"userId\": \"EEETEE\",\n" +
            "            \"isSolved\": false,\n" +
            "            \"createTime\": 1544198730000,\n" +
            "            \"phone\": 110112119,\n" +
            "            \"balance\": 0,\n" +
            "            \"schoolName\": \"西安邮电大学\",\n" +
            "            \"userName\": \"twet\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"withdrawId\": 9,\n" +
            "            \"userId\": \"EEETEE\",\n" +
            "            \"isSolved\": false,\n" +
            "            \"createTime\": 1544198730000,\n" +
            "            \"phone\": 110112119,\n" +
            "            \"balance\": 0,\n" +
            "            \"schoolName\": \"西安邮电大学\",\n" +
            "            \"userName\": \"twet\"\n" +
            "        }\n" +
            "    ]\n" +
            "}")})

    public ResponseEntity viewUnHandledWithDraw() {

        List<WithdrawRequestVO> vos = adminService.viewAllWithDrawRequestByStatus(false);

        return ResultUtil.success(vos);
    }

    /**`
     * 查询已处理的提现请求
     *
     * @return 已处理提现请求列表
     */
    @ApiOperation("查询已处理的提现请求")
    @Secured("ROLE_ADMIN_1")
    @GetMapping("/withdrawDeposit/viewHandled")
    @ApiResponses({
    @ApiResponse(code = 200, message = "{\n" +
            "    \"code\": 0,\n" +
            "    \"msg\": \"请求成功\",\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"withdrawId\": 6,\n" +
            "            \"userId\": \"EEETEE\",\n" +
            "            \"isSolved\": true,\n" +
            "            \"createTime\": 1544198730000,\n" +
            "            \"phone\": 110112119,\n" +
            "            \"balance\": 0,\n" +
            "            \"schoolName\": \"西安邮电大学\",\n" +
            "            \"userName\": \"twet\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"withdrawId\": 9,\n" +
            "            \"userId\": \"EEETEE\",\n" +
            "            \"isSolved\": true,\n" +
            "            \"createTime\": 1544198730000,\n" +
            "            \"phone\": 110112119,\n" +
            "            \"balance\": 0,\n" +
            "            \"schoolName\": \"西安邮电大学\",\n" +
            "            \"userName\": \"twet\"\n" +
            "        }\n" +
            "    ]\n" +
            "}")})
    public ResponseEntity viewHandledWithDraw() {
        return ResultUtil.success(adminService.viewAllWithDrawRequestByStatus(true));
    }

    /**
     * 将指定用户的余额清零
     *
     * @param userId
     * @return 处理结果，200 成功，400 失败 401 权限不足
     */
    @ApiOperation("将指定用户的余额清零")
    @ApiParam(name = "userId", value = "用户id")
    @Secured("ROLE_ADMIN_1")
    @GetMapping("/withdrawDeposit/withDraw/{userId}")
    public ResponseEntity withDrawAllMoney(@PathVariable String userId) {
        adminService.handleWithDrawDeposit(userId);
        return ResultUtil.success();
    }

    /**
     * 查询所有未处理的反馈信息
     *
     * @return 未处理反馈信息列表
     */
    @ApiOperation("查询所有未处理的反馈信息")
    @GetMapping("/feedback/viewUnHandled")
    @ApiResponses({
    @ApiResponse(code = 200, message = "返回示例：" +
            "{\n" +
            "    \"code\": 0,\n" +
            "    \"msg\": \"请求成功\",\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"feedbackId\": 2,\n" +
            "            \"content\": \"fwe\",\n" +
            "            \"userId\": \"1\",\n" +
            "            \"isRead\": false,\n" +
            "            \"createTime\": 1543298864000\n" +
            "            \"phone\": 110112119\n"+
            "        }\n" +
            "    ]\n" +
            "}")})
    public ResponseEntity viewAllUnHandledFeedBack() {
        return ResultUtil.success(adminService.getFeedBackByState(false));
    }

    /**
     * 查询所有已处理的反馈信息
     *
     * @return 已处理的反馈信息列表
     */
    @ApiOperation("查询所有已处理的反馈信息")
    @GetMapping("/feedback/viewHandled")
    @ApiResponses({
    @ApiResponse(code = 200, message = "返回示例：" +
            "{\n" +
            "    \"code\": 0,\n" +
            "    \"msg\": \"请求成功\",\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"feedbackId\": 1,\n" +
            "            \"content\": \"fwe\",\n" +
            "            \"userId\": \"1\",\n" +
            "            \"isRead\": true,\n" +
            "            \"createTime\": 1543298792000\n" +
            "            \"phone\": 110112119\n"+
            "        }\n" +
            "    ]\n" +
            "}")})
    public ResponseEntity viewAllHandledFeedBack() {
        return ResultUtil.success(adminService.getFeedBackByState(true));
    }

    /**
     * 处理指定反馈信息
     *
     * @param id 反馈信息id
     * @return 处理结果，200 成功，400 失败 401 权限不足
     */
    @ApiOperation("处理指定反馈信息")
    @ApiParam(name = "id", value = "反馈信息id")
    @GetMapping("/feedback/handle/{id}")
    public ResponseEntity handledFeedBack(@PathVariable int id) {
        adminService.handleFeedBack(id);
        return ResultUtil.success();
    }

    /**
     * 删除二级管理员
     *
     * @param id 被删二级管理员id
     * @return 处理结果，200 成功，400 失败 401 权限不足
     */
    @ApiOperation("删除二级管理员")
    @ApiParam(name = "id", value = "被删二级管理员id")
    @Secured("ROLE_ADMIN_1")
    @GetMapping("/delete/{id}")
    public ResponseEntity deleteAdmin(@PathVariable int id) {
        adminService.deleteSecondAdmin(id);
        return ResultUtil.success();
    }

    /**
     * 将二级管理员设为地区负责人
     *
     * @param id 二级管理员id
     * @return 处理结果，200 成功，400 失败 401 权限不足
     */
    @ApiOperation("将二级管理员设为地区负责人")
    @ApiParam(name = "id", value = "二级管理员id")
    @Secured("ROLE_ADMIN_1")
    @GetMapping("/modify/setBoss/{id}")
    public ResponseEntity setBoss(@PathVariable int id) {
        adminService.modifyIsBossAttribute(id, true);
        return ResultUtil.success();
    }

    /**
     * 更改二级管理员负责学校
     *
     * @param id       管理员id
     * @param schoolId 学校id
     * @return 处理结果，200 成功，400 失败 401 权限不足
     */
    @ApiOperation("更改二级管理员负责学校")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "管理员id"),
            @ApiImplicitParam(name = "schoolId", value = "学习id"),
    })
    @Secured("ROLE_ADMIN_1")
    @GetMapping("/modify/setSchool/{id}/{schoolId}")
    public ResponseEntity setSchoolId(@PathVariable int id, @PathVariable int schoolId) {
        adminService.modifySecondAdminSchool(id, schoolId);
        return ResultUtil.success();
    }

    @ApiOperation("查询所有管理员信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "{\n" +
                    "    \"code\": 0,\n" +
                    "    \"msg\": \"请求成功\",\n" +
                    "    \"data\": [\n" +
                    "        {\n" +
                    "            \"adminId\": -2140936994,\n" +
                    "            \"adminPhone\": 12434454,\n" +
                    "            \"adminName\": \"GZHXMV\",\n" +
                    "            \"adminPass\": \"$2a$10$SuUZpzqDXK8Q6P8AbMFv7u8oKRVVEpUt/50aD6xnXMugH/9OL2.3i\",\n" +
                    "            \"adminRole\": \"ROLE_ADMIN_2\",\n" +
                    "            \"isBoss\": false,\n" +
                    "            \"adminSchoolId\": 2\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"adminId\": -2116578769,\n" +
                    "            \"adminPhone\": 12434454,\n" +
                    "            \"adminName\": \"HgShrm\",\n" +
                    "            \"adminPass\": \"$2a$10$ypNhx/o4/cZ4eMjHZT84GOtmsY5MeNrBtFNfDb5wCpjkFWiblukRy\",\n" +
                    "            \"adminRole\": \"ROLE_ADMIN_2\",\n" +
                    "            \"isBoss\": false,\n" +
                    "            \"adminSchoolId\": 2\n" +
                    "        }]}")})
    @Secured("ROLE_ADMIN_1")
    @GetMapping("/list")
    public ResponseEntity getAdminList() {
        return ResultUtil.success(adminService.getAllAdminInfo());
    }

    /**
     * 通过关键字模糊匹配学校列表, 如果关键字为空则返回所有学校
     */
    @GetMapping("/school")
    public ResponseEntity getSchool(String key) {
        if (key == null) return ResultUtil.success(schoolService.getSchoolInFuzzyMatching("%"));
        return ResultUtil.success(schoolService.getSchoolInFuzzyMatching(key));
    }

}