package com.drivingschool.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 1. Admin login interceptor (protects all admin/management pages)
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/students/**", "/instructors/**", "/vehicles/**",
                        "/schedules/**", "/packages/**", "/payments/**", "/admins/**", "/dashboard")
                .excludePathPatterns("/login", "/css/**", "/js/**")
                .order(1);

        // 2. Admin role-based interceptor (blocks Manager from /admins)
        registry.addInterceptor(new AdminAuthorizationInterceptor())
                .addPathPatterns("/admins/**")
                .order(2);

        // 3. Manager read-only interceptor (optional – blocks write operations)
        registry.addInterceptor(new AuthorizationInterceptor())
                .addPathPatterns("/students/**", "/instructors/**", "/vehicles/**",
                        "/schedules/**", "/packages/**", "/payments/**")
                .order(3);
    }
}