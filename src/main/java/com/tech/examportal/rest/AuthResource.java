package com.tech.examportal.rest;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.examportal.dto.JwtAuthRequest;
import com.tech.examportal.dto.JwtAuthResponse;
import com.tech.examportal.dto.UserDto;
import com.tech.examportal.entity.User;
import com.tech.examportal.security.CustomUserDetailsService;
import com.tech.examportal.util.JwtUtils;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AuthResource {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private ModelMapper mapper;

	private UserDto  userDto;
	
	@PostMapping("/generateToken")
	public ResponseEntity<?> generateToken(@RequestBody JwtAuthRequest jwtRequest) throws Exception{
		
		try {
			
			this.authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
			
		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
			throw new Exception("User not found");
		}
		
		 UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
		
		 String token = this.jwtUtils.generateToken(userDetails);
		 
		 return ResponseEntity.ok(new JwtAuthResponse(token));
		 
	}
	
	
	public void authenticate(String username, String password) throws Exception{
		
		try {
			
			authenticationManager.
			authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
			
		} catch (DisabledException e) {
			throw new Exception("User disabled :"+ e.getMessage());
		}catch (BadCredentialsException e) {
			throw new Exception("Invalid credentials :"+ e.getMessage());
		}
		
	}
	
	@GetMapping("/currentUser")
	public UserDto getCurrentUser(Principal principal) {
		
		User user =  (User)this.customUserDetailsService.loadUserByUsername(principal.getName());
		 UserDto userDto = this.mapper.map(user, UserDto.class);
		 return userDto;
	}

}
