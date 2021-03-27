package com.example.demo.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The WebSecurity class is a custom implementation of the default web security
 * configuration provided by Spring Security.
 */
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private UserDetailsServiceImpl userDetailsService;
	private PasswordEncoder bCryptPasswordEncoder;

	/**
	 * Defines public resources. Below, we have set the SIGN_UP_URL endpoint as
	 * public. The http.cors() is used to make the Spring Security support the CORS
	 * (Cross-Origin Resource Sharing) and CSRF (Cross-Site Request Forgery)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll().anyRequest().authenticated()
				.and().addFilter(new JWTAuthenticationFilter(authenticationManager()))
				.addFilter(new JWTAuthenticationVerficationFilter(authenticationManager())).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	/**
	 * It declares the BCryptPasswordEncoder as the encoding technique, and loads
	 * user-specific data
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.parentAuthenticationManager(authenticationManagerBean()).userDetailsService(userDetailsService)
				.passwordEncoder(bCryptPasswordEncoder);
	}
}
