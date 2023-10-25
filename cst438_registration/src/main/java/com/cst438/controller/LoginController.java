package com.cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cst438.domain.AccountCredentials;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.UserRepository;
import com.cst438.domain.User;
import com.cst438.service.JwtService;


@RestController
public class LoginController {
	@Autowired
	private JwtService jwtService;

	@Autowired	
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;


	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials) {
		UsernamePasswordAuthenticationToken creds =
				new UsernamePasswordAuthenticationToken(
						credentials.username(),
						credentials.password());

		Authentication auth = authenticationManager.authenticate(creds);
		
		User role = userRepository.findByAlias(credentials.username());
		
		System.out.println(role.getRole());
		String userRole = role.getRole();
		// Generate token
		String jwts = jwtService.getToken(auth.getName());
		//System.out.println(auth.getName());
		//String role = jwtService.getToken(userAuth.getName());
		
		// Build response with the generated token
		return ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts)
				.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
				.header(HttpHeaders.AUTHORIZATION, "Role" + userRole)
				.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,"Role")
				.build();

	}
	
	
}