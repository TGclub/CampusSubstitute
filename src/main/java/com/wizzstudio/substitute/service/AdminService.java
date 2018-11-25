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



    /**
     * 检验是否是合法的登录请求
     * @param loginDTO 登录信息
     * @return
     */
    boolean isValidAdmin(AdminLoginDTO loginDTO);

    /**
     * 获取用户基本信息
     * @param admin 用于保存的信息
     * @return
     */
    void addNewAdmin(AdminInfo admin);

    /**
     * 获取未被接单的订单
     * @return 订单信息
     */
    List<Indent> getUnPickedIndent();

    /**
     * 获取未处理的紧急订单
     * @return 订单列表
     */
    List<Indent> getUnHandledUrgentIndents();

    /**
     * 获取已处理的紧急订单
     * @return 订单列表
     */
    List<Indent> getHandledUrgentIndents();

    /**
     * 获取所有的优惠券信息
     * @return 所有的优惠券
     */
    List<CouponInfo> getAllCoupon();

    /**
     * 将紧急订单设置为已处理
     * @param id 订单id
     */
    void handleUrgent(int id);

    /**
     * 添加新的优惠券
     * @param coupon 优惠券信息
     */
    void addNewCoupon(CouponInfo coupon);

    /**
     * 删除优惠券
     * @param couponId
     */
    void deleteCoupon(int couponId);

    /**
     * 获取所有未处理的意见反馈
     * @return
     */
    List<Feedback> getUnHandledFeedBack();

    /**
     * 获取所有未处理的意见反馈
     * @return
     */
    List<Feedback> getHandledFeedBack();

    /**
     * 将反馈设置为已处理
     * @param id
     */
    void handleFeedBack(int id);


    /**
     * 提现请求
     * @param userId
     */
    void handleWithDrawDeposit(String userId);

    /**
     * 获取所有的二级管理员
     * @return
     */
    List<AdminInfo> getAllSecondAdmin();

    /**
     * 修改二级管理员学校
     * @param adminId 被修改的管理员id
     * @param schoolId 学校id
     */
    void modifySecondAdminSchool(int adminId, int schoolId);

    /**
     * 将管理员设置为区域负责人
     * @param secondAdminId 二级管理员id
     * @param isBoss
     */
    void modifyIsBossAttribute(int secondAdminId, boolean isBoss);

    /**
     * 删除二级管理员
     * @param adminId 被删管理员id
     */
    void deleteSecondAdmin(int adminId);

    /**
     * 获取详细监控信息
     * @param from 起始日期
     * @param to 结束日期
     * @return 指定日期范围内的每日信息
     */
    List<CountInfo> getConcreteCountInfo(Integer from, Integer to);

    /**
     * 查看指定状态的所有提现请求
     * @param status true 已处理， false 未处理
     * @return
     */
    List<WithdrawRequest> viewAllWithDrawRequestByStatus(boolean status);


}
