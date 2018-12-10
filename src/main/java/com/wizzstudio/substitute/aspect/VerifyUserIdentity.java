package com.wizzstudio.substitute.aspect;

import com.wizzstudio.substitute.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.file.AccessDeniedException;

/**
 * Created by Kikyou on 18-12-4
 */
@Configuration
@Aspect
@Slf4j
public class VerifyUserIdentity {
//    @Pointcut("execution( public * com.wizzstudio.substitute.controller.UserController.*(..))")
//    public void match() {
//    }
//
//    @Before("match()")
//    public void check(JoinPoint point) throws AccessDeniedException {
//        String userId = ((CustomUserDetails)(SecurityContextHolder.getContext()
//                .getAuthentication().getPrincipal())).getUsername();
//        if (!((String)point.getArgs()[0]).equals(userId)) {
//            log.info((String) point.getArgs()[0]);
//            throw new AccessDeniedException("Access Denied");
//        }
//        log.info("passed check: "+userId);
//    }

}
