package com.gn.booking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtTokenProvider jwtTokenProvider;
	
	@Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    	http.csrf(csrf -> csrf.disable())
    		.cors(cors -> cors.configurationSource(CorsConfig.corsConfigurationSource()))
    		.httpBasic(basic -> basic.disable())
    		.formLogin(login -> login.disable())
    		.authorizeHttpRequests(authorize -> authorize
    				.requestMatchers("/api/**").authenticated()
                    .anyRequest().permitAll()
            )
    		.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
	
	@Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() {
    	return new JwtAuthenticationFilter(jwtTokenProvider);
    }
	
	 @Bean
	 AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		 return http.getSharedObject(AuthenticationManagerBuilder.class)
	                   .build();
	    }
}
