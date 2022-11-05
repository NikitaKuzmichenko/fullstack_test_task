package com.example.demo.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserRoleDto {

    private long id;

    @NotNull
    private String roleName;
}
