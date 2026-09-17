package com.setu.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SessionRoleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String contextPath = request.getContextPath();
        String path = request.getRequestURI().substring(contextPath.length());

        HttpSession session = request.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        String requiredRole = null;
        boolean anyLoggedInAllowed = false;

        if (path.startsWith("/admin/") || path.startsWith("/api/admin/")) {
            requiredRole = "ADMIN";
        } else if (path.startsWith("/donor/")) {
            requiredRole = "DONOR";
        } else if (path.startsWith("/ngo/")) {
            requiredRole = "NGO";
        } else if (path.startsWith("/volunteer/")) {
            requiredRole = "VOLUNTEER";
        } else if (path.startsWith("/api/")) {
            anyLoggedInAllowed = true;
        }

        if (requiredRole != null) {
            if (role == null) {
                response.sendRedirect(contextPath + "/login");
                return;
            }
            if (!requiredRole.equals(role)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied for this role.");
                return;
            }
        } else if (anyLoggedInAllowed) {
            if (role == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Login required to access this API.");
                return;
            }
        }

        chain.doFilter(req, res);
    }
}