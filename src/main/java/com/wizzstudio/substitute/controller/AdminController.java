package com.wizzstudio.substitute.controller;

import com.wizzstudio.substitute.domain.AdminInfo;
import com.wizzstudio.substitute.domain.CouponInfo;
import com.wizzstudio.substitute.enums.Role;
import com.wizzstudio.substitute.service.AdminService;
import com.wizzstudio.substitute.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by Kikyou on 18-11-12
 */
@RestController
@RequestMapping("/admin")
@Secured("ROLE_ADMIN_2")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 分配权限接口
     * @param id 被分配管理员id
     * @param privilege 被分配权限
     * @return 处理结果，200 成功，400 失败 401 权限不足
     */
    @GetMapping("/privilege/allocation/{id}/{privilege}")
    @Secured("ROLE_ADMIN_1")
    public ResponseEntity allocatePrivilege(@PathVariable Integer id, @PathVariable String privilege) {
        Role role = Role.valueOf(privilege);
        adminService.allocatePrivilege(id, role);
        return ResultUtil.success();
    }

    /**
     * 创建新的管理员
     * @param info 管理员信息
     * @param bindingResult
     * @return 处理结果，200 成功，400 失败 401 权限不足
     */
    @Secured("ROLE_ADMIN_1")
    @PostMapping("/create")
    public ResponseEntity createNewAdministrator(@RequestBody @Valid AdminInfo info, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return ResultUtil.error();
        adminService.addNewAdmin(info);
        return ResultUtil.success();
    }

    /**
     * 查询未被接单的订单列表
     * @param principal
     * @return 订单列表
     */
    @GetMapping("statistics/viewUnPicked")
    public ResponseEntity viewUnPickedIndent(Principal principal) {
        return ResultUtil.success(adminService.getUnPickedIndent());

    }

    /**
     * 查询日期范围闭区间的每日统计信息
     * @param from 起始日期
     * @param to 结束日趋
     * @return 统计信息列表
     */
    @Secured("ROLE_ADMIN_1")
    @GetMapping("statistics/viewConcreteInfo/{from}/{to}")
    public ResponseEntity viewConcreteInfo(@PathVariable Integer from, @PathVariable Integer to) {
        return ResultUtil.success(adminService.getConcreteCountInfo(from, to));
    }

    /**
     * 查询所有的紧急订单
     * @return 紧急订单列表
     */
    @Secured("ROLE_ADMIN_1")
    @GetMapping("/urgent/indent")
    public ResponseEntity viewUrgentIndent() {
        return ResultUtil.success(adminService.getUnHandledUrgentIndents());
    }

    /**
     * 处理紧急订单
     * @param id 订单id
     * @return 处理结果，200 成功，400 失败 401 权限不足
     */
    @Secured("ROLE_ADMIN_1")
    @GetMapping("/urgent/indent/id}")
    public ResponseEntity setHandled(@PathVariable int id) {
        adminService.handleUrgent(id);
        return ResultUtil.success();
    }

    /**
     * 查询已处理的紧急订单
     * @return 已处理紧急订单列表
     */
    @Secured("ROLE_ADMIN_1")
    @GetMapping("/urgent/indent/handled")
    public ResponseEntity viewHandledUrgentIndent() {
        return ResultUtil.success(adminService.getHandledUrgentIndents());
    }

    /**
     * 查询所有的优惠券信息
     * @return 优惠券列表
     */
    @Secured("ROLE_ADMIN_1")
    @GetMapping("/coupon/viewAll")
    public ResponseEntity viewAllCoupon() {
        return ResultUtil.success(adminService.getAllCoupon());
    }

    /**
     * 创建新的优惠券
     * @param couponInfo 优惠券信息
     * @return 处理结果，200 成功，400 失败 401 权限不足
     */
    @Secured("ROLE_ADMIN_1")
    @PostMapping("/coupon/addNew")
    public ResponseEntity newCoupon(@RequestBody CouponInfo couponInfo) {
        adminService.addNewCoupon(couponInfo);
        return ResultUtil.success();
    }

    /**
     * 删除指定优惠券
     * @param id 被删优惠券id
     * @return 处理结果，200 成功，400 失败 401 权限不足
     */
    @Secured("ROLE_ADMIN_1")
    @GetMapping("/coupon/delete/{id}")
    public ResponseEntity deleteCoupon(@PathVariable int id) {
        adminService.deleteCoupon(id);
        return ResultUtil.success();
    }

    /**
     * 查询未处理的提现请求
     * @return 处理结果，200 成功，400 失败 401 权限不足
     */
    @Secured("ROLE_ADMIN_1")
    @GetMapping("/withdrawDeposit/viewUnHandled")
    public ResponseEntity viewUnHandledWithDraw() {
        return ResultUtil.success(adminService.viewAllWithDrawRequestByStatus(false));
    }

    /**
     * 查询已处理的提现请求
     * @return 已处理提现请求列表
     */
    @Secured("ROLE_ADMIN_1")
    @GetMapping("/withdrawDeposit/viewHandled")
    public ResponseEntity viewHandledWithDraw() {
        return ResultUtil.success(adminService.viewAllWithDrawRequestByStatus(true));
    }

    /**
     * 将指定用户的余额清零
     * @param userId
     * @return 处理结果，200 成功，400 失败 401 权限不足
     */
    @Secured("ROLE_ADMIN_1")
    @GetMapping("/withdrawDeposit/withDraw/{userId}")
    public ResponseEntity withDrawAllMoney(@PathVariable String userId) {
        adminService.handleWithDrawDeposit(userId);
        return ResultUtil.success();
    }

    /**
     * 查询所有未处理的反馈信息
     * @return 未处理反馈信息列表
     */
    @GetMapping("/feedback/viewUnHandled")
    public ResponseEntity viewAllUnHandledFeedBack() {
        return ResultUtil.success(adminService.getUnHandledFeedBack());
    }

    /**
     * 查询所有已处理的反馈信息
     * @return 已处理的反馈信息列表
     */
    @GetMapping("/feedback/viewHandled")
    public ResponseEntity viewAllHandledFeedBack() {
        return ResultUtil.success(adminService.getHandledFeedBack());
    }

    /**
     * 处理指定反馈信息
     * @param id 反馈信息id
     * @return 处理结果，200 成功，400 失败 401 权限不足
     */
    @GetMapping("/feedback/handle/{id}")
    public ResponseEntity handledFeedBack(@PathVariable int id) {
        adminService.handleFeedBack(id);
        return ResultUtil.success();
    }

    /**
     * 删除二级管理员
     * @param id 被删二级管理员id
     * @return 处理结果，200 成功，400 失败 401 权限不足
     */
    @Secured("ROLE_ADMIN_1")
    @GetMapping("/delete/{id}")
    public ResponseEntity deleteAdmin(@PathVariable int id) {
        adminService.deleteSecondAdmin(id);
        return ResultUtil.success();
    }

    /**
     * 将二级管理员设为地区负责人
     * @param id 二级管理员id
     * @return 处理结果，200 成功，400 失败 401 权限不足
     */
    @Secured("ROLE_ADMIN_1")
    @GetMapping("/modify/setBoss/{id}")
    public ResponseEntity setBoss(@PathVariable int id) {
        adminService.modifyIsBossAttribute(id, true);
        return ResultUtil.success();
    }

    /**
     * 更改二级管理员负责学校
     * @param id 管理员id
     * @param schoolId 学习id
     * @return 处理结果，200 成功，400 失败 401 权限不足
     */
    @Secured("ROLE_ADMIN_1")
    @GetMapping("/modify/setSchool/{id}/{schoolId}")
    public ResponseEntity setSchoolId(@PathVariable int id, @PathVariable int schoolId) {
        adminService.modifySecondAdminSchool(id, schoolId);
        return ResultUtil.success();
    }


}