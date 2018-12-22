package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.VO.FeedbackVO;
import com.wizzstudio.substitute.VO.UnPickedIndentVO;
import com.wizzstudio.substitute.VO.UrgentIndentVO;
import com.wizzstudio.substitute.VO.WithdrawRequestVO;
import com.wizzstudio.substitute.dao.*;
import com.wizzstudio.substitute.domain.*;
import com.wizzstudio.substitute.dto.AdminLoginDTO;
import com.wizzstudio.substitute.dto.AdminCouponDTO;
import com.wizzstudio.substitute.enums.Role;
import com.wizzstudio.substitute.enums.indent.IndentStateEnum;
import com.wizzstudio.substitute.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    private SchoolDao schoolDao;

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
        log.info("name: " + loginDTO.getAdminName() + "," + "passwd " + loginDTO.getPassword());
        AdminInfo admin = adminDao.getAdminInfoByAdminName(loginDTO.getAdminName());
        log.info(admin.getAdminName() + " in database " + admin.getAdminPass());
        return encoder.matches(loginDTO.getPassword(), admin.getAdminPass());
        //return admin.getAdminPass().equals(loginDTO.getPassword());
    }

    @Override
    public void addNewAdmin(AdminInfo info) {
        info.setAdminPass(encoder.encode(info.getAdminPass()));
        adminDao.save(info);
    }

    @Override
    public List<UnPickedIndentVO> getUnPickedIndent() {
        List<Indent> indents = indentDao.findAllByIndentStateOrderByCreateTimeDesc(IndentStateEnum.WAIT_FOR_PERFORMER);
        List<UnPickedIndentVO> vos = new ArrayList<>();
        indents.forEach(x -> {
            UnPickedIndentVO vo = new UnPickedIndentVO();
            BeanUtils.copyProperties(x, vo);
            User user = userDao.findUserById(x.getPublisherId());
            if (user != null) {
                vo.setSchoolId(user.getSchoolId());
            }
            vos.add(vo);
        });
        return vos;
    }

    @Override
    public CouponInfo getSpecificCoupon(int id) {
        return couponInfoDao.findByCouponId(id);
    }

    public AdminServiceImpl() {
        super();
    }

    @Override
    public List<UrgentIndentVO> getUrgentIndentsByHandledState(Boolean isHandled, Integer schoolId, String adminName) {
        AdminInfo adminInfo = adminDao.getAdminInfoByAdminName(adminName);
        if (adminInfo.getAdminRole().equals(Role.ROLE_ADMIN_2)) {
            if (!adminInfo.getAdminSchoolId().equals(schoolId)) {
                throw new AccessDeniedException("Access Denied");
            }
        }
        List<Indent> indents = indentDao.findAllByIsSolvedAndUrgentTypeGreaterThanOrderByCreateTimeDesc(isHandled, 0);
        List<UrgentIndentVO> vos = new ArrayList<>();
        indents.forEach(x -> {
            UrgentIndentVO urgentIndentVO = new UrgentIndentVO();
            BeanUtils.copyProperties(x, urgentIndentVO);
            User user = userDao.findUserById(x.getPublisherId());
            if (user != null) {
                urgentIndentVO.setTrueName(user.getTrueName());
                urgentIndentVO.setUserName(user.getUserName());
                urgentIndentVO.setPhone(user.getPhone());
                School school = schoolDao.findSchoolById(user.getSchoolId());
                if (school != null && school.getSchoolName() != null)
                    urgentIndentVO.setSchool(school.getSchoolName());
                if (schoolId != null) {
                    if (user.getSchoolId().equals(schoolId)) {
                        vos.add(urgentIndentVO);
                    }
                } else {
                    vos.add(urgentIndentVO);
                }
            }
        });
        return vos;
    }

    @Override
    public List<CouponInfo> getAllCoupon() {
        return couponInfoDao.findAll();
    }

    @Override
    public void addNewCoupon(AdminCouponDTO newCoupon) throws IOException {
        CouponInfo coupon = new CouponInfo();
        coupon.setIsDeleted(false);
        coupon.setReducePrice(newCoupon.getReducePrice());
        coupon.setValidTime(new Date(newCoupon.getValidTime()));
        coupon.setInvalidTime(new Date(newCoupon.getInvalidTime()));
        coupon.setLeastPrice(newCoupon.getLeastPrice());
        coupon.setPicture(newCoupon.getFile().getBytes());
        couponInfoDao.save(coupon);
    }

    @Override
    public void deleteCoupon(int couponId) {
        couponInfoDao.deleteById(couponId);
    }

    @Override
    public List<FeedbackVO> getFeedBackByState(boolean status) {
        List<Feedback> feedbacks = feedbackDao.findByIsRead(status);
        List<FeedbackVO> vos = new ArrayList<>();
        feedbacks.forEach(x -> {
            FeedbackVO vo = new FeedbackVO();
            BeanUtils.copyProperties(x, vo);
            User user = userDao.findUserById(x.getUserId());
            if (user != null) {
                vo.setPhone(user.getPhone());
            }
            vos.add(vo);
        });
        return vos;
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
        withdrawRequests.forEach(r -> {
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
        if (schoolDao.findSchoolById(schoolId) != null) {
            adminInfo.setAdminSchoolId(schoolId);
            adminDao.save(adminInfo);
        }
    }

    @Override
    public void modifyIsBossAttribute(int secondAdminId, boolean isBoss) {
        AdminInfo adminInfo = adminDao.getAdminInfoByAdminId(secondAdminId);
        adminInfo.setIsBoss(isBoss);
        adminDao.save(adminInfo);
    }

    @Override
    public List<AdminInfo> findAllBossBySchoolId(int schoolId) {
        return adminDao.findByAdminSchoolIdAndIsBoss(schoolId, true);
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
    public List<WithdrawRequestVO> viewAllWithDrawRequestByStatus(boolean status) {
        List<WithdrawRequest> withdrawRequests = withdrawRequestDao.findAllByIsSolved(status);
        List<WithdrawRequestVO> vos = new ArrayList<>();
        withdrawRequests.forEach(x -> {
            WithdrawRequestVO withdrawRequest = new WithdrawRequestVO();
            BeanUtils.copyProperties(x, withdrawRequest);
            User user = userDao.findUserById(x.getUserId());
            if (user != null) {
                School school = schoolDao.findSchoolById(user.getSchoolId());
                BeanUtils.copyProperties(user, withdrawRequest);
                withdrawRequest.setSchoolName(school.getSchoolName());
            }
            vos.add(withdrawRequest);
        });
        return vos;
    }

    @Override
    public List<AdminInfo> getAllAdminInfo() {
        return adminDao.findAll();
    }

    @Override
    public List<AdminInfo> getAllAdminInfoBySchoolIdAndRole(Integer schoolId,Role role) {
        return adminDao.findByAdminSchoolIdAndAdminRoleIs(schoolId,role);
    }

    @Override
    public List<CountInfo> getConcreteCountInfoBySchoolId(Integer schoolId, Integer from, Integer to, String adminName) {
        if (adminName == null) return null;
        AdminInfo adminInfo = adminDao.getAdminInfoByAdminName(adminName);
        if (adminInfo == null) return null;
        Role role = adminInfo.getAdminRole();
        List<CountInfo> countInfos;
        switch (role) {
            case ROLE_ADMIN_1:
                countInfos = countInfoDao.getAllByCountDateBetweenAndSchoolId(from, to, schoolId);
                break;
            case ROLE_ADMIN_2:
                if (schoolId.equals(adminInfo.getAdminSchoolId()))
                    countInfos = countInfoDao.getAllByCountDateBetweenAndSchoolId(from,to,schoolId);
                else throw new AccessDeniedException("AccessDenied");
                break;
            default:
                throw new AccessDeniedException("AccessDenied");
        }
        return  countInfos;
    }
}
