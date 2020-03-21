package com.tothenew.bluebox.bluebox.enitity;

public class Role {
	Integer id;
	String authority;
	
//	Default Constructor
	public Role() {	}
	
//	Getters and Setters
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getAuthority() {
		return authority;
	}
	
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	@Override
	public String toString() {
		return "Role{" +
				"id=" + id +
				", authority='" + authority + '\'' +
				'}';
	}
}
