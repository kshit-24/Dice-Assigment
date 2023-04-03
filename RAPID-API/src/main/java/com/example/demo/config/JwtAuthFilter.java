package com.example.demo.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.CustomUserDetailService;
import com.example.exception.ErrorResponse;
import com.example.exception.TokenExpireException;
import com.example.jwt.util.JwtUtil;

@Configuration
@ComponentScan
@Component
@Service

public class JwtAuthFilter extends OncePerRequestFilter {

	
	@Value("${rapid.token.start}")
	String tokenStart;
	
	@Autowired
	private CustomUserDetailService detailService;
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws  TokenExpireException , ServletException, IOException {
		String tokenHeader = request.getHeader("Authorization");
		String username=null;
		String jwtToken=null;
		boolean isTokenExpire ;
		if(request!=null && tokenHeader!=null && tokenHeader.startsWith(tokenStart)) {
			jwtToken = tokenHeader.substring(tokenStart.length());
			
			 JwtUtil jwtUtil = new JwtUtil();

			try {
				isTokenExpire = jwtToken!=null && jwtUtil.validateTokenExpiry(jwtToken);
				username = jwtUtil.extractUsernamefromToken(jwtToken);
				
			}catch(Exception e) {
				throw new TokenExpireException("Token is expired. Create new Token");
			}
			
			if(username!=null &&!isTokenExpire && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails userdetails = this.detailService.loadUserByUsername(username);
				
				if(jwtUtil.validateToken(jwtToken, userdetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken	= 
						new UsernamePasswordAuthenticationToken(userdetails,null, userdetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
				else {
					/*
					 * need to throw exception and handle
					 */
					System.out.println("Token is not valide");
				}
			}else {
				System.out.println("username is null");
			}
			
			
		}
		
		filterChain.doFilter(request, response);
	}
	
	
	
}
