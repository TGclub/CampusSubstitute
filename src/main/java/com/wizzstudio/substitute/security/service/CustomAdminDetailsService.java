package com.wizzstudio.substitute.security.service;

import com.wizzstudio.substitute.dao.AdminDao;
import com.wizzstudio.substitute.domain.AdminInfo;
import com.wizzstudio.substitute.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Kikyou on 18-11-18
 */
@Service
public class CustomAdminDetailsService implements UserDetailsService {

    @Autowired
    private AdminDao adminDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AdminInfo adminInfo = adminDao.getAdminInfoByAdminName(username);
        if (adminInfo == null) return null;
        return CustomUserDetails.create(adminInfo);
    }
}
