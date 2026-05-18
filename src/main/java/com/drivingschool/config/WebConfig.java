package com.drivingschool.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig class is used to register all custom interceptors
 * in the Spring Boot application.
 *
 * Interceptors are used for:
 * - Authentication (checking login)
 * - Authorization (checking user roles/permissions)
 * - Restricting access to certain pages
 */

@Configuration // Marks this class as a Spring configuration class
public class WebConfig implements WebMvcConfigurer {

    /**
     * This method registers all interceptors and defines
     * which URL paths they should apply to.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // =========================================================
        // 1. LOGIN INTERCEPTOR
        // =========================================================
        // Purpose:
        // Checks whether the admin/manager user is logged in.
        //
        // Applied to:
        // All management-related pages such as students,
        // instructors, vehicles, schedules, payments, etc.
        //
        // Excluded paths:
        // - /login      -> login page should be accessible
        // - /css/**     -> allow CSS files
        // - /js/**      -> allow JavaScript files
        //
        // Order:
        // Runs first before all other interceptors.
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/students/**", "/instructors/**", "/vehicles/**",
                        "/schedules/**", "/packages/**", "/payments/**", "/admins/**", "/dashboard")
                .excludePathPatterns("/login", "/css/**", "/js/**")
                .order(1);

        // =========================================================
        // 2. ADMIN AUTHORIZATION INTERCEPTOR
        // =========================================================
        // Purpose:
        // Allows only ADMIN users to access admin pages.
        //
        // Example:
        // - Admin can access /admins/**
        // - Manager cannot access /admins/**
        //
        // Order:
        // Runs after login validation.
        registry.addInterceptor(new AdminAuthorizationInterceptor())
                .addPathPatterns("/admins/**")
                .order(2);

        // =========================================================
        // 3. AUTHORIZATION INTERCEPTOR
        // =========================================================
        // Purpose:
        // Restricts managers to read-only access.
        //
        // Example:
        // - Managers can view data
        // - Managers cannot add/update/delete records
        //
        // Applied to:
        // Student, instructor, vehicle, schedule,
        // package, and payment management pages.
        //
        // Order:
        // Runs after admin authorization checks.
        registry.addInterceptor(new AuthorizationInterceptor())
                .addPathPatterns("/students/**", "/instructors/**", "/vehicles/**",
                        "/schedules/**", "/packages/**", "/payments/**")
                .order(3);

        // =========================================================
        // 4. STUDENT INTERCEPTOR
        // =========================================================
        // Purpose:
        // Checks whether a student is logged in before
        // accessing the student dashboard.
        //
        // Applied to:
        // - /student/dashboard
        //
        // Excluded paths:
        // - /student/login
        // - /student/logout
        //
        // Order:
        // Runs after all admin/manager interceptors.
        registry.addInterceptor(new StudentInterceptor())
                .addPathPatterns("/student/dashboard")
                .excludePathPatterns("/student/login", "/student/logout")
                .order(4);
    }
}