package com.example.demo.presentation.controller;

import com.example.demo.presentation.exception.RefreshTokenExpiredException;
import com.example.demo.presentation.security.token.jwt.JwtTokenManager;
import com.example.demo.presentation.security.token.refresh.RefreshTokenManager;
import com.example.demo.service.RefreshTokenService;
import com.example.demo.service.UserService;
import com.example.demo.service.dto.RefreshTokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/auth",produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	private final RefreshTokenManager tokenManager;

	@Autowired
	private final JwtTokenManager jwtTokenManager;

	@Autowired
	private final RefreshTokenService tokenService;

	@Autowired
	private final UserService userService;

	public AuthenticationController(RefreshTokenManager tokenManager,
									JwtTokenManager jwtTokenManager,
									RefreshTokenService tokenService,
									UserService userService) {
		this.tokenManager = tokenManager;
		this.jwtTokenManager = jwtTokenManager;
		this.tokenService = tokenService;
		this.userService = userService;
	}

	@PreAuthorize("permitAll()")
	@GetMapping(value = "refreshToken", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> refreshToken(
			@RequestHeader(value = RefreshTokenManager.HEADER_NAME) String refreshToken) {

		RefreshTokenDto token = tokenService.getByToken(refreshToken);
		if (tokenManager.isTokenExpired(token)) {
			throw new RefreshTokenExpiredException();
		}

		return ResponseEntity.status(HttpStatus.OK)
				.header(RefreshTokenManager.HEADER_NAME, tokenManager.refreshToken(refreshToken))
				.header(
						JwtTokenManager.HEADER_NAME,
						jwtTokenManager.createJwt(userService.getById(token.getUserId())))
				.build();
	}
}
