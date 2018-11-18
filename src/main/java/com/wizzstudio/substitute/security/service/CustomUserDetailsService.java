package com.wizzstudio.substitute.security.service;

import com.wizzstudio.substitute.domain.AdminInfo;
import com.wizzstudio.substitute.domain.User;
import com.wizzstudio.substitute.security.CustomUserDetails;
import com.wizzstudio.substitute.service.AdminService;
import com.wizzstudio.substitute.service.UserService;
import com.wizzstudio.substitute.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Kikyou on 18-11-12
 */
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserService service;

    @Override
    @SuppressWarnings("ConstantConditions")
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = service.findUserById(userId);
        return CustomUserDetails.create(user);

    }




}
