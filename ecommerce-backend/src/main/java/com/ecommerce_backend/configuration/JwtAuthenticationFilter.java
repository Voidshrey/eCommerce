package com.ecommerce_backend.configuration;

import com.ecommerce_backend.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

// this acts as connect dot between token and spring security
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // get auth from header
        var token = request.getHeader("Authorization");

        // check if auth null or have bearer
        if (null != token && token.startsWith("Bearer ")) {

            // parse only the token
            String parsedToken = token.substring(7);

            if (jwtUtils.validateToken(parsedToken)) {
                var userNameFromToken = jwtUtils.getUserNameFromToken(parsedToken);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = UsernamePasswordAuthenticationToken.authenticated(userNameFromToken, null,
                        Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }

        // do the next steps means move to controller

        filterChain.doFilter(request, response);
    }
}
