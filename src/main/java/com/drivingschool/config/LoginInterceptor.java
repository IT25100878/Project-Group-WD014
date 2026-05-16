package com.drivingschool.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * LoginInterceptor is used to protect secure pages
 * from unauthorized access.
 *
 * This interceptor checks whether an admin user
 * is logged into the system before allowing access
 * to protected pages.
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * preHandle() runs before the controller method executes.
     *
     * Purpose:
     * - Check whether the user is logged in
     * - Redirect unauthenticated users to login page
     *
     * Returns:
     * - true  -> continue request
     * - false -> stop request
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // =====================================================
        // GET CURRENT SESSION
        // =====================================================

        /**
         * Gets the current HTTP session.
         *
         * Session stores logged-in user information.
         */
        HttpSession session = request.getSession();

        // =====================================================
        // CHECK LOGIN STATUS
        // =====================================================

        /**
         * Check whether "loggedAdmin" exists in session.
         *
         * If it is null:
         * - user is not logged in
         * - redirect user to login page
         */
        if (session.getAttribute("loggedAdmin") == null) {

            // Redirect user to login page
            response.sendRedirect("/login");

            // Stop request execution
            return false;
        }

        // =====================================================
        // USER IS LOGGED IN
        // =====================================================

        /**
         * If session contains loggedAdmin:
         * - allow access to requested page
         */
        return true;
    }
}
