package com.reservSystem.ReservSystem.config;

import com.reservSystem.ReservSystem.models.Role;
import com.reservSystem.ReservSystem.models.User;
import com.reservSystem.ReservSystem.services.JwtService;
import com.reservSystem.ReservSystem.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        if (!jwtService.isTokenValid(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String email = jwtService.extractEmail(token);

        if (email != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            User user = null;
            try {
                user = userService.findByEmail(email);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    );

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return path.startsWith("/h2-console")
                || path.startsWith("/api/auth/login")
                || path.startsWith("/api/auth/register")
                || path.startsWith("/swagger")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/webjars")
                || path.startsWith("/error");
    }
}