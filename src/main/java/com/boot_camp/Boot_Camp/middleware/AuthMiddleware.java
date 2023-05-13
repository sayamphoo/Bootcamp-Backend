package com.boot_camp.Boot_Camp.middleware;

import com.boot_camp.Boot_Camp.security.Security;
import com.boot_camp.Boot_Camp.services.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthMiddleware implements HandlerInterceptor {

    @Autowired
    private Security security;
    @Autowired
    private UtilService utilService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        if (path.endsWith("/login") || path.endsWith("/register")) {
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if (security.validateToken(token)) {
                String id = utilService.searchDatabaseID(security.getIdToken(token));
                if (id == null) {
                    throw new Exception("User not found.");
                }
                request.setAttribute("id", id);

                return true;
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Unauthorized access");
        return false;
    }

}