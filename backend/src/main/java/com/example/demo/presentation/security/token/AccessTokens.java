package com.example.demo.presentation.security.token;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccessTokens implements Serializable {
    private String jwt;
    private String refreshToken;
}
