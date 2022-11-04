package com.example.demo.persistence.repository;

import com.example.demo.persistence.entity.RefreshToken;

public interface RefreshTokenRepository {

    RefreshToken getById(long id);

    RefreshToken getByUserId(long id);

    RefreshToken getByUserLogin(String login);

    RefreshToken getByToken(String token);

    RefreshToken createToken(RefreshToken token);

    boolean updateToken(RefreshToken token);
}
