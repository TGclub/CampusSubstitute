package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.enums.Role;

/**
 * Created by Kikyou on 18-11-12
 */
public interface AdminService {

    void allocatePrivilege(String userId, Role role);
}
