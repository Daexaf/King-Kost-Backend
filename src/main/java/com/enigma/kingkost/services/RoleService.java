package com.enigma.kingkost.services;

import com.enigma.kingkost.entities.RoleType;
import org.springframework.stereotype.Service;


public interface RoleService {
    RoleType getOrSave(RoleType role);
}
