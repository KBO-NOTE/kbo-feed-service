package com.kbonote.kbofeedservice.common.config;

import com.kbonote.kbofeedservice.common.auth.CurrentUserArgumentResolver;
import com.kbonote.kbofeedservice.common.auth.GatewayAuthInterceptor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final GatewayAuthInterceptor gatewayAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(gatewayAuthInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/v1/feeds/health");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CurrentUserArgumentResolver());
    }
}
