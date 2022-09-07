package com.tech.examportal.serviceImpl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tech.examportal.constants.RoleConstants;
import com.tech.examportal.dto.UserDto;
import com.tech.examportal.entity.Role;
import com.tech.examportal.entity.User;
import com.tech.examportal.entity.UserRoleRel;
import com.tech.examportal.exception.ResourceNotFoundException;
import com.tech.examportal.exception.UserFoundException;
import com.tech.examportal.repository.RoleRepository;
import com.tech.examportal.repository.UserRepository;
import com.tech.examportal.rest.UserResource;
import com.tech.examportal.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PasswordEncoder encoder;

	private final Logger logger = LoggerFactory.getLogger(UserResource.class);

	private User orElseThrow ;

	@Override
	public UserDto createUser(UserDto userDto) throws Exception {

		User user = this.mapper.map(userDto, User.class);
		user.setPassword(this.encoder.encode(userDto.getPassword()));

		Set<UserRoleRel> userRolesSet = new HashSet<>();

		Role role = new Role();
		role.setRoleId(RoleConstants.USER_ROLE_ID);
		role.setRoleName(RoleConstants.USER_ROLE_NAME);

		UserRoleRel userRoles = new UserRoleRel();

		userRoles.setUser(user);
		userRoles.setRole(role);

		userRolesSet.add(userRoles);

		Optional<User> fetchedUser = this.userRepository.findByUsername(user.getUsername());

		if (!fetchedUser.isEmpty() && fetchedUser != null) {
			System.out.println("User already present");
			throw new UserFoundException("User already present");
		} else {
			this.roleRepository.save(role);
			user.setUserRoleRel(userRolesSet);
			

			user = this.userRepository.save(user);
		}

		UserDto savedUserDto = this.mapper.map(user, UserDto.class);
		return savedUserDto;
	}

	@Override
	public UserDto getUserByUsername(String userName) {

		Optional<User> fetchedUser = this.userRepository.findByUsername(userName);
		UserDto userDto = this.mapper.map(fetchedUser, UserDto.class);

		return userDto;
	}

	@Override
	public void deleteUserByUsername(String username) {

		this.userRepository.findOneByUsername(username).ifPresent(user -> {
			this.userRepository.delete(user);

			logger.debug("Deleted User: {}", user);

		});

	}

	@Override
	public UserDto updateUser(UserDto userDto) throws Exception {
		
		User user = this.mapper.map(userDto, User.class);
		Long userId = user.getId();
		
		User userToUpdate = this.userRepository.findById(user.getId()).
		orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		
		userToUpdate.setAbout(user.getAbout());
		userToUpdate.setEmailId(user.getEmailId());
		userToUpdate.setFirstName(user.getFirstName());
		userToUpdate.setLastName(user.getLastName());
		userToUpdate.setMobileNumber(user.getMobileNumber());
		userToUpdate.setPassword(user.getPassword());
		userToUpdate.setProfile(user.getProfile());

		userToUpdate = this.userRepository.save(userToUpdate);

		
		UserDto updatedUser = this.mapper.map(userToUpdate, UserDto.class);

		return updatedUser;
	}

}
