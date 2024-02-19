package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.entities.AppUser;
import com.enigma.kingkost.entities.UserCredential;
import com.enigma.kingkost.repositories.UserCredentialRepository;
import com.enigma.kingkost.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserCredentialRepository userCredentialRepository;

    @Override
    public AppUser loadUserByUserId(String id) {
        UserCredential userCredential = userCredentialRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("invalid credential"));
        return AppUser.builder()
                .id(userCredential.getId())
                .username(userCredential.getUsername())
                .password(userCredential.getPassword())
                .role(userCredential.getRoleTypeId().getName())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredential userCredential = userCredentialRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("invalid credential"));
        return AppUser.builder()
                .id(userCredential.getId())
                .username(userCredential.getUsername())
                .password(userCredential.getPassword())
                .role(userCredential.getRoleTypeId().getName())
                .build();
    }

    @Override
    public UserCredential updateUserCredential(UserCredential userCredential) {
        String userId = userCredential.getId();
        if (userId != null && loadUserByUserId(userId) != null) return userCredentialRepository.save(userCredential);
        return null;
    }

}
