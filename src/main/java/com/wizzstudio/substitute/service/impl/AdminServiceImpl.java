package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.*;
import com.wizzstudio.substitute.domain.*;
import com.wizzstudio.substitute.dto.AdminLoginDTO;
import com.wizzstudio.substitute.dto.CouponDTO;
import com.wizzstudio.substitute.enums.Role;
import com.wizzstudio.substitute.enums.indent.IndentStateEnum;
import com.wizzstudio.substitute.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Kikyou on 18-11-12
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private CouponInfoDao couponInfoDao;

    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private IndentDao indentDao;

    @Autowired
    private CountInfoDao countInfoDao;

    @Autowired
    private WithdrawRequestDao withdrawRequestDao;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void allocatePrivilege(Integer id, Role role) {
        AdminInfo adminInfo = adminDao.getAdminInfoByAdminId(id);
        adminInfo.setAdminRole(role);
        adminDao.save(adminInfo);
    }

    @Override
    public AdminInfo getAdminInfo(Integer adminId) {
        return adminDao.getAdminInfoByAdminId(adminId);
    }

    @Override
    public boolean isValidAdmin(AdminLoginDTO loginDTO) {
        log.info("name: " + loginDTO.getAdminName() + "," + "passwd" + loginDTO.getPassword());
        AdminInfo admin = adminDao.getAdminInfoByAdminName(loginDTO.getAdminName());
        log.info(admin.getAdminName() + " in database" + admin.getAdminPass());
        if (!admin.getAdminPass().equals(encoder.encode(loginDTO.getPassword()))) return false;
        return true;
    }

    @Override
    public void addNewAdmin(AdminInfo info) {
        info.setAdminPass(encoder.encode(info.getAdminPass()));
        adminDao.save(info);
    }

    @Override
    public List<Indent> getUnPickedIndent() {
        return indentDao.findAllByIndentStateOrderByCreateTimeDesc(IndentStateEnum.WAIT_FOR_PERFORMER);
    }

    @Override
    public CouponInfo getSpecificCoupon(int id) {
        return couponInfoDao.findByCouponId(id);
    }

    public AdminServiceImpl() {
        super();
    }

    @Override
    public List<Indent> getUnHandledUrgentIndents() {
        return indentDao.findAllByIsSolvedAndUrgentTypeGreaterThanOrderByCreateTimeDesc(false, 0);
    }

    @Override
    public List<Indent> getHandledUrgentIndents() {
        return indentDao.findAllByIsSolvedAndUrgentTypeGreaterThanOrderByCreateTimeDesc(true, 0);
    }

    @Override
    public List<CouponInfo> getAllCoupon() {
        return couponInfoDao.findAll();
    }

    @Override
    public void addNewCoupon(CouponDTO newCoupon) throws IOException {
        CouponInfo coupon = new CouponInfo();
        coupon.setIsDeleted(false);
        coupon.setReducePrice(newCoupon.getReducePrice());
        coupon.setValidTime(new Date(newCoupon.getValidTime()));
        coupon.setInvalidTime(new Date(newCoupon.getInvalidTime()));
        coupon.setLeastPrice(newCoupon.getLeastPrice());
        coupon.setPicture(newCoupon.getPicture().getBytes());
        couponInfoDao.save(coupon);
    }

    @Override
    public void deleteCoupon(int couponId) {
        couponInfoDao.deleteById(couponId);
    }

    @Override
    public List<Feedback> getUnHandledFeedBack() {
        return feedbackDao.findByIsRead(false);
    }

    @Override
    public List<Feedback> getHandledFeedBack() {
        return feedbackDao.findByIsRead(true);
    }

    @Override
    public void handleFeedBack(int id) {
        Feedback feedback = feedbackDao.findByFeedbackId(id);
        feedback.setIsRead(true);
        feedbackDao.save(feedback);
    }

    /**
     * 处理提现请求，按照一次提现全部提出处理。
     *
     * @param id 用户id
     */
    @Override
    public void handleWithDrawDeposit(String id) {
        User user = userDao.findUserById(id);
        user.setBalance(new BigDecimal(0));
        userDao.save(user);
        List<WithdrawRequest> withdrawRequests = withdrawRequestDao.findAllByUserId(id);
        withdrawRequests.stream().forEach(r -> {
            r.setIsSolved(true);
            withdrawRequestDao.save(r);
        });
    }

    @Override
    public List<AdminInfo> getAllSecondAdmin() {
        return adminDao.getAdminInfoByAdminRole(Role.ROLE_ADMIN_2);
    }

    @Override
    public void modifySecondAdminSchool(int adminId, int schoolId) {
        AdminInfo adminInfo = adminDao.getAdminInfoByAdminId(adminId);
        adminInfo.setAdminSchoolId(schoolId);
        adminDao.save(adminInfo);
    }

    @Override
    public void modifyIsBossAttribute(int secondAdminId, boolean isBoss) {
        AdminInfo adminInfo = adminDao.getAdminInfoByAdminId(secondAdminId);
        adminInfo.setIsBoss(isBoss);
        adminDao.save(adminInfo);
    }

    @Override
    public List<AdminInfo> findAllBossBySchoolId(int schoolId) {
        return adminDao.findByAdminSchoolIdAndIsBoss(schoolId,true);
    }

    @Override
    public void deleteSecondAdmin(int adminId) {
        adminDao.deleteById(adminId);
    }

    @Override
    public List<CountInfo> getConcreteCountInfo(Integer from, Integer to) {
        return countInfoDao.getAllByCountDateBetweenOrderByCountDate(from, to);
    }

    @Override
    public void handleUrgent(int id) {
        Indent indent = indentDao.findByIndentId(id);
        indent.setIsSolved(true);
        indentDao.save(indent);
    }

    @Override
    public List<WithdrawRequest> viewAllWithDrawRequestByStatus(boolean status) {
        return withdrawRequestDao.findAllByIsSolved(status);
    }
}
