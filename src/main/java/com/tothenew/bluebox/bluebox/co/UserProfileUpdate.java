package com.tothenew.bluebox.bluebox.co;

import javax.validation.constraints.NotEmpty;

public class UserProfileUpdate {

  @NotEmpty(message = "First Name Field can not be Empty")
  private String firstName;

  private String middleName;

  @NotEmpty(message = "Last Name Field can not be Empty")
  private String lastName;

  public UserProfileUpdate() {
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

  @Override
  public String toString() {
    return "UserProfileUpdate{" +
        "firstName='" + firstName + '\'' +
        ", middleName='" + middleName + '\'' +
        ", lastName='" + lastName + '\'' +
        '}';
  }
}
