package com.drivingschool.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("adminRole");

        if (role == null) return true;          // not logged in
        if ("SuperAdmin".equals(role)) return true; // full access

        // "Admin" role: allow only GET requests that are NOT /new, /edit, /delete
        String method = request.getMethod();
        String uri = request.getRequestURI();

        boolean isWriteOperation = !"GET".equalsIgnoreCase(method) ||
                uri.contains("/new") ||
                uri.contains("/edit") ||
                uri.contains("/delete");

        if (isWriteOperation) {
            // Redirect back to the list page with an error parameter
            String referer = request.getHeader("Referer");
            if (referer == null || referer.isEmpty()) {
                referer = request.getContextPath() + "/students"; // fallback
            }
            String redirectUrl = referer + (referer.contains("?") ? "&" : "?") +
                    "error=Access Denied: Admins cannot add, edit, or delete.";
            response.sendRedirect(redirectUrl);
            return false;
        }
        return true;
    }
}