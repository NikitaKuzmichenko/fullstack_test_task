package com.example.demo.presentation.security.token.refresh;

import com.example.demo.service.RefreshTokenService;
import com.example.demo.service.UserService;
import com.example.demo.service.dto.RefreshTokenDto;
import com.example.demo.service.exception.EntityNotExistException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Data
@Component
@PropertySource(value = "classpath:security/refreshToken.properties")
public class RefreshTokenManager {

	private long liveTime;

	public static final String HEADER_NAME = "REFRESH_TOKEN";

	@Autowired
	private final RefreshTokenService refreshTokenService;

	@Autowired
	private final UserService userService;

	public RefreshTokenManager(@Value("${liveTimeInSec}") long liveTime,
							   RefreshTokenService refreshTokenService,
							   UserService userService) {
		this.liveTime = liveTime;
		this.refreshTokenService = refreshTokenService;
		this.userService = userService;
	}

	public String generateToken(long userId) {
		RefreshTokenDto token;
		try {
			token = refreshTokenService.getByUserId(userId);
		}catch (EntityNotExistException e){
			token = new RefreshTokenDto();
			token.setUserId(userId);
			token.setCreationDate(new Date());
			token.setExpirationDate(getExpirationDate(new Date()));
			token.setValue(UUID.randomUUID().toString());
			refreshTokenService.create(token);
			return token.getValue();
		}

		token.setCreationDate(new Date());
		token.setExpirationDate(getExpirationDate(new Date()));
		token.setValue(UUID.randomUUID().toString());
		refreshTokenService.update(token);

		return token.getValue();
	}


	public String refreshToken(String token) {
		RefreshTokenDto existingToken = refreshTokenService.getByToken(token);
		if (existingToken == null) {
			return null;
		}
		return generateToken(existingToken.getUserId());
	}

	public boolean isTokenExpired(RefreshTokenDto token) {
		if (token == null) {
			return false;
		}
		return token.getExpirationDate().compareTo(Date.from(Instant.now())) < 0;
	}

	private Date getExpirationDate(Date date) {
		return Date.from(date.toInstant().plus(liveTime, ChronoUnit.SECONDS));
	}

}
