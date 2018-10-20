package com.wizzstudio.substitute.web.interceptor;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wizzstudio.substitute.exception.NoAuthenticationException;
import com.wizzstudio.substitute.util.JWTUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ParseJWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws NoAuthenticationException {
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return true;
        }
        String token = JWTUtil.getToken(request);

        // 前台可以没有token，后台后面还有身份认证
        if (token == null) {
            return true;
        }

        try {
            DecodedJWT decodedJWT = JWTUtil.verifyToken(token);
            Integer uid = decodedJWT.getClaim(JWTUtil.USER_ID_KEY).asInt();
            Integer role = decodedJWT.getClaim(JWTUtil.USER_ROLE).asInt();
            request.setAttribute(JWTUtil.USER_ID_KEY, uid);
            request.setAttribute(JWTUtil.USER_ROLE, role);
        } catch (SignatureVerificationException e) {
            throw new NoAuthenticationException("Token is Wrong.");
        }

        return true;
    }
}
