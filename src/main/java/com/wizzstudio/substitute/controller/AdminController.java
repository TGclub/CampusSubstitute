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

    @GetMapping("/privilege/allocation/{id}/{privilege}")
    @Secured("ROLE_ADMIN_1")
    public ResponseEntity allocatePrivilege(@PathVariable Integer id, @PathVariable String privilege) {
        Role role = Role.valueOf(privilege);
        adminService.allocatePrivilege(id, role);
        return ResultUtil.success();
    }

    @Secured("ROLE_ADMIN_1")
    @PostMapping("/create")
    public ResponseEntity createNewAdministrator(@RequestBody @Valid AdminInfo info, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return ResultUtil.error();
        adminService.addNewAdmin(info);
        return ResultUtil.success();
    }


    @GetMapping("statistics/viewUnPicked")
    public ResponseEntity viewUnPickedIndent(Principal principal) {
        return ResultUtil.success(adminService.getUnPickedIndent());
    }

    @Secured("ROLE_ADMIN_1")
    @GetMapping("statistics/viewConcreteInfo/{from}/{to}")
    public ResponseEntity viewConcreteInfo(@PathVariable Integer from, @PathVariable Integer to) {
        return ResultUtil.success(adminService.getConcreteCountInfo(from, to));
    }

    @Secured("ROLE_ADMIN_1")
    @GetMapping("/urgent/indent")
    public ResponseEntity viewUrgentIndent() {
        return ResultUtil.success(adminService.getUnHandledUrgentIndents());
    }

    @Secured("ROLE_ADMIN_1")
    @GetMapping("/urgent/indent/id}")
    public ResponseEntity setHandled(@PathVariable int id) {
        adminService.handleUrgent(id);
        return ResultUtil.success();
    }

    @Secured("ROLE_ADMIN_1")
    @GetMapping("/urgent/indent/handled")
    public ResponseEntity viewHandledUrgentIndent() {
        return ResultUtil.success(adminService.getHandledUrgentIndents());
    }

    @Secured("ROLE_ADMIN_1")
    @GetMapping("/coupon/viewAll")
    public ResponseEntity viewAllCoupon() {
        return ResultUtil.success(adminService.getAllCoupon());
    }

    @Secured("ROLE_ADMIN_1")
    @PostMapping("/coupon/addNew")
    public ResponseEntity newCoupon(@RequestBody CouponInfo couponInfo) {
        adminService.addNewCoupon(couponInfo);
        return ResultUtil.success();
    }

    @Secured("ROLE_ADMIN_1")
    @GetMapping("/coupon/delete/{id}")
    public ResponseEntity deleteCoupon(@PathVariable int id) {
        adminService.deleteCoupon(id);
        return ResultUtil.success();
    }

    @Secured("ROLE_ADMIN_1")
    @GetMapping("/withdrawDeposit/viewUnHandled")
    public ResponseEntity viewUnHandledWithDraw() {
        return ResultUtil.success(adminService.viewAllWithDrawRequestByStatus(false));
    }

    @Secured("ROLE_ADMIN_1")
    @GetMapping("/withdrawDeposit/viewHandled")
    public ResponseEntity viewHandledWithDraw() {
        return ResultUtil.success(adminService.viewAllWithDrawRequestByStatus(true));
    }

    @Secured("ROLE_ADMIN_1")
    @GetMapping("/withdrawDeposit/withDraw/{userId}")
    public ResponseEntity withDrawAllMoney(@PathVariable String userId) {
        adminService.handleWithDrawDeposit(userId);
        return ResultUtil.success();
    }

    @GetMapping("/feedback/viewUnHandled")
    public ResponseEntity viewAllUnHandledFeedBack() {
        return ResultUtil.success(adminService.getUnHandledFeedBack());
    }

    @GetMapping("/feedback/viewHandled")
    public ResponseEntity viewAllHandledFeedBack() {
        return ResultUtil.success(adminService.getHandledFeedBack());
    }

    @GetMapping("/feedback/handle/{id}")
    public ResponseEntity handledFeedBack(@PathVariable int id) {
        adminService.handleFeedBack(id);
        return ResultUtil.success();
    }

    @Secured("ROLE_ADMIN_1")
    @PostMapping("/create")
    public ResponseEntity createNewAdmin(@RequestBody AdminInfo adminInfo) {
        adminService.addNewAdmin(adminInfo);
        return ResultUtil.success();
    }

    @Secured("ROLE_ADMIN_1")
    @GetMapping("/delete/{id}")
    public ResponseEntity deleteAdmin(@PathVariable int id) {
        adminService.deleteSecondAdmin(id);
        return ResultUtil.success();
    }

    @Secured("ROLE_ADMIN_1")
    @GetMapping("/modify/setBoss/{id}")
    public ResponseEntity setBoss(@PathVariable int id) {
        adminService.modifyIsBossAttribute(id, true);
        return ResultUtil.success();
    }
    @Secured("ROLE_ADMIN_1")
    @GetMapping("/midify/setSchool/{id}/{schoolId}")
    public ResponseEntity setSchoolId(@PathVariable int id, @PathVariable int schooId) {
        adminService.modifySecondAdminSchool(id, schooId);
        return ResultUtil.success();
    }


}