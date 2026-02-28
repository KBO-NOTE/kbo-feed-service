package com.kbonote.kbofeedservice.common.auth;

import com.kbonote.kbofeedservice.common.exception.UnauthorizedException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUserParam.class)
                && parameter.getParameterType().equals(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        Object userIdAttr = webRequest.getAttribute(GatewayAuthInterceptor.ATTR_USER_ID, NativeWebRequest.SCOPE_REQUEST);
        Object userRoleAttr = webRequest.getAttribute(GatewayAuthInterceptor.ATTR_USER_ROLE, NativeWebRequest.SCOPE_REQUEST);

        if (!(userIdAttr instanceof Long userId) || !(userRoleAttr instanceof String userRole)) {
            throw new UnauthorizedException("인증이 필요합니다.");
        }
        return new CurrentUser(userId, userRole);
    }
}
