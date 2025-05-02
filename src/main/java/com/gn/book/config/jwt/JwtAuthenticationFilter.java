package com.gn.book.config.jwt;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	// 클라이언트 요청시 JWT를 인증하는 필터
	// 시큐리티를 통과하려면 2단계 필터를 거쳐야 함
	// 1. JwtAuthenticationFilter
	// 2. UsernamePasswordAuthenticationFilter
	private final JwtTokenProvider jwtTokenProvider;
	 
	 @Override
	    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain)
	            throws ServletException, IOException {

	        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
	        
	        if (header != null && header.startsWith("Bearer ")) {
	            String token = header.substring(7);
	            if (jwtTokenProvider.validateToken(token)) {
	                Claims claims = jwtTokenProvider.getClaims(token);
	                // 여기서 사용자 정보 필요시 SecurityContext에 설정 가능
	                // 사용자 정보 SecurityContext에 설정
	                UsernamePasswordAuthenticationToken authentication =
	                    new UsernamePasswordAuthenticationToken(
	                        claims.getSubject(), null, null); // 권한 정보 있으면 여기에 리스트 전달
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	                
	                request.setAttribute("accountId", claims.getSubject());
	            } else {
	                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	                return;
	            }
	        } else {
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            return;
	        }

	        filterChain.doFilter(request, response);
	    }
}
