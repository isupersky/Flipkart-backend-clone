package com.tothenew.bluebox.bluebox.enitity;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Role {

  @Id
  @GeneratedValue
  Integer id;
  String authority;

  @ManyToMany(mappedBy = "roles")
  private Set<User> user;

  //	Default Constructor
  public Role() {
  }

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
