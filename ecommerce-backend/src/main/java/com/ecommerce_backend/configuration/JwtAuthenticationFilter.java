package com.ecommerce_backend.configuration;

import com.ecommerce_backend.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

// this acts as connect dot between token and spring security
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final HandlerExceptionResolver resolver;

    public JwtAuthenticationFilter(JwtUtils jwtUtils, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtUtils = jwtUtils;
        this.resolver = resolver;
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
            try {
                if (jwtUtils.validateToken(parsedToken)) {
                    var userNameFromToken = jwtUtils.getUserNameFromToken(parsedToken);
                    var roleFromToken = jwtUtils.getRoleFromToken(parsedToken);
                    List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(roleFromToken));

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                            = UsernamePasswordAuthenticationToken.authenticated(userNameFromToken, null,
                            authorities);

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }

            } catch (Exception e) {
                resolver.resolveException(request, response, null, e);
                return;
            }
            // do the next steps means move to controller
        }
        filterChain.doFilter(request, response);
    }
}
