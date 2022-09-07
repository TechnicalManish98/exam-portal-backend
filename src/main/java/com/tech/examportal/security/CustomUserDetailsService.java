package com.tech.examportal.security;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tech.examportal.entity.User;
import com.tech.examportal.repository.UserRepository;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
   // @Autowired
   // private CustomUserDetails customUserDetails;
    
	@Autowired
	private UserRepository userRepository;
	private Optional<User> user;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		user = this.userRepository.findByUsername(username);

		if (user.isEmpty() && user == null) {
			System.out.println("User not found");
			throw new UsernameNotFoundException("User with username " + username + " was not found in the database");
		}

//		CustomUserDetails customUserDetails = new CustomUserDetails(user.get());
		
		UserDetails userDetails = (UserDetails) user.get();
		
	// customUserDetails.setUsername(user.get().getUsername());
	// customUserDetails.setPassword(user.get().getPassword());
	 
		
		//User userDetails = user.get();
		return userDetails;
	}

}
