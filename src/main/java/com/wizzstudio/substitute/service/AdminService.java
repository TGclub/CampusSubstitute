package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.domain.*;
import com.wizzstudio.substitute.dto.AdminLoginDTO;
import com.wizzstudio.substitute.enums.Role;

import java.util.List;

/**
 * Created by Kikyou on 18-11-12
 */
public interface AdminService {

    /**
     *
     * @param userId 被分配权限的用户id
     * @param role 用户被分配的权限
     */
    void allocatePrivilege(Integer userId, Role role);

    AdminInfo getAdminInfo(Integer adminId);

    void createNewAdmin(AdminInfo admin);

    boolean isValidAdmin(AdminLoginDTO loginDTO);

    void addNewAdmin(AdminInfo admin);

    List<Indent> getUnPickedIndent();

    List<Indent> getUnHandledUrgentIndents();

    List<Indent> getHandledUrgentIndents();

    List<CouponInfo> getAllCoupon();

    void handleUrgent(int id);
    void addNewCoupon(CouponInfo coupon);

    void deleteCoupon(int couponId);

    List<Feedback> getUnHandledFeedBack();

    List<Feedback> getHandledFeedBack();

    void handleFeedBack(int id);


    /**
     * 提现请求
     * @param userId
     */
    void handleWithDrawDeposit(String userId);

    List<AdminInfo> getAllSecondAdmin();

    void modifySecondAdminSchool(int adminId, int schoolId);

    void modifyIsBossAttribute(int secondAdminId, boolean isBoss);

    void deleteSecondAdmin(int adminId);

    List<CountInfo> getConcreteCountInfo(Integer from, Integer to);

    List<WithdrawRequest> viewAllWithDrawRequestByStatus(boolean status);


}
