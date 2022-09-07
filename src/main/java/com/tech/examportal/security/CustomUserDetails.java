/*package com.tech.examportal.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.tech.examportal.entity.User;

@Component("UserDetails")
public class CustomUserDetails extends User
implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	//@Lazy
	private User user;

	public CustomUserDetails(User user) {
	//	super(user);
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Set<Authority> authority = new HashSet<>();
		
		this.user.getUserRoleRel().forEach(userRole -> 
		{
			authority.add(new Authority(userRole.getRole().getRoleName()));
		});
		return authority;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}*/
