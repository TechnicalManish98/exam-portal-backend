package com.tech.examportal.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tech.examportal.audit.Auditable;
import com.tech.examportal.security.Authority;

@Entity
@Table(name = "users")
public class User extends Auditable<String> implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq")
	private long id;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "email_address", nullable = false, unique=true)
	private String emailId;
	
	@Column(nullable = false, unique=true)
	private String username;

	@JsonIgnore
	private String password;

	private String about;

	@Column(name = "mobile_number")
	private String mobileNumber;
	
	private String profile;
	
	private boolean isActive = true;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "user")
	private Set<UserRoleRel> userRoleRel = new HashSet<>();

	public User() {
		super();
	}

	

	public User(long id, String firstName, String lastName, String emailId, String username, String password,
			String about, String mobileNumber, String profile, boolean isActive, Set<UserRoleRel> userRoleRel) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.username = username;
		this.password = password;
		this.about = about;
		this.mobileNumber = mobileNumber;
		this.profile = profile;
		this.isActive = isActive;
		this.userRoleRel = userRoleRel;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<UserRoleRel> getUserRoleRel() {
		return userRoleRel;
	}

	public void setUserRoleRel(Set<UserRoleRel> userRoleRel) {
		this.userRoleRel = userRoleRel;
	}



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Set<Authority> authority = new HashSet<>();
		
		this.userRoleRel.forEach(userRole -> 
		{
			authority.add(new Authority(userRole.getRole().getRoleName()));
		});
		return authority;
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
	
	

}
