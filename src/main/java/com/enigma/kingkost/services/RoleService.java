package com.enigma.kingkost.services;

import com.enigma.kingkost.entities.RoleType;


public interface RoleService {
    RoleType getOrSave(RoleType role);
}
