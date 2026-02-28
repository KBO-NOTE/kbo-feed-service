package com.kbonote.kbofeedservice.common.auth;

import com.kbonote.kbofeedservice.common.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class GatewayAuthInterceptor implements HandlerInterceptor {

    public static final String HEADER_USER_ID = "X-User-ID";
    public static final String HEADER_USER_ROLE = "X-User-Role";
    public static final String ATTR_USER_ID = "gateway.userId";
    public static final String ATTR_USER_ROLE = "gateway.userRole";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userIdHeader = request.getHeader(HEADER_USER_ID);
        String userRoleHeader = request.getHeader(HEADER_USER_ROLE);

        if (!StringUtils.hasText(userIdHeader) || !StringUtils.hasText(userRoleHeader)) {
            throw new UnauthorizedException("인증이 필요합니다.");
        }

        Long userId;
        try {
            userId = Long.valueOf(userIdHeader);
        } catch (NumberFormatException ex) {
            throw new UnauthorizedException("인증이 필요합니다.");
        }

        request.setAttribute(ATTR_USER_ID, userId);
        request.setAttribute(ATTR_USER_ROLE, userRoleHeader);
        return true;
    }
}
