package com.example.demo.persistence.repository;

import com.example.demo.persistence.entity.UserRole;

public interface UserRoleRepository {

    UserRole getRoleByName(String roleName);

    UserRole getRoleById(long id);

    UserRole create(UserRole role);
}
