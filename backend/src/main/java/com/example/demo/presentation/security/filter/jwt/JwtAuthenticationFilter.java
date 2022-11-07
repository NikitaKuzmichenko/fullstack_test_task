package com.example.demo.presentation.security.filter.jwt;

import com.example.demo.presentation.exception.wrapper.ExceptionWrapper;
import com.example.demo.presentation.security.entity.AuthenticationEntity;
import com.example.demo.presentation.security.token.jwt.JwtTokenManager;
import com.example.demo.presentation.security.token.refresh.RefreshTokenManager;
import com.example.demo.service.security.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	public static final String AUTHENTICATION_ERROR_MSG = "Authentication error";

	private final AuthenticationManager authenticationManager;

	private final JwtTokenManager jwtTokenManager;

	private final RefreshTokenManager refreshTokenManager;

	private final ObjectMapper mapper = JsonMapper.builder()
			.addModule(new JavaTimeModule())
			.build();

	public JwtAuthenticationFilter(
			AuthenticationManager authenticationManager,
			JwtTokenManager jwtTokenManager,
			RefreshTokenManager refreshTokenManager) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenManager = jwtTokenManager;
		this.refreshTokenManager = refreshTokenManager;
	}

	@Override
	public Authentication attemptAuthentication(
			HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		try {
			AuthenticationEntity authenticationRequest =
					new ObjectMapper().readValue(request.getInputStream(), AuthenticationEntity.class);

			Authentication authentication =
					new UsernamePasswordAuthenticationToken(
							authenticationRequest.getLogin(), authenticationRequest.getPassword());

			return authenticationManager.authenticate(authentication);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	protected void successfulAuthentication(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain chain,
			Authentication authResult)
			throws IOException, ServletException {
		CustomUserDetails user = (CustomUserDetails) authResult.getPrincipal();

		String jwt = jwtTokenManager.createJwt(user);
		String refreshToken = refreshTokenManager.generateToken(user.getUserId());

		response.setHeader(JwtTokenManager.HEADER_NAME, jwt);
		response.setHeader(RefreshTokenManager.HEADER_NAME, refreshToken);
	}

	@Override
	protected void unsuccessfulAuthentication(
			HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {

		ExceptionWrapper msg = new ExceptionWrapper(
				AUTHENTICATION_ERROR_MSG,
				HttpStatus.UNAUTHORIZED.value(),
				ZonedDateTime.now());

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getOutputStream().println(mapper.writeValueAsString(msg));


	}
}
