package com.dcl.config;



import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.SneakyThrows;

@Configuration
@EnableWebSecurity
public class AppConfig {
	
	@Bean
	public ModelMapper modelmapper() {
		return new ModelMapper();
	}
	
	private final UserDetailsService uservice;
	
	public AppConfig(UserDetailsService uservice) {
		this.uservice=uservice;
	}
	
	@Bean
	@SneakyThrows
	public SecurityFilterChain filtersecurity(HttpSecurity security) {
		
		security.csrf(csrf->csrf.disable())
		        .authorizeHttpRequests(req->req.requestMatchers(
				                               "/user/register",
				                               "/user/loginu",
				                               "/v3/api-docs/**",
     		                                   "/swagger-ui/**",
     		                                   "/swagger-ui.html")			                              
				                               .permitAll()
				                               .anyRequest()
				                               .authenticated());		                                       
		return security.build();
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authmanager(AuthenticationConfiguration config) {
		return config.getAuthenticationManager();
	}
	
	@Bean
	public DaoAuthenticationProvider provider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider(uservice);
		provider.setPasswordEncoder(encoder());
		return provider;
	}
}
