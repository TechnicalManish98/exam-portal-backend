package com.tech.examportal.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tech.examportal.audit.Auditable;

@Entity
@Table(name = "roles")
public class Role{

	private static final long serialVersionUID = 1L;

	@Id
	private Long roleId;

	@Column(name = "role_name", nullable = false)
	private String roleName;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "role")
	private Set<UserRoleRel> userRoleRel = new HashSet<>();

	public Role() {
		super();
	}

	public Role(Long roleId, String roleName) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
