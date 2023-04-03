package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CustomUserDetailService;
import com.example.exception.ErrorResponse;
import com.example.exception.TokenExpireException;
import com.example.jwt.util.JwtUtil;
import com.example.model.JWTRequest;
import com.example.model.JwtResponse;

@RestController
public class JwtController {

	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private CustomUserDetailService detailService;
	
	
	
	@PostMapping(value = "/rapid/token")
	public ResponseEntity<?> generateToken(@RequestBody JWTRequest jwtreq) throws UsernameNotFoundException, Exception{
		try {
			
			this.manager.authenticate(new UsernamePasswordAuthenticationToken(jwtreq.getUsername(), jwtreq.getPassword()));
		}
		catch (UsernameNotFoundException e) {
			throw new UsernameNotFoundException("Bad credentials");
		}
		
		UserDetails userdetails = this.detailService.loadUserByUsername(jwtreq.getUsername());
		JwtUtil jwtUtil = new JwtUtil();
		String token = jwtUtil.generateToken(userdetails);
		
		return  ResponseEntity.ok(new JwtResponse(token));
	}
	
		
		
	}

