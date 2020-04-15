package com.tothenew.bluebox.bluebox.dto;

public abstract class UserDTO {

  private Long id;

  private String email;

  private String firstName;

  private String middleName;

  private String lastName;

  private boolean isActive;

  private String userDp;


  public UserDTO() {
  }

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

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public String getUserDp() {
    return userDp;
  }

  public void setUserDp(String userDp) {
    this.userDp = userDp;
  }

  @Override
  public String toString() {
    return "UserDTO{" +
        "id=" + id +
        ", email='" + email + '\'' +
        ", firstName='" + firstName + '\'' +
        ", middleName='" + middleName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", isActive=" + isActive +
        '}';
  }
}
