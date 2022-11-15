package com.example.demo.presentation.security;

import com.example.demo.presentation.security.filter.exceprion.FilterChainExceptionHandler;
import com.example.demo.presentation.security.filter.jwt.JwtAuthenticationFilter;
import com.example.demo.presentation.security.filter.jwt.JwtTokenVerifier;
import com.example.demo.presentation.security.token.jwt.JwtTokenManager;
import com.example.demo.presentation.security.token.refresh.RefreshTokenManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfig{

	@Bean
	public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder,
														 UserDetailsService detailsService) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(detailsService);
		return provider;
	}

	@Bean
	public AuthenticationManager authManager(HttpSecurity http,
											 PasswordEncoder passwordEncoder,
											 UserDetailsService detailsService) throws Exception {

		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(detailsService)
				.passwordEncoder(passwordEncoder)
				.and()
				.build();
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http,
											  AuthenticationManager authenticationManager,
											  AccessDeniedHandler accessDeniedHandler,
											  FilterChainExceptionHandler filterChainExceptionHandler,
											  AuthenticationEntryPoint authenticationEntryPoint,
											  JwtTokenManager jwtTokenManager,
											  RefreshTokenManager refreshTokenManager) throws Exception {

		http.
				csrf().disable().
				cors().configurationSource(corsConfigurationSource());

		http.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler)
				.authenticationEntryPoint(authenticationEntryPoint)
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilterBefore(filterChainExceptionHandler, LogoutFilter.class)
				.addFilter(
						new JwtAuthenticationFilter(
								authenticationManager, jwtTokenManager, refreshTokenManager))
				.addFilterBefore(new JwtTokenVerifier(jwtTokenManager), JwtAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("http://localhost:4200"));
		configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"));
		configuration.setAllowCredentials(true);
		//the below three lines will add the relevant CORS response headers
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
