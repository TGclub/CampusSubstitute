package com.wizzstudio.substitute.controller;

import com.wizzstudio.substitute.domain.AdminInfo;
import com.wizzstudio.substitute.enums.Role;
import com.wizzstudio.substitute.service.AdminService;
import com.wizzstudio.substitute.util.RandomUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.Random;

/**
 * Created by Kikyou on 18-11-25
 */
@SpringBootTest
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class AdminServiceImplTest {

    @Autowired
    private AdminService adminService;

    @Test
   public void allocatePrivilege() {
    }

    @Test
   public void getAdminInfo() {
    }

    @Test
   public void isValidAdmin() {
    }
    @Test
    @Rollback(false)
   public void addNewAdmin() {
        for (int i = 0; i < 20; i++) {
            AdminInfo.Builder builder = AdminInfo.newBuilder()
                    .setAdminId(new Random().nextInt())
                    .setAdminName(RandomUtil.getRandomString(6))
                    .setAdminPhone(12434454L)
                    .setIsBoss(false)
                    .setAdminRole(Role.ROLE_ADMIN_2)
                    .setAdminSchoolId(2);
            if (i % 3 ==0)
                builder.setIsBoss(true);
            AdminInfo info = builder.build();
            info.setAdminPass("testestr");
            adminService.addNewAdmin(info);
        }
    }

    @Test
   public void getUnPickedIndent() {
    }

    @Test
   public void getUnHandledUrgentIndents() {
    }

    @Test
   public void getHandledUrgentIndents() {
    }

    @Test
   public void getAllCoupon() {
    }

    @Test
   public void addNewCoupon() {
    }

    @Test
   public void deleteCoupon() {
    }

    @Test
   public void getUnHandledFeedBack() {
    }

    @Test
   public void getHandledFeedBack() {
    }

    @Test
   public void handleFeedBack() {
    }

    @Test
   public void handleWithDrawDeposit() {
    }

    @Test
   public void getAllSecondAdmin() {
    }

    @Test
   public void modifySecondAdminSchool() {
    }

    @Test
   public void modifyIsBossAttribute() {
    }

    @Test
   public void deleteSecondAdmin() {
    }

    @Test
   public void getConcreteCountInfo() {
    }

    @Test
   public void handleUrgent() {
    }

    @Test
   public void viewAllWithDrawRequestByStatus() {
    }
}
