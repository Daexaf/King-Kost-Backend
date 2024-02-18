package com.enigma.kingkost.security;

import com.enigma.kingkost.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String headerAuth = request.getHeader("Authorization");
            String token = null;
            if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
                token = headerAuth.substring(7);
            }
            if (token != null && jwtUtil.verifyJwtToken(token)) {
                Map<String, String> userInfo = jwtUtil.getUserInfoByToken(token);
                UserDetails user = userService.loadUserByUserId(userInfo.get("userId"));
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "Forbidden: Invalid or missing token");
            String jsonBody = objectMapper.writeValueAsString(responseBody);

            response.getWriter().write(jsonBody);
            response.getWriter().flush();
            response.getWriter().close();
            return;
        }
        filterChain.doFilter(request, response);
    }
}
