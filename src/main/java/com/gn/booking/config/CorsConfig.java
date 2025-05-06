package com.gn.booking.config;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CorsConfig {
	
	public static CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		
		// 리소스를 허용할 URL
		ArrayList<String> allowedOriginPatterns = new ArrayList<String>();
        allowedOriginPatterns.add("http://localhost:3000");
        allowedOriginPatterns.add("http://127.0.0.1:3000");
        configuration.setAllowedOrigins(allowedOriginPatterns);
        
        // 리소스를 허용할 HTTP METHOD
        ArrayList<String> allowedHttpMethods = new ArrayList<>();
        allowedHttpMethods.add("GET");
        allowedHttpMethods.add("POST");
        allowedHttpMethods.add("PUT");
        allowedHttpMethods.add("DELETE");
        configuration.setAllowedMethods(allowedHttpMethods);
        
        // 모든 헤더에 적용
        configuration.setAllowedHeaders(Collections.singletonList("*"));
		
        //인증, 인가를 위한 credentials 를 TRUE로 설정
        configuration.setAllowCredentials(true); 
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
	}

}
