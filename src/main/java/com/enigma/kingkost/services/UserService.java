package com.enigma.kingkost.services;

import com.enigma.kingkost.entities.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    AppUser loadUserByUserId(String id);
}
