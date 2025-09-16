package com.whitenight.smartflow.utils.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String token = null;

        // Check if header starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // remove "Bearer "
        }

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            try {
                if (jwtUtil.isTokenValid(token)) {
                    Claims claims = jwtUtil.extractAllClaims(token);

                    UUID staffId = UUID.fromString(claims.getSubject());
                    String staffRole = claims.get("staffRole", String.class);
                    UUID staffCompanyId = null;
                    try {
                        staffCompanyId = UUID.fromString(claims.get("staffCompanyId", String.class));
                    } catch (Exception ignored) {}

                    CustomUserPrincipal principal = new CustomUserPrincipal(staffId, staffRole, staffCompanyId);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(principal, null, List.of());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                logger.warn("JWT Error: " + e.getMessage());
            }
        }

        // Pass request to next filter
        filterChain.doFilter(request, response);
    }
}
