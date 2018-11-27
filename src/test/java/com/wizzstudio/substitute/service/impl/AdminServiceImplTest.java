package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.domain.AdminInfo;
import com.wizzstudio.substitute.domain.CountInfo;
import com.wizzstudio.substitute.domain.CouponInfo;
import com.wizzstudio.substitute.enums.Role;
import com.wizzstudio.substitute.service.AdminService;
import com.wizzstudio.substitute.util.RandomUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * Created by Kikyou on 18-11-25
 */
@SpringBootTest
@Transactional
@Rollback(false)
@RunWith(SpringJUnit4ClassRunner.class)
public class AdminServiceImplTest {

    @Autowired
    private AdminService adminService;

    @Test
    public void allocatePrivilege() {
    }

    @Test
    public void getAdminInfo() {
        adminService.getAdminInfo(123);
    }

    public AdminServiceImplTest() {
    }

    @Test
    public void isValidAdmin() {
    }
    @Test
    @Rollback(false)
    public void addNewAdmin() {
        for (int i = 0; i < 20; i++) {
            AdminInfo.Builder builder = AdminInfo.newBuilder()
                    .setAdminName("test")
                    .setAdminPass("absddafkja")
                    .setAdminPhone(1232434454L)
                    .setAdminRole(Role.ROLE_ADMIN_2)
                    .setAdminSchoolId(2)
                    .setAdminId(new Random().nextInt());
            if (i % 3 ==0)
                builder.setIsBoss(true);
            AdminInfo info = builder.build();
            adminService.addNewAdmin(info);
        }
    }

    @Test
    public void getUnPickedIndent() {
        adminService.getUnPickedIndent();
    }

    @Test
    public void getUnHandledUrgentIndents() {
        adminService.getUnHandledUrgentIndents();
    }

    @Test
    public void getHandledUrgentIndents() {
        adminService.getHandledUrgentIndents();
    }

    @Test
    public void getAllCoupon() {
        adminService.getAllCoupon();
    }


    @Test
    public void deleteCoupon() {
        adminService.deleteCoupon(1);
    }

    @Test
    public void getUnHandledFeedBack() {
        adminService.getUnHandledFeedBack();
    }

    @Test
    public void getHandledFeedBack() {
        adminService.getHandledFeedBack();
    }

    @Test
    public void handleFeedBack() {
        adminService.handleFeedBack(1);
    }

    @Test
    public void handleWithDrawDeposit() {
     //   adminService.handleWithDrawDeposit();
    }

    @Test
    public void getAllSecondAdmin() {
        adminService.getAllSecondAdmin();
    }

    @Test
    @Rollback(false)
    public void modifySecondAdminSchool() {
        adminService.modifySecondAdminSchool(1, 3);
    }

    @Test
    public void modifyIsBossAttribute() {
        adminService.modifyIsBossAttribute(1, true);
    }

    @Test
    public void deleteSecondAdmin() {
        adminService.deleteSecondAdmin(2);
    }

    @Test
    public void getConcreteCountInfo() {
        List<CountInfo> infoList = adminService.getConcreteCountInfo(3,9);
        System.out.println("egsdgse");
        infoList.stream().map(x -> x.getLoginUser()).forEach(System.out::println);
    }

    @Test
    public void handleUrgent() {
        adminService.handleUrgent(3);
    }

    @Test
    public void viewAllWithDrawRequestByStatus() {
        adminService.viewAllWithDrawRequestByStatus(true);
    }
}