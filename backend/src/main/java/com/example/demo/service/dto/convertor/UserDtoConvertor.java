package com.example.demo.service.dto.convertor;

import com.example.demo.persistence.entity.User;
import com.example.demo.persistence.entity.UserRole;
import com.example.demo.service.dto.UserDto;

public final class UserDtoConvertor {
    private UserDtoConvertor(){}

    public static UserDto toDto(User user){
        if(user == null){
            return null;
        }

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setPassword(user.getPassword());
        dto.setLogin(user.getLogin());
        dto.setRole(user.getRole().getRole());

        return dto;
    }

    public static User fromDto(UserDto user){
        if(user == null){
            return null;
        }
        User entity = new User();
        entity.setId(user.getId());
        entity.setPassword(user.getPassword());
        entity.setLogin(user.getLogin());
        UserRole role = new UserRole();
        role.setRole(user.getRole());
        entity.setRole(role);
        return entity;
    }
}
