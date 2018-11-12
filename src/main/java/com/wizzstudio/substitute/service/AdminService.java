package com.wizzstudio.substitute.service;

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
    void allocatePrivilege(String userId, Role role);
}
