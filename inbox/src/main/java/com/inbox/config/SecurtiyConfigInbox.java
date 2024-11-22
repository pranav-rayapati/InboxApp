package com.inbox.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurtiyConfigInbox {

	@Bean
	SecurityFilterChain securityConfig(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.authorizeHttpRequests(
						authz -> authz.requestMatchers("/", "/error","/user ").permitAll().anyRequest().authenticated())
				.exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
				.csrf((csrf) -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
				.logout(l -> l.logoutSuccessUrl("/").permitAll())
				.oauth2Login(o -> o.defaultSuccessUrl("/user", true).failureUrl("/login?error=true"));

		return httpSecurity.build();

	}
}
