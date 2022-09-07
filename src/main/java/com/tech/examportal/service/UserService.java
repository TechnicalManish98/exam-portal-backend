package com.tech.examportal.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.tech.examportal.dto.UserDto;
import com.tech.examportal.entity.UserRoleRel;

@Service
public interface UserService {
	
	public UserDto createUser(UserDto userDto) throws Exception;

	public UserDto getUserByUsername(String userName);

	public void deleteUserByUsername(String username);
	
	public UserDto updateUser(UserDto userDto) throws Exception;


}
