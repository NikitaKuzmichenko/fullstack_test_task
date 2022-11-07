package com.example.demo.presentation.security.failurehandler;

import com.example.demo.presentation.exception.wrapper.ExceptionWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	public static final String FORBIDDEN_ERROR_MSG = "Request must be authenticated";

	private final ObjectMapper mapper = JsonMapper.builder()
			.addModule(new JavaTimeModule())
			.build();

	@Override
	public void commence(
			HttpServletRequest request,
			HttpServletResponse response,
			AuthenticationException authException)
			throws IOException, ServletException {
		ExceptionWrapper msg = new ExceptionWrapper(FORBIDDEN_ERROR_MSG,
				HttpStatus.FORBIDDEN.value(),
				ZonedDateTime.now());

		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getOutputStream().println(mapper.writeValueAsString(msg));

	}
}
