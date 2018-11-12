package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.UserDao;
import com.wizzstudio.substitute.enums.Role;
import com.wizzstudio.substitute.pojo.User;
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
    private UserDao userDao;
    @Override
    public void allocatePrivilege(String userId, Role role) {
        User user = userDao.findUserById(userId);
        if (!role.equals(Role.ROLE_ADMIN_1))
        user.setRole(role);
        userDao.save(user);
    }
}
