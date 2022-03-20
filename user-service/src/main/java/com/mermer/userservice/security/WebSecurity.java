package com.mermer.userservice.security;

import javax.servlet.Filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http.csrf().disable();
		//http.authorizeRequests().antMatchers("/users/**").permitAll();
		http.authorizeRequests()
		.antMatchers("/**").hasIpAddress("172.20.208.1")//IP 설정
		.and()
		.addFilter(getAuthenticationFilter());
		
		http.headers().frameOptions().disable();
	}

	
	private AuthenticationFilter getAuthenticationFilter() throws Exception {

		AuthenticationFilter authenticationFilter = new AuthenticationFilter();
		authenticationFilter.setAuthenticationManager(authenticationManager());
		return authenticationFilter;
	}
}
