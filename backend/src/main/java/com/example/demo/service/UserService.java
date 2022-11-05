package com.example.demo.service;

import com.example.demo.service.dto.UserDto;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface UserService {

    UserDto create(@Valid UserDto user);

    void update(@Valid UserDto user);

    UserDto createAndEncodePassword(@Valid UserDto user);

    UserDto getById(long id);

    UserDto getByLogin(String login);

    void delete(long id);
}
