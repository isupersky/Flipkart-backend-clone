package com.tothenew.bluebox.bluebox.enitity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Email(message = "This is not a valid Email")
  @NotEmpty(message = "Email field can not be Empty")
  private String email;

  @NotEmpty(message = "First Name Field can not be Empty")
  private String firstName;

  private String middleName;

  @NotEmpty(message = "Last Name Field can not be Empty")
  private String lastName;

  @NotEmpty(message = "Password is a mandatory field")
  @Length(min = 8, max = 15, message = "The Length of the password should be between 8 to 15 characters.")
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d.*)(?=.*\\W.*)[a-zA-Z0-9\\S]{8,15}$",
      message = "The Password should be 8-15 Characters with atleast 1 Lower case, 1 Upper case, 1 Special Character, 1 Number")
  private String password;

  @JsonIgnore
  @ManyToMany
  @JoinTable(name = "user_role",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName =
          "id"))
  private List<Role> roles;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Address> address = new ArrayList<>();

  private boolean isDeleted = false;
  private boolean isActive = false;

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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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


  public List<Address> getAddress() {
    return address;
  }

  public void setAddress(List<Address> address) {
    address.forEach(e -> e.setUser(this));
    this.address = address;
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
        ", isDeleted=" + isDeleted +
        ", isActive=" + isActive +
        '}';
  }
}
