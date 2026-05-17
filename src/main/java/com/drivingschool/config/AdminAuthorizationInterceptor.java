package com.drivingschool.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminAuthorizationInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(); //get the current user session
        String role = (String) session.getAttribute("adminRole");  //get the admin role stored in the session

        //if user role is not found in session, allow the request
        if (role == null) {
            return true;
        }


    }

}
