package com.example.demo.service;

import com.example.demo.service.dto.RefreshTokenDto;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface RefreshTokenService {

    RefreshTokenDto create(@Valid RefreshTokenDto token);

    void update(@Valid RefreshTokenDto token);

    RefreshTokenDto getById(long id);

    RefreshTokenDto getByUserLogin(String login);

    RefreshTokenDto getByUserId(long id);

    RefreshTokenDto getByToken(String token);

}
