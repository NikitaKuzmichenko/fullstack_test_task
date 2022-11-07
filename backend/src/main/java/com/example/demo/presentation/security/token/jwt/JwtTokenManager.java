package com.example.demo.presentation.security.token.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.service.dto.UserDto;
import com.example.demo.service.security.CustomUserDetails;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

@Data
@Component
@PropertySource(value = "classpath:security/jwtToken.properties")
public class JwtTokenManager {

	private String secret;

	private long tokenExpirationPeriodInSeconds;

	public JwtTokenManager(
			@Value("${secret}") String secret,
			@Value("${timeToLiveInSec}") long tokenExpirationPeriodInSeconds) {
		this.secret = secret;
		this.tokenExpirationPeriodInSeconds = tokenExpirationPeriodInSeconds;
	}

	private Algorithm encodingAlgorithm;
	public static final String HEADER_NAME = "JWT";
	public static final String AUTHORITIES_CLAIMS_FIELD_NAME = "authorities";

	@PostConstruct
	private void postInit() {
		encodingAlgorithm = Algorithm.HMAC256(secret);
	}

	public String createJwt(CustomUserDetails user) {
		if (user == null) {
			return null;
		}
		return JWT.create()
				.withSubject(user.getUsername())
				.withIssuedAt(new Date())
				.withJWTId(String.valueOf(user.getUserId()))
				.withExpiresAt(getExpirationDate(new Date()))
				.withClaim(
						AUTHORITIES_CLAIMS_FIELD_NAME,
						user.getAuthorities().stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.sign(encodingAlgorithm);
	}

	public String createJwt(UserDto user) {
		if (user == null) {
			return null;
		}
		return JWT.create()
				.withSubject(user.getLogin())
				.withIssuedAt(new Date())
				.withJWTId(String.valueOf(user.getId()))
				.withExpiresAt(getExpirationDate(new Date()))
				.withClaim(AUTHORITIES_CLAIMS_FIELD_NAME, user.getRole())
				.sign(encodingAlgorithm);
	}

	public DecodedJWT decode(String jwt) {
		if (jwt == null) {
			return null;
		}
		JWTVerifier verifier = JWT.require(encodingAlgorithm).build();
		return verifier.verify(jwt);
	}

	private Date getExpirationDate(Date date) {
		return Date.from(date.toInstant().plus(tokenExpirationPeriodInSeconds, ChronoUnit.SECONDS));
	}
}
