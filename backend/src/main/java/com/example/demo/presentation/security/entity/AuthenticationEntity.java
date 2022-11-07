package com.example.demo.presentation.security.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthenticationEntity implements Serializable {
	private String login;
	private String password;
}
