package com.BreedingGrounds.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.BreedingGrounds.service.JWTTokenService;
import com.BreedingGrounds.service.UserProfileService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final UserProfileService userProfileService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final JWTTokenService jwtTokenService;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	public SecurityConfig(UserProfileService userProfileService, 
			BCryptPasswordEncoder bCryptPasswordEncoder,
			 JWTTokenService jwtTokenService,
			 JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
		this.userProfileService = userProfileService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.jwtTokenService = jwtTokenService;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests().antMatchers(
				"/api/v1/userprofile/login",
				"/api/v1/userprofile/logout/{id}").permitAll()
		.anyRequest().authenticated()
		.and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.addFilterBefore(new JWTAuthenticationFilter(
				jwtTokenService, 
				userProfileService), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(this.daoAuthenticationProvider());
		
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		
		provider.setPasswordEncoder(this.bCryptPasswordEncoder);
		provider.setUserDetailsService(userProfileService);
		
		return provider;
	}
	
	public UserProfileService getUserProfileService() {
		return userProfileService;
	}
	
}
