package com.tech.examportal.rest;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.examportal.dto.ApiResponse;
import com.tech.examportal.dto.UserDto;
import com.tech.examportal.service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserResource {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper mapper;
	
	private final Logger logger = LoggerFactory.getLogger(UserResource.class);



	@PostMapping("/user")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) throws Exception {

		logger.debug("REST request to create User: {}", userDto.getUsername());
		
		UserDto createdUser = this.userService.createUser(userDto);
		
		return new ResponseEntity<UserDto>(createdUser, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<UserDto> getUser(@PathVariable("username") String username) {
		
		logger.debug("REST request to get User: {}", username);
		
		return ResponseEntity.ok(this.userService.getUserByUsername(username));
		
	}
	
	@DeleteMapping("/{username}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("username") String username) {
		
		logger.debug("REST request to delete User: {}", username);

		this.userService.deleteUserByUsername(username);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully with identifier "+username, true), HttpStatus.OK);
	}
	
	@PutMapping("/users")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto) throws Exception {
		
		logger.debug("REST request to update User: {}", userDto.getUsername());
		
		UserDto user = this.userService.updateUser(userDto);
		
		return new ResponseEntity<UserDto>(user,HttpStatus.OK);
		
	}

}
