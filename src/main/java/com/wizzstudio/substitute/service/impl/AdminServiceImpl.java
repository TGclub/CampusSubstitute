package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.AdminDao;
import com.wizzstudio.substitute.dao.IndentDao;
import com.wizzstudio.substitute.domain.AdminInfo;
import com.wizzstudio.substitute.domain.Indent;
import com.wizzstudio.substitute.dto.AdminLoginDTO;
import com.wizzstudio.substitute.enums.IndentStateEnum;
import com.wizzstudio.substitute.enums.Role;
import com.wizzstudio.substitute.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Kikyou on 18-11-12
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private IndentDao indentDao;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void allocatePrivilege(Integer id, Role role) {
        AdminInfo adminInfo = adminDao.getAdminInfoByAdminId(id);
        adminInfo.setAdminRole(role);
        adminDao.save(adminInfo);
    }

    @Override
    public void createNewAdmin(AdminInfo adminInfo) {
        adminInfo.setAdminPass(encoder.encode(adminInfo.getAdminPass()));
        adminDao.save(adminInfo);
    }

    @Override
    public AdminInfo getAdminInfo(Integer adminId) {
        return adminDao.getAdminInfoByAdminId(adminId);
    }

    @Override
    public boolean isValidAdmin(AdminLoginDTO loginDTO) {
        AdminInfo admin = adminDao.getAdminInfoByAdminName(loginDTO.getAdminName());
        if (admin == null) return false;
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
}
