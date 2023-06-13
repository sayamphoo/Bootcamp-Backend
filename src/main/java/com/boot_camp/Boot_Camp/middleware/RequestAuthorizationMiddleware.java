package com.boot_camp.Boot_Camp.middleware;

import com.boot_camp.Boot_Camp.model.domain.ValidateDomain;
import com.boot_camp.Boot_Camp.security.Security;
import com.boot_camp.Boot_Camp.services.UtilService;
import org.jetbrains.annotations.NotNull;
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
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, Object handler) throws IOException {


        String paths = request.getRequestURI();

        String[] pathList = {
                "login",
                "register",
                "image",
                "forgot-password",
                "all-delete",
        };


        for (String path : pathList) {
            if (paths.contains(path)) {
                return true;
            }
        }


        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if (security.validateToken(token)) {
                String id = utilService.searchDatabaseID(security.getIdToken(token));
                if (id == null) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
                }
                request.setAttribute("id", id);
                request.setAttribute("idAccount", security.getIdToken(token));
                return true;
            }
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "TokenExpired");

    }
}
