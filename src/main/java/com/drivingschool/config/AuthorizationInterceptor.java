package com.drivingschool.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * AuthorizationInterceptor controls user permissions
 * based on admin roles.
 *
 * Purpose:
 * - Allow SuperAdmin full access
 * - Restrict normal Admin users to read-only access
 * - Block add, edit, and delete operations
 */
public class AuthorizationInterceptor implements HandlerInterceptor {

    /**
     * preHandle() executes before controller methods.
     *
     * It checks:
     * - User role
     * - Request method
     * - Requested URL
     *
     * Returns:
     * - true  -> allow request
     * - false -> block request
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // =====================================================
        // GET CURRENT SESSION
        // =====================================================

        /**
         * Retrieve current HTTP session.
         */
        HttpSession session = request.getSession();

        // =====================================================
        // GET USER ROLE
        // =====================================================

        /**
         * Retrieve admin role from session.
         *
         * Example roles:
         * - SuperAdmin
         * - Admin
         */
        String role = (String) session.getAttribute("adminRole");

        // =====================================================
        // CHECK LOGIN STATUS
        // =====================================================

        /**
         * If role is null:
         * - user is not logged in
         * - allow request to continue
         *
         * LoginInterceptor usually handles authentication.
         */
        if (role == null)
            return true;          // not logged in

        // =====================================================
        // SUPER ADMIN ACCESS
        // =====================================================

        /**
         * SuperAdmin has full access to all operations.
         */
        if ("SuperAdmin".equals(role))
            return true; // full access

        // =====================================================
        // ADMIN READ-ONLY RESTRICTION
        // =====================================================

        /**
         * Admin users are restricted to read-only access.
         *
         * Allowed:
         * - GET requests for viewing pages
         *
         * Blocked:
         * - POST requests
         * - Add operations
         * - Edit operations
         * - Delete operations
         */

        // Get HTTP request method
        String method = request.getMethod();

        // Get requested URL
        String uri = request.getRequestURI();

        // =====================================================
        // CHECK WRITE OPERATIONS
        // =====================================================

        /**
         * A request is considered a write operation if:
         * - Method is NOT GET
         * OR
         * - URL contains /new
         * - URL contains /edit
         * - URL contains /delete
         */
        boolean isWriteOperation = !"GET".equalsIgnoreCase(method) ||
                uri.contains("/new") ||
                uri.contains("/edit") ||
                uri.contains("/delete");

        // =====================================================
        // BLOCK WRITE OPERATIONS
        // =====================================================
        if (isWriteOperation) {
            /**
             * Get previous page URL.
             *
             * Used to redirect user back
             * after access denial.
             */
            String referer = request.getHeader("Referer");

            // =================================================
            // FALLBACK URL
            // =================================================

            /**
             * If referer is missing:
             * redirect to student list page.
             */
            if (referer == null || referer.isEmpty()) {
                referer = request.getContextPath() + "/students"; // fallback
            }

            // =================================================
            // CREATE ERROR MESSAGE
            // =================================================

            /**
             * Add error message as URL parameter.
             */
            String redirectUrl = referer + (referer.contains("?") ? "&" : "?") +
                    "error=Access Denied: Admins cannot add, edit, or delete.";

            // Redirect user back with error message
            response.sendRedirect(redirectUrl);

            // Stop request execution
            return false;
        }

        // =====================================================
        // ALLOW ACCESS
        // =====================================================

        /**
         * If request is safe/read-only,
         * allow access.
         */
        return true;
    }
}