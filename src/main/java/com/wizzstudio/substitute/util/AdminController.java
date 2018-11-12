package com.wizzstudio.substitute.util;

import com.wizzstudio.substitute.enums.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Kikyou on 18-11-12
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/privilege/allocation/{userId}/{privilege}")
    @Secured("ROLE_ADMIN_1")
    public ResponseEntity allocatePrivilege(@PathVariable String userId, @PathVariable Role privilege) {
        return ResultUtil.success();
    }
}
