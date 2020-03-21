package com.tothenew.bluebox.bluebox.enitity;

import java.util.UUID;

public class UserRole {
	
	private UUID userId;
	private Integer roleId;
	
//	Default Constructor
	public UserRole() {	}
	
//	Getters and setters
	public UUID getUserId() {
		return userId;
	}
	
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	
	public Integer getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	@Override
	public String toString() {
		return "UserRole{" +
				"userId=" + userId +
				", roleId=" + roleId +
				'}';
	}
}
