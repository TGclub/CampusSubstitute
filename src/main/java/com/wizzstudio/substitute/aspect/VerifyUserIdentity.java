package com.wizzstudio.substitute.aspect;

import com.wizzstudio.substitute.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * Created by Kikyou on 18-12-4
 */
@Configuration
@Aspect
@Slf4j
public class VerifyUserIdentity {
    @Pointcut("execution( public * com.wizzstudio.substitute.controller.UserController.*(..))")
    public void match() {
    }

    @Before("match()")
    public void check(JoinPoint point) throws AccessDeniedException {
        String userId;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUserDetails) {
            userId = ((CustomUserDetails) (SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal())).getUsername();
        } else {
            throw new AccessDeniedException("Access Denied");
        }
        if (point.getArgs()[0] instanceof String)
        if (!((String)point.getArgs()[0]).equals(userId)) {
            throw new AccessDeniedException("Access Denied");
        }
        log.info("passed check: "+userId);
    }
}
