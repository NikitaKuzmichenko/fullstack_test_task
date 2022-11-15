package com.example.demo.presentation.security.filter.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.presentation.security.token.jwt.JwtTokenManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {

	private final JwtTokenManager jwtTokenManager;

	public JwtTokenVerifier(JwtTokenManager jwtTokenManager) {
		this.jwtTokenManager = jwtTokenManager;
	}

	@Override
	protected void doFilterInternal(
			HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = request.getHeader(JwtTokenManager.HEADER_NAME);

		if (token == null) {
			filterChain.doFilter(request, response);
			return;
		}

		DecodedJWT decodedJWT = jwtTokenManager.decode(token);
		String login = decodedJWT.getSubject();

		Set<SimpleGrantedAuthority> authorities =
				Arrays.stream(
						decodedJWT.
								getClaim(JwtTokenManager.AUTHORITIES_CLAIMS_FIELD_NAME).
								asArray(String.class)).
						filter(Objects::nonNull).
						map(SimpleGrantedAuthority::new).
						collect(Collectors.toSet());

		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(login, null, authorities);

		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		filterChain.doFilter(request, response);
	}
}
