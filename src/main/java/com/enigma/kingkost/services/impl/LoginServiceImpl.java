package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.dto.request.LoginRequest;
import com.enigma.kingkost.dto.response.LoginResponse;
import com.enigma.kingkost.entities.AppUser;
import com.enigma.kingkost.security.JwtUtil;
import com.enigma.kingkost.services.*;
import com.enigma.kingkost.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final JwtUtil jwtUtil;
    private final ValidationUtil validationUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public LoginResponse login(LoginRequest request) {
        try {
            validationUtil.validate(request);

            String username = request.getUsername();
            if (username != null) {
                username = username.toLowerCase();
            } else {
                throw new NullPointerException("Username cannot be null");
            }

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    username, request.getPassword()
            ));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            AppUser appUser = (AppUser) authentication.getPrincipal();

            String token = jwtUtil.generateToken(appUser);

            return LoginResponse.builder()
                    .username(appUser.getUsername())
                    .token(token)
                    .role(appUser.getRole().name())
                    .userId(appUser.getId())
                    .build();

        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username cannot be null");
        }
    }
}
