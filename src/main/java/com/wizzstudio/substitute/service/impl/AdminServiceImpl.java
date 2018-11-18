package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.AdminDao;
import com.wizzstudio.substitute.domain.AdminInfo;
import com.wizzstudio.substitute.enums.Role;
import com.wizzstudio.substitute.domain.User;
import com.wizzstudio.substitute.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Kikyou on 18-11-12
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;
    @Override
    public void allocatePrivilege(Integer id, Role role) {
        AdminInfo adminInfo = adminDao.getAdminInfoByAdminId(id);
        adminInfo.setAdminRole(role);
        adminDao.save(adminInfo);
    }

    @Override
    public void createNewAdmin(AdminInfo adminInfo) {
        adminDao.save(adminInfo);
    }

    @Override
    public AdminInfo getAdminInfo(Integer adminId) {
        return adminDao.getAdminInfoByAdminId(adminId);
    }
}
