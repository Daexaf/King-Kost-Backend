package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.entities.RoleType;
import com.enigma.kingkost.repositories.RoleRepository;
import com.enigma.kingkost.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleType getOrSave(RoleType role) {
        Optional<RoleType> optionalRole = roleRepository.findByName(role.getName());

        if (!optionalRole.isEmpty()) {
            return optionalRole.get();
        }
        return roleRepository.save(role);
    }
}
