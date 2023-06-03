package com.boot_camp.Boot_Camp.middleware;

import com.boot_camp.Boot_Camp.model.domain.ValidateDomain;
import com.boot_camp.Boot_Camp.security.Security;
import com.boot_camp.Boot_Camp.services.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class RequestAuthorizationMiddleware implements HandlerInterceptor {

    @Autowired
    private Security security;
    @Autowired
    private UtilService utilService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        String path = request.getRequestURI();
        if (path.endsWith("/login")) {
            return true;
        }
        if (path.endsWith("/register")) {
            return true;
        }

        if (path.contains("image")) {
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if (security.validateToken(token)) {
                String id = utilService.searchDatabaseID(security.getIdToken(token));
                if (id == null) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
                }
                request.setAttribute("id", id);
                return true;
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ValidateDomain validateDomain = new ValidateDomain();
        validateDomain.setValid(false);
        validateDomain.setMessage("TokenExpired");
        response.getWriter().write(String.valueOf(new ValidateDomain()));
        return false;
    }
}
