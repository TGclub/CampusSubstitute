package com.wizzstudio.substitute.security;


import com.wizzstudio.substitute.security.service.CustomAdminDetailsService;
import com.wizzstudio.substitute.util.CookieUtil;
import com.wizzstudio.substitute.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kikyou on 18-11-12
 */
public class AuthenticationFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private UserDetailsService userDetailsService;

    private CustomAdminDetailsService adminDetailsService;

    private RedisUtil util;

    public AuthenticationFilter(UserDetailsService userDetailsService, CustomAdminDetailsService adminDetailsService, RedisUtil util) {
        this.userDetailsService = userDetailsService;
        this.util = util;
        this.adminDetailsService = adminDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String uri = request.getRequestURI().toString();
        log.info("processing authentication for '{}'", uri);
        Cookie cookie = CookieUtil.getCookie(request);
        if (cookie != null) {
            String key = cookie.getValue();
            log.info(key);
            if (key != null) {
                String value = util.getCachedUserId(key);
                if (value != null) {
                    UserDetails userDetails;
                    if (uri.startsWith("/admin")) {
                        userDetails = adminDetailsService.loadUserByUsername(value);
                        System.out.println("admin uri");
                    } else {
                        userDetails = userDetailsService.loadUserByUsername(value);
                    }
                    if (userDetails != null) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                                null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        log.info("authorized user '{}', setting security context", userDetails.getUsername());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
