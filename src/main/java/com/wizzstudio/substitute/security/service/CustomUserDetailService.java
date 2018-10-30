package com.wizzstudio.substitute.security.service;

import com.wizzstudio.substitute.constants.Constants;
import com.wizzstudio.substitute.pojo.User;
import com.wizzstudio.substitute.security.CustomUserDetail;
import com.wizzstudio.substitute.service.UserService;
import com.wizzstudio.substitute.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserService service;

    @Override
    @SuppressWarnings("ConstantConditions")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Cookie[] cookies = request.getCookies();
        Cookie cookie = Arrays.stream(cookies).filter(c -> c.getName().equals(Constants.TOKEN)).collect(Collectors.toList()).get(0);
        String userId = redisUtil.getCachedUserId(cookie.getValue());
        User user = service.findUserById(userId);
        return CustomUserDetail.create(user);

    }
}
