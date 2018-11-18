package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.domain.AdminInfo;
import com.wizzstudio.substitute.dto.AdminLoginDTO;
import com.wizzstudio.substitute.enums.Role;

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
}
