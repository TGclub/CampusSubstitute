package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.domain.AdminInfo;
import com.wizzstudio.substitute.enums.Role;
import com.wizzstudio.substitute.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;


/**
 * Created by Kikyou on 18-11-25
 */
@SpringBootTest
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
class AdminServiceImplTest {

    @Autowired
    private AdminService adminService;

    @Test
    void allocatePrivilege() {
    }

    @Test
    void getAdminInfo() {
    }

    @Test
    void isValidAdmin() {
    }
    @org.junit.jupiter.api.Test
    @Rollback(false)
    void addNewAdmin() {
        for (int i = 0; i < 20; i++) {
            AdminInfo.Builder builder = AdminInfo.newBuilder()
                    .setAdminName("test")
                    .setAdminPass("absdafa")
                    .setAdminPhone(1232434454L)
                    .setAdminRole(Role.ROLE_ADMIN_2)
                    .setAdminSchoolId(2);
            if (i % 3 ==0)
                builder.setIsBoss(true);
            AdminInfo info = builder.build();
            adminService.addNewAdmin(info);
        }
    }

    @Test
    void getUnPickedIndent() {
    }

    @Test
    void getUnHandledUrgentIndents() {
    }

    @Test
    void getHandledUrgentIndents() {
    }

    @Test
    void getAllCoupon() {
    }

    @Test
    void addNewCoupon() {
    }

    @Test
    void deleteCoupon() {
    }

    @Test
    void getUnHandledFeedBack() {
    }

    @Test
    void getHandledFeedBack() {
    }

    @Test
    void handleFeedBack() {
    }

    @Test
    void handleWithDrawDeposit() {
    }

    @Test
    void getAllSecondAdmin() {
    }

    @Test
    void modifySecondAdminSchool() {
    }

    @Test
    void modifyIsBossAttribute() {
    }

    @Test
    void deleteSecondAdmin() {
    }

    @Test
    void getConcreteCountInfo() {
    }

    @Test
    void handleUrgent() {
    }

    @Test
    void viewAllWithDrawRequestByStatus() {
    }
}