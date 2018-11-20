package com.wizzstudio.substitute.controller;

import com.wizzstudio.substitute.domain.AdminInfo;
import com.wizzstudio.substitute.enums.Role;
import com.wizzstudio.substitute.service.AdminService;
import com.wizzstudio.substitute.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by Kikyou on 18-11-12
 */
@RestController
@RequestMapping("/admin")
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
/*
    @Secured("ROLE_ADMIN_1")
    @PostMapping("/test")
    public String test() {
        return "Hello world";
    }*/

    @Secured("ROLE_ADMIN_1")
    @PostMapping("/create")
    public ResponseEntity createNewAdministrator(@RequestBody @Valid AdminInfo info, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return ResultUtil.error();
        adminService.addNewAdmin(info);
        return ResultUtil.success();
    }


    @Secured("ROLE_ADMIN_2")
    @GetMapping("/viewUnPicked")
    public ResponseEntity viewUnPickedIndent(Principal principal) {
        return ResultUtil.success(adminService.getUnPickedIndent());
    }




}
