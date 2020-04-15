package com.tothenew.bluebox.bluebox.enitity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String email;

  private String firstName;

  private String middleName;

  private String lastName;

  private String password;

  private String userDp;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName =
          "id"))
  private List<Role> roles;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<Address> address;

  //
  private boolean isDeleted = false;
  private boolean isActive = false;
  private boolean isLocked = false;

  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;

  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedDate;

  //	Default Constructor
  public User() {
  }

//	Getters And setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

//  public String getPassword() {
//    return password;
//  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUserDp() {
    return userDp;
  }

  public void setUserDp(String userDp) {
    this.userDp = userDp;
  }

  public boolean isDeleted() {
    return isDeleted;
  }

  public void setDeleted(boolean deleted) {
    isDeleted = deleted;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  public Set<Address> getAddress() {
    return address;
  }

  public void setAddress(Set<Address> address) {
    address.forEach(e -> e.setUser(this));
    this.address = address;
  }

  public boolean isLocked() {
    return isLocked;
  }

  public void setLocked(boolean locked) {
    isLocked = locked;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public Date getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(Date updatedDate) {
    this.updatedDate = updatedDate;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", email='" + email + '\'' +
        ", firstName='" + firstName + '\'' +
        ", middleName='" + middleName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", password='" + password + '\'' +
        ", roles=" + roles +
        ", address=" + address +
        ", isDeleted=" + isDeleted +
        ", isActive=" + isActive +
        ", isLocked=" + isLocked +
        ", createdDate=" + createdDate +
        ", updatedDate=" + updatedDate +
        '}';
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    if (isDeleted) {
      return false;
    }
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    if (isLocked) {
      return false;
    }
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return isActive;
  }
}
